package com.thiyagu06.installer.service

import com.thiyagu06.installer.CommandRunnerException
import picocli.CommandLine
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


object InitializerService {

    private val userHomeDir: String = System.getProperty("user.home")

    private val installerDir: Path = Paths.get(userHomeDir, ".dev-stack")

    val tempDirectory: Path = Paths.get(installerDir.toString(), "temp")

    fun initializeInstallerDirectory() {
        if (!Files.exists(installerDir)) {
            try {
                Files.createDirectory(installerDir)
            } catch (e: IOException) {
                throw CommandRunnerException(
                    "Failed initializing .dev-stack directory.",
                    CommandLine.ExitCode.SOFTWARE
                )
            }
        }
    }
}