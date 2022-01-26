package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.StepExecutionStatus.FAILURE
import com.thiyagu06.runner.model.StepExecutionStatus.SUCCESS
import com.thiyagu06.runner.model.StepExecutionResult
import com.thiyagu06.runner.reporter.ConsoleReporter
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList

class StepStatusExecutionTracker {

    private val executionStatuses = CopyOnWriteArrayList<StepExecutionResult>()

    private fun track(stepsResult: StepExecutionResult) {
        executionStatuses.addIfAbsent(stepsResult)
    }

    fun onSuccess(stepName: String, duration: Duration, commandOutput: String) {
        val summary = StepExecutionResult(stepName, duration, SUCCESS, commandOutput)
        track(summary)
    }

    fun onFailure(stepName: String, duration: Duration, commandOutput: String) {
        ConsoleReporter.error("Failed to execute step: $stepName, output: ${getFailureReason(commandOutput)}")
        val summary = StepExecutionResult(stepName, duration, FAILURE, commandOutput)
        track(summary)
    }

    fun printSummary() {
        executionStatuses.forEach {
            ConsoleReporter.info("${it.stepName} ........... [ ${it.status.convertToEmoji().value} ]")
        }
    }

    private fun getFailureReason(commandOutput: String): String {
        return commandOutput.substringAfter("line 2:")
    }
}
