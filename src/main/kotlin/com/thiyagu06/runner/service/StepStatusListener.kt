package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.StepStatus.FAILURE
import com.thiyagu06.runner.model.StepStatus.SUCCESS
import com.thiyagu06.runner.model.StepExecutionSummary
import com.thiyagu06.runner.model.StepStatus.SKIPPED
import com.thiyagu06.runner.model.StepStatus.VERIFICATION_ERROR
import com.thiyagu06.runner.reporter.ConsoleReporter
import java.util.concurrent.CopyOnWriteArrayList

object StepStatusListener {

    private val executionStatuses = CopyOnWriteArrayList<StepExecutionSummary>()

    private fun track(stepsSummary: StepExecutionSummary) {
        executionStatuses.addIfAbsent(stepsSummary)
    }

    fun onSuccess(stepName: String, commandOutput: String, shouldPrint: Boolean) {
        val summary = StepExecutionSummary(stepName, SUCCESS, commandOutput)
        if (shouldPrint) ConsoleReporter.info("successfully executed step: $stepName output: $commandOutput")
        track(summary)
    }

    fun onFailure(stepName: String, commandOutput: String) {
        ConsoleReporter.error("Failed to execute step: $stepName, output: ${getFailureReason(commandOutput)}")
        val summary = StepExecutionSummary(stepName, FAILURE, commandOutput)
        track(summary)
    }

    fun onVerificationError(stepName: String, commandOutput: String, userMessage: String) {
        ConsoleReporter.error("Failed to execute step: $stepName, output: ${getFailureReason(commandOutput)}, message:$userMessage")
        val summary = StepExecutionSummary(stepName, VERIFICATION_ERROR, commandOutput)
        track(summary)
    }

    fun onSkipped(stepName: String) {
        val summary = StepExecutionSummary(stepName, SKIPPED, "")
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
