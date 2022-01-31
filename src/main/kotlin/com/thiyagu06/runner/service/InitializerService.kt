package com.thiyagu06.runner.service

import com.thiyagu06.runner.exception.CommandRunnerException
import picocli.CommandLine
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object InitializerService {

    fun initGlobalDirectory() {
        val installerDir = getInstallerDirectory()
        if (!Files.exists(installerDir)) {
            try {
                Files.createDirectory(installerDir)
            } catch (e: IOException) {
                throw CommandRunnerException(
                    "Failed initializing .command-runner directory. exception:${e.message}",
                    CommandLine.ExitCode.SOFTWARE
                )
            }
        }
    }

    private fun getInstallerDirectory(): Path {
        val userHomeDir = System.getProperty("user.home")
        return Paths.get(userHomeDir, ".command-runner")
    }

    fun getTempDirectory(): Path {
        return Paths.get(getInstallerDirectory().toString(), "temp")
    }

    fun initTempDirectory() {
        val tempDirectory = getTempDirectory()
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
