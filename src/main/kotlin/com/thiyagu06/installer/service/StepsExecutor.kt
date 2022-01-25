package com.thiyagu06.installer.service

import com.thiyagu06.installer.CommandRunnerException
import com.thiyagu06.installer.Stage
import com.thiyagu06.installer.model.Command
import com.thiyagu06.installer.model.CommandExecutionResult
import com.thiyagu06.installer.model.Pipeline
import com.thiyagu06.installer.reporter.ConsoleReporter
import com.thiyagu06.installer.reporter.StepStatusTracker
import picocli.CommandLine
import java.io.File
import java.time.Clock
import java.time.Duration
import java.time.Instant

object StepsExecutor {

    fun runAllSteps(pipeline: Pipeline, stage: Stage) {
        when (stage) {
            Stage.SETUP -> runSetupSteps(pipeline)
            Stage.TEARDOWN -> runTearDownSteps(pipeline)
        }
    }

    private fun runSetupSteps(pipeline: Pipeline) {
        with(pipeline) {
            ConsoleReporter.info("Running pipeline: $name, description=$description")
            steps.forEach {
                runStep(it.key, it.value.setup)
            }
        }
    }

    private fun runStep(name: String, commands: List<Command>) {
        ConsoleReporter.info("Running step: $name")
        val startTime = Instant.now(Clock.systemDefaultZone())
        commands.forEach { command ->
            val result = CommandExecutor.execute(command)
            if (result is CommandExecutionResult.Failure) {
                StepStatusTracker.onFailure(
                    name,
                    Duration.between(startTime, Instant.now(Clock.systemDefaultZone())),
                    result.commandOutput
                )
                ConsoleReporter.error("error when executing command: $command in stage:$name error: ${command.onFailure}")
                throw CommandRunnerException(
                    "error when executing stage:$name error: $command.onFailure",
                    CommandLine.ExitCode.USAGE
                )
            }
        }
        StepStatusTracker.onSuccess(name, Duration.between(startTime, Instant.now(Clock.systemDefaultZone())))
    }

    private fun runTearDownSteps(pipeline: Pipeline) {
        println("running tear down stage: ${pipeline.name}")
    }
}