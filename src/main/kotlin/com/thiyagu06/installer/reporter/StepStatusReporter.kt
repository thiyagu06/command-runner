package com.thiyagu06.installer.reporter

import com.thiyagu06.installer.model.StepExecutionStatus.FAILURE
import com.thiyagu06.installer.model.StepExecutionStatus.SUCCESS
import com.thiyagu06.installer.model.StepExecutionSummary
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList

//TODO should this be singleton. What happens when running it in multiple tabs
object StepStatusReporter {

    private val executionStatuses = CopyOnWriteArrayList<StepExecutionSummary>()

    private fun track(stepsSummary: StepExecutionSummary) {
        executionStatuses.addIfAbsent(stepsSummary)
    }

    fun onSuccess(stepName: String, duration: Duration, commandOutput: String) {
        val summary = StepExecutionSummary(stepName, duration, SUCCESS, commandOutput)
        track(summary)
    }

    fun onFailure(stepName: String, duration: Duration, commandOutput: String) {
        ConsoleReporter.error("Failed to execute step: ${stepName}, output: ${getFailureReason(commandOutput)}")
        val summary = StepExecutionSummary(stepName, duration, FAILURE, commandOutput)
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
