package com.thiyagu06.installer.service

import com.thiyagu06.installer.CommandRunnerException
import com.thiyagu06.installer.Stage
import com.thiyagu06.installer.model.CommandExecutionResult
import com.thiyagu06.installer.model.Command
import com.thiyagu06.installer.model.Pipeline
import com.thiyagu06.installer.reporter.ConsoleReporter
import com.thiyagu06.installer.reporter.StepStatusReporter
import java.time.Clock
import java.time.Duration
import java.time.Instant
import kotlin.time.ExperimentalTime

object StepsExecutor {

    fun runAllSteps(pipeline: Pipeline, stage: Stage) {
        ConsoleReporter.info("Running pipeline: ${pipeline.name} for stage: $stage, description: ${pipeline.description}")
        when (stage) {
            Stage.SETUP -> runCommands(pipeline.steps.setup)
            Stage.TEARDOWN -> runCommands(pipeline.steps.tearDown)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun runCommands(commands: List<Command>) {
        commands.forEach {
            val startTime = Instant.now(Clock.systemDefaultZone())
            val result = CommandExecutor.execute(it.command)
            val executionTime = Duration.between(startTime, Instant.now(Clock.systemDefaultZone()))
            when (result) {
                is CommandExecutionResult.Success -> {
                    StepStatusReporter.onSuccess(it.name, executionTime, result.commandOutput)
                }
                is CommandExecutionResult.Failure -> {
                    StepStatusReporter.onFailure(it.name, executionTime, result.commandOutput)
                    if (it.abortIfFailed) {
                        throw CommandRunnerException(
                            "Failed to execute command: ${it.name}, output: ${result.commandOutput}",
                            result.exitCode
                        )
                    }
                }
            }
        }
    }
}
