package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.Command
import com.thiyagu06.runner.model.CommandExecutionResult.Failure
import com.thiyagu06.runner.model.CommandExecutionResult.Success
import com.thiyagu06.runner.model.RunnerGlobalSettings
import java.time.Clock
import java.time.Duration
import java.time.Instant

object StepsExecutor {

    fun runSteps(commands: List<Command>, settings: RunnerGlobalSettings) {
        val executedCommands: MutableList<Command> = mutableListOf()
        for (command in commands) {
            val startTime = Instant.now(Clock.systemDefaultZone())
            val result = CommandExecutor.execute(command.command)
            val executionTime = Duration.between(startTime, Instant.now(Clock.systemDefaultZone()))
            executedCommands.add(command)
            when (result) {
                is Success -> StepExecutionTracker.onSuccess(
                    command.name,
                    executionTime,
                    result.commandOutput,
                    settings.printStepResult
                )
                is Failure -> {
                    StepExecutionTracker.onFailure(command.name, executionTime, result.commandOutput)
                    if (command.abortIfFailed) break
                }
            }
        }
        commands.minus(executedCommands).forEach { StepExecutionTracker.onSkipped(it.name) }
        StepExecutionTracker.printSummary()
    }
}
