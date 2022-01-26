package com.thiyagu06.installer.service

import com.thiyagu06.installer.model.CommandExecutionResult
import org.zeroturnaround.exec.ProcessExecutor
import org.zeroturnaround.exec.ProcessResult
import picocli.CommandLine
import java.io.File
import java.nio.file.Files

object CommandExecutor {

    init {
        InitializerService.initializeTempDirectory()
    }

    fun execute(command: String): CommandExecutionResult {
        val result = runCommand(command)
        return when (result.exitValue) {
            CommandLine.ExitCode.OK -> CommandExecutionResult.Success(result.exitValue, result.outputUTF8())
            else -> CommandExecutionResult.Failure(result.exitValue, result.outputUTF8())
        }
    }

    private fun runCommand(command: String): ProcessResult {
        val processExecutor = ProcessExecutor()
        val file: File = createExecutableFile(command)
        val list: List<String> = listOf(file.absolutePath)
        processExecutor.directory(InitializerService.tempDirectory.toFile()).command(list).readOutput(true)
        return processExecutor.execute()
    }

    private fun createExecutableFile(command: String): File {
        val file = File.createTempFile("stage_command", ".sh", InitializerService.tempDirectory.toFile())
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