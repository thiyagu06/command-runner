package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.CommandResult
import org.zeroturnaround.exec.ProcessExecutor
import org.zeroturnaround.exec.ProcessResult
import picocli.CommandLine
import java.io.File
import java.nio.file.Files

object CommandExecutor {

    fun execute(command: String, printResult: Boolean): CommandResult {
        InitializerService.initTempDirectory()
        val executionListener = if (printResult) TrackCommandProgressListener(command) else NoopCommandExecutionLister
        executionListener.beforeExecution()
        val result = runCommand(command, printResult)
        executionListener.afterExecution()
        return when (result.exitValue) {
            CommandLine.ExitCode.OK -> CommandResult.Success(result.outputUTF8())
            else -> CommandResult.Failure(result.outputUTF8())
        }
    }

    private fun runCommand(command: String, printResult: Boolean): ProcessResult {
        val processExecutor = ProcessExecutor().redirectError(System.err)
        if (printResult) processExecutor.redirectOutput(System.out)
        val file: File = createExecutableFile(command)
        val list: List<String> = listOf(file.absolutePath)
        processExecutor.directory(InitializerService.tempDirectory.toFile()).command(list).readOutput(true)
        return processExecutor.execute()
    }

    private fun createExecutableFile(command: String): File {
        val file = File.createTempFile("step_command", ".sh", InitializerService.tempDirectory.toFile())
        file.apply {
            setExecutable(true)
            setWritable(true)
            setReadable(true)
            deleteOnExit()
        }
        Files.write(file.toPath(), wrapCommandInShellFile(command).toByteArray())
        return file
    }

    private fun wrapCommandInShellFile(value: String): String =
        buildString {
            append("#!/bin/bash")
            append(System.lineSeparator())
            append(value)
            append(System.lineSeparator())
        }
}
