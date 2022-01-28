package com.thiyagu06.runner.exception

import com.thiyagu06.runner.model.AnsiCodes.BLUE
import com.thiyagu06.runner.model.AnsiCodes.RESET
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
        cmd.err.println(cmd.colorScheme.errorText("Failed execute the command: $BLUE ${cmd.commandName} $RESET"))
        cmd.err.println(cmd.colorScheme.errorText(ex.message))
        return when (ex) {
            is StepNotFoundException -> ex.exitCode.also { ConsoleReporter.printSteps(ex.availableSteps, ex.stage) }
            is CommandRunnerException -> ex.exitCode
            else -> CommandLine.ExitCode.USAGE
        }
    }
}
