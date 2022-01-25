package com.thiyagu06.installer.service

import com.thiyagu06.installer.CommandRunnerException
import com.thiyagu06.installer.Stage
import com.thiyagu06.installer.model.Command
import com.thiyagu06.installer.model.CommandExecutionResult
import com.thiyagu06.installer.model.Pipeline
import com.thiyagu06.installer.reporter.StatusReporter
import picocli.CommandLine
import java.io.File

object StepsExecutor {

    fun runAllSteps(pipeLineYaml: File, stage: Stage) {
        val pipeline = PipelineConverter.toPipeline(pipeLineYaml)
        when (stage) {
            Stage.SETUP -> runSetupSteps(pipeline)
            Stage.TEARDOWN -> runTearDownSteps(pipeline)
        }
    }

    private fun runSetupSteps(pipeline: Pipeline) {
        with(pipeline) {
            StatusReporter.info("Running pipeline: $name, description=$description")
            steps.forEach {
                runStep(it.key, it.value.setup)
            }
        }
    }

    private fun runStep(name: String, commands: List<Command>) {
        StatusReporter.info("Running stage: $name")
        commands.forEach {
            when (CommandExecutor.execute(it)) {
                is CommandExecutionResult.Success -> StatusReporter.info("Command executed Successfully")
                else -> throw CommandRunnerException(
                    "error when executing stage:$name error: $it.onFailure",
                    CommandLine.ExitCode.USAGE
                ).also {
                    StatusReporter.error("error when executing stage:$name error: $it.onFailure")
                }
            }
        }
    }

    private fun runTearDownSteps(pipeline: Pipeline) {
        println("running tear down stage: ${pipeline.name}")
    }
}