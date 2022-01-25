package com.thiyagu06.installer.commands

import com.thiyagu06.installer.CommandRunnerException
import com.thiyagu06.installer.Stage
import com.thiyagu06.installer.reporter.ConsoleReporter
import com.thiyagu06.installer.reporter.StepStatusTracker
import com.thiyagu06.installer.service.InitializerService
import com.thiyagu06.installer.service.PipelineManager
import com.thiyagu06.installer.service.StepsExecutor
import picocli.CommandLine
import picocli.CommandLine.Option
import java.io.File
import kotlin.system.exitProcess


open class OptionsCommand {

    @Option(names = ["--file", "-f"], required = true, description = ["absolute path of the pipeline yaml"])
    var pipeline: String = ""

    fun run(stage: Stage) {
        try {
            InitializerService.initializeInstallerDirectory()
            val pipeFileYaml = PipelineManager.toPipeline(pipeline)
            StepsExecutor.runAllSteps(pipeFileYaml, stage)
        } catch (exception: CommandRunnerException) {
            ConsoleReporter.error(exception.message)
            exitProcess(exception.exitCode)
        } finally {
            StepStatusTracker.printSummary()
        }
    }
}