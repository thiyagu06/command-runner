package com.thiyagu06.installer.reporter

import com.thiyagu06.installer.model.Emoji
import com.thiyagu06.installer.model.StepExecutionStatus.FAILURE
import com.thiyagu06.installer.model.StepExecutionStatus.SUCCESS
import com.thiyagu06.installer.model.StepExecutionSummary
import de.vandermeer.asciitable.AsciiTable
import java.time.Duration
import java.util.concurrent.CopyOnWriteArrayList

object StepStatusTracker {

    private val executionStatuses = CopyOnWriteArrayList<StepExecutionSummary>()

    private fun track(stepsSummary: StepExecutionSummary) {
        executionStatuses.addIfAbsent(stepsSummary)
    }

    fun onSuccess(stepName: String, duration: Duration) {
        val summary = StepExecutionSummary(stepName, duration, "", SUCCESS)
        track(summary)
    }

    fun onFailure(stepName: String, duration: Duration, message: String) {
        val summary = StepExecutionSummary(stepName, duration, message, FAILURE)
        track(summary)
    }

    fun printSummary() {
        executionStatuses.forEach { _ ->
            ConsoleReporter.printAnsi(Emoji.RED_X_MARK.value)
        }
    }
}