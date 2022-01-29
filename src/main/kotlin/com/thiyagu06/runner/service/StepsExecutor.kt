package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.CommandResult
import com.thiyagu06.runner.model.RunnerGlobalSettings
import com.thiyagu06.runner.model.Step
import com.thiyagu06.runner.model.StepStatus
import com.thiyagu06.runner.model.StepStatus.SUCCESS
import com.thiyagu06.runner.model.StepStatus.FAILURE
import com.thiyagu06.runner.model.StepStatus.VERIFICATION_ERROR

object StepsExecutor {

    fun runSteps(steps: List<Step>, settings: RunnerGlobalSettings) {
        val executedSteps: MutableList<Step> = mutableListOf()
        for (step in steps) {
            val result = CommandExecutor.execute(step.command)
            executedSteps.add(step)
            val stepStatus = handleOutput(result, step, settings)
            if (stepStatus != SUCCESS && step.continueOnFail.not()) break
        }
        steps.minus(executedSteps).forEach { StepStatusListener.onSkipped(it.name) }
        StepStatusListener.printSummary()
    }

    private fun handleOutput(result: CommandResult, step: Step, settings: RunnerGlobalSettings): StepStatus {
        return when (result) {
            is CommandResult.Failure -> {
                StepStatusListener.onFailure(step.name, result.output).let { FAILURE }
            }
            is CommandResult.Success -> {
                if (isOutputMatches(result, step))
                    StepStatusListener.onSuccess(step.name, result.output, settings.printStepResult).let { SUCCESS }
                else
                    StepStatusListener.onVerificationError(step.name, result.output, step.failureMessage)
                        .let { VERIFICATION_ERROR }
            }
        }
    }

    private fun isOutputMatches(result: CommandResult, step: Step): Boolean {
        return step.expectedOutput.toRegex().containsMatchIn(result.output)
    }
}
