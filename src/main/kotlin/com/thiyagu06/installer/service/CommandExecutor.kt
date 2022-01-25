package com.thiyagu06.installer.service

import com.thiyagu06.installer.model.Command
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

    fun execute(command: Command): CommandExecutionResult {
        val commandExecutionResult = executeCommand(command.command)
        return when (commandExecutionResult.exitValue) {
            CommandLine.ExitCode.OK -> CommandExecutionResult.Success(1, commandExecutionResult.outputUTF8())
            else -> CommandExecutionResult.Failure(0, commandExecutionResult.outputUTF8())
        }
    }

    private fun executeCommand(command: String): ProcessResult {
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