package com.thiyagu06.runner.service

import com.thiyagu06.runner.Stage
import com.thiyagu06.runner.model.Command
import com.thiyagu06.runner.model.CommandExecutionResult.Failure
import com.thiyagu06.runner.model.CommandExecutionResult.Success
import com.thiyagu06.runner.model.Pipeline
import com.thiyagu06.runner.reporter.ConsoleReporter
import java.time.Clock
import java.time.Duration
import java.time.Instant

object StepsExecutor {

    fun runAllSteps(pipeline: Pipeline, stage: Stage) {
        ConsoleReporter.header("Running pipeline: ${pipeline.name} for stage: $stage, description: ${pipeline.description}")
        when (stage) {
            Stage.SETUP -> runCommands(pipeline.steps.setup)
            Stage.TEARDOWN -> runCommands(pipeline.steps.tearDown)
        }
    }

    private fun runCommands(commands: List<Command>) {
        var recentFailedCommand: Command? = null
        for (command in commands) {
            if (recentFailedCommand != null && recentFailedCommand.abortIfFailed) {
                StepExecutionTracker.onSkipped(command.name)
                continue
            }
            val startTime = Instant.now(Clock.systemDefaultZone())
            val result = CommandExecutor.execute(command.command)
            val executionTime = Duration.between(startTime, Instant.now(Clock.systemDefaultZone()))
            when (result) {
                is Success -> StepExecutionTracker.onSuccess(command.name, executionTime, result.commandOutput)
                is Failure -> {
                    recentFailedCommand = command
                    StepExecutionTracker.onFailure(command.name, executionTime, result.commandOutput)
                }
            }
        }
        StepExecutionTracker.printSummary()
    }
}
