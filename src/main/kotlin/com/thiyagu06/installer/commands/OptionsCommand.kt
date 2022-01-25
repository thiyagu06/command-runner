package com.thiyagu06.installer.commands

import com.thiyagu06.installer.CommandRunnerException
import com.thiyagu06.installer.Stage
import com.thiyagu06.installer.reporter.StatusReporter
import com.thiyagu06.installer.service.InitializerService
import com.thiyagu06.installer.service.StepsExecutor
import picocli.CommandLine
import picocli.CommandLine.Option
import java.io.File
import kotlin.system.exitProcess


open class OptionsCommand {

    @Option(names = ["--pipeline", "-p"], description = ["absolute path of the pipeline yaml"])
    private val pipeline: String? = null

    fun run(command: OptionsCommand, stage: Stage) {
        try {
            pipeline?.let {
                val pipeFileYaml = getPipeline(it)
                InitializerService.initializeInstallerDirectory()
                StepsExecutor.runAllSteps(pipeFileYaml, stage)
            } ?: let {
                val commandLine = CommandLine(command)
                commandLine.usage(System.out)
                return
            }
        } catch (exception: CommandRunnerException) {
            StatusReporter.error(exception.message)
            exitProcess(exception.exitCode)
        }
    }

    private fun getPipeline(location: String): File {
        val pipelineFile = File(location)
        if (pipelineFile.isFile.not() && pipelineFile.exists().not()) {
            throw CommandRunnerException(
                "could not find file at: ${pipelineFile.absolutePath}",
                CommandLine.ExitCode.USAGE
            )
        }
        return pipelineFile
    }
}