package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.Command
import com.thiyagu06.runner.model.CommandExecutionResult.Failure
import com.thiyagu06.runner.model.CommandExecutionResult.Success
import com.thiyagu06.runner.model.RunnerGlobalSettings

object StepsExecutor {

    fun runSteps(commands: List<Command>, settings: RunnerGlobalSettings) {
        val executedCommands: MutableList<Command> = mutableListOf()
        for (command in commands) {
            val result = CommandExecutor.execute(command.command)
            executedCommands.add(command)
            when (result) {
                is Success -> StepExecutionTracker.onSuccess(
                    command.name,
                    result.commandOutput,
                    settings.printStepResult
                )
                is Failure -> {
                    StepExecutionTracker.onFailure(command.name, result.commandOutput)
                    if (!command.continueOnFail) break
                }
            }
        }
        commands.minus(executedCommands).forEach { StepExecutionTracker.onSkipped(it.name) }
        StepExecutionTracker.printSummary()
    }
}
