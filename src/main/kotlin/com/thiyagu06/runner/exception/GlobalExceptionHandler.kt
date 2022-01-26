package com.thiyagu06.runner.exception

import com.thiyagu06.runner.reporter.ConsoleReporter
import picocli.CommandLine.ParseResult
import picocli.CommandLine.IExecutionExceptionHandler
import picocli.CommandLine

class GlobalExceptionHandler : IExecutionExceptionHandler {
    override fun handleExecutionException(
        ex: Exception,
        cmd: CommandLine,
        parseResult: ParseResult
    ): Int {
        ConsoleReporter.error(ex.message!!)
        return when (ex) {
            is CommandRunnerException -> ex.exitCode
            else -> CommandLine.ExitCode.USAGE
        }
    }
}
