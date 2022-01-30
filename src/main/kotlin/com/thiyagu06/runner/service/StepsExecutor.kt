package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.AnsiCodes.CYAN
import com.thiyagu06.runner.model.AnsiCodes.RESET
import com.thiyagu06.runner.model.CommandResult
import com.thiyagu06.runner.model.RunnerGlobalSettings
import com.thiyagu06.runner.model.Step
import com.thiyagu06.runner.model.StepStatus
import com.thiyagu06.runner.model.StepStatus.SUCCESS
import com.thiyagu06.runner.model.StepStatus.FAILED
import com.thiyagu06.runner.model.StepStatus.VERIFICATION_FAILED

object StepsExecutor {

    fun runSteps(steps: List<Step>, settings: RunnerGlobalSettings) {
        val executedSteps: MutableList<Step> = mutableListOf()
        for (step in steps) {
            val result = CommandExecutor.execute(step.command, settings.printStepResult)
            executedSteps.add(step)
            val stepStatus = handleOutput(result, step)
            if (stepStatus != SUCCESS && step.continueOnFail.not()) break
        }
        steps.minus(executedSteps).forEach { StepStatusListener.onSkipped(it.name) }
    }

    private fun handleOutput(result: CommandResult, step: Step): StepStatus {
        return when (result) {
            is CommandResult.Failure -> {
                StepStatusListener.onFailure(step.name, result.output).let { FAILED }
            }
            is CommandResult.Success -> {
                if (isOutputMatches(result, step))
                    StepStatusListener.onSuccess(step.name, result.output).let { SUCCESS }
                else
                    StepStatusListener.onVerificationError(step.name, result.output, step.failureMessage)
                        .let { VERIFICATION_FAILED }
            }
        }
    }

    private fun isOutputMatches(result: CommandResult, step: Step): Boolean {
        return step.expectedOutput.toRegex().containsMatchIn(result.output)
    }

    fun printSummary() {
        StepStatusListener.printSummary()
    }

    fun printStatusLegend() {
        print("$CYAN Legend: $RESET")
        StepStatus.values().forEach { status ->
            print(" ${status.displayName} [ ${status.uniCode} ]")
        }
    }
}
