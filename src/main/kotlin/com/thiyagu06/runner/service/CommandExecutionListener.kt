package com.thiyagu06.runner.service

import com.thiyagu06.runner.model.AnsiCodes
import com.thiyagu06.runner.reporter.ConsoleReporter

interface CommandExecutionListener {

    fun beforeExecution()
    fun afterExecution()
}

class TrackCommandProgressListener(val command: String) : CommandExecutionListener {
    override fun beforeExecution() {
        ConsoleReporter.info(
            "${AnsiCodes.RUNNER} ${AnsiCodes.RUNNER} ${AnsiCodes.CYAN} Executing command: ${AnsiCodes.RESET} ${AnsiCodes.GREEN} $command ${AnsiCodes.RESET} ${AnsiCodes.RUNNER} ${AnsiCodes.RUNNER}"
        )
    }

    override fun afterExecution() {
        ConsoleReporter.info(
            "${AnsiCodes.RUNNER} ${AnsiCodes.RUNNER} ${AnsiCodes.CYAN} execution completed for command: ${AnsiCodes.RESET} ${AnsiCodes.GREEN} $command ${AnsiCodes.RESET} ${AnsiCodes.RUNNER} ${AnsiCodes.RUNNER}"
        )
    }
}

object NoopCommandExecutionLister : CommandExecutionListener {
    override fun beforeExecution() {
    }

    override fun afterExecution() {
    }
}
