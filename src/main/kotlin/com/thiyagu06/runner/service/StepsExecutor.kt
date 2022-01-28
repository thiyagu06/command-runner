package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.Command
import com.thiyagu06.runner.model.CommandExecutionResult.Failure
import com.thiyagu06.runner.model.CommandExecutionResult.Success
import java.time.Clock
import java.time.Duration
import java.time.Instant

object StepsExecutor {

    fun runSteps(commands: List<Command>) {
        var lastFailedCommand: Command? = null
        for (command in commands) {
            if (lastFailedCommand?.abortIfFailed == true) {
                StepExecutionTracker.onSkipped(command.name)
                continue
            }
            val startTime = Instant.now(Clock.systemDefaultZone())
            val result = CommandExecutor.execute(command.command)
            val executionTime = Duration.between(startTime, Instant.now(Clock.systemDefaultZone()))
            when (result) {
                is Success -> StepExecutionTracker.onSuccess(command.name, executionTime, result.commandOutput)
                is Failure -> {
                    lastFailedCommand = command
                    StepExecutionTracker.onFailure(command.name, executionTime, result.commandOutput)
                }
            }
        }
        StepExecutionTracker.printSummary()
    }
}
