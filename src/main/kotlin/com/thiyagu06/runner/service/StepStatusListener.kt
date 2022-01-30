package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.StepStatus.FAILED
import com.thiyagu06.runner.model.StepStatus.SUCCESS
import com.thiyagu06.runner.model.StepExecutionSummary
import com.thiyagu06.runner.model.StepStatus.SKIPPED
import com.thiyagu06.runner.model.StepStatus.VERIFICATION_FAILED
import com.thiyagu06.runner.reporter.ConsoleReporter
import java.util.concurrent.CopyOnWriteArrayList

object StepStatusListener {

    private val executionStatuses = CopyOnWriteArrayList<StepExecutionSummary>()

    private fun track(stepsSummary: StepExecutionSummary) {
        executionStatuses.addIfAbsent(stepsSummary)
    }

    fun onSuccess(stepName: String, commandOutput: String) {
        val summary = StepExecutionSummary(stepName, SUCCESS, commandOutput)
        track(summary)
    }

    fun onFailure(stepName: String, commandOutput: String) {
        val summary = StepExecutionSummary(stepName, FAILED, commandOutput)
        track(summary)
    }

    fun onVerificationError(stepName: String, commandOutput: String, message: String) {
        ConsoleReporter.error("output verification failed: $stepName, output: $commandOutput , message: $message")
        val summary = StepExecutionSummary(stepName, VERIFICATION_FAILED, commandOutput)
        track(summary)
    }

    fun onSkipped(stepName: String) {
        val summary = StepExecutionSummary(stepName, SKIPPED, "")
        track(summary)
    }

    fun printSummary() {
        executionStatuses.forEach {
            ConsoleReporter.info("${it.name} ........... [ ${it.status.uniCode} ]")
        }
    }
}
