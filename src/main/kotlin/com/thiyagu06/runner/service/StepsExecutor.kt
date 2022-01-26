package com.thiyagu06.runner.service

import com.thiyagu06.runner.CommandRunnerException
import com.thiyagu06.runner.Stage
import com.thiyagu06.runner.model.Command
import com.thiyagu06.runner.model.CommandExecutionResult
import com.thiyagu06.runner.model.Pipeline
import com.thiyagu06.runner.reporter.ConsoleReporter
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
        val stepExecutionTracker = StepExecutionTracker()
        commands.forEach {
            val startTime = Instant.now(Clock.systemDefaultZone())
            val result = CommandExecutor.execute(it.command)
            val executionTime = Duration.between(startTime, Instant.now(Clock.systemDefaultZone()))
            when (result) {
                is CommandExecutionResult.Success -> {
                    stepExecutionTracker.onSuccess(it.name, executionTime, result.commandOutput)
                }
                is CommandExecutionResult.Failure -> {
                    stepExecutionTracker.onFailure(it.name, executionTime, result.commandOutput)
                    if (it.abortIfFailed) {
                        throw CommandRunnerException(
                            "Failed to execute command: ${it.name}, output: ${result.commandOutput}",
                            result.exitCode
                        )
                    }
                }
            }
        }
        stepExecutionTracker.printSummary()
    }
}
