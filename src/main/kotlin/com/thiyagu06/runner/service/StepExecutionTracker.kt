package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.StepStatus.FAILURE
import com.thiyagu06.runner.model.StepStatus.SUCCESS
import com.thiyagu06.runner.model.StepExecutionResult
import com.thiyagu06.runner.model.StepStatus.SKIPPED
import com.thiyagu06.runner.reporter.ConsoleReporter
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList

object StepExecutionTracker {

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

    fun onSkipped(stepName: String) {
        val summary = StepExecutionResult(stepName, Duration.ZERO, SKIPPED, "")
        track(summary)
    }

    fun printSummary() {
        executionStatuses.forEach {
            ConsoleReporter.info("${it.name} ........... [ ${it.status.convertToAnsiCode()} ]")
        }
    }

    private fun getFailureReason(commandOutput: String): String {
        return commandOutput.substringAfter("line 2:")
    }
}
