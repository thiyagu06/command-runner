package com.thiyagu06.runner.service

import com.thiyagu06.runner.exception.CommandRunnerException
import picocli.CommandLine
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object InitializerService {

    private val userHomeDir: String = System.getProperty("user.home")

    private val installerDir: Path = Paths.get(userHomeDir, ".command-runner")

    val tempDirectory: Path = Paths.get(installerDir.toString(), "temp")

    fun initGlobalDirectory() {
        if (!Files.exists(installerDir)) {
            try {
                Files.createDirectory(installerDir)
            } catch (e: IOException) {
                throw CommandRunnerException(
                    "Failed initializing .command-runner directory.",
                    CommandLine.ExitCode.SOFTWARE
                )
            }
        }
    }

    fun initTempDirectory() {
        if (!Files.exists(tempDirectory)) {
            try {
                Files.createDirectory(tempDirectory)
            } catch (e: IOException) {
                throw CommandRunnerException(
                    "Failed initializing .temp directory.",
                    CommandLine.ExitCode.SOFTWARE
                )
            }
        }
    }
}
