package com.thiyagu06.runner.commands

import com.thiyagu06.runner.CommandRunnerException
import com.thiyagu06.runner.Stage
import com.thiyagu06.runner.reporter.ConsoleReporter
import com.thiyagu06.runner.service.InitializerService
import com.thiyagu06.runner.service.PipelineManager
import com.thiyagu06.runner.service.StepsExecutor
import picocli.CommandLine.Option
import kotlin.system.exitProcess

open class OptionsCommand {

    @Option(names = ["--file", "-f"], required = true, description = ["absolute path of the pipeline yaml"])
    var pipeline: String = ""

    fun run(stage: Stage) {
        try {
            InitializerService.initializeInstallerDirectory()
            val pipelineYaml = PipelineManager.toPipeline(pipeline)
            StepsExecutor.runAllSteps(pipelineYaml, stage)
        } catch (exception: CommandRunnerException) {
            ConsoleReporter.error(exception.message)
            exitProcess(exception.exitCode)
        }
    }
}
