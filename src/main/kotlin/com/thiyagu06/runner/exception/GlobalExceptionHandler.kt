package com.thiyagu06.runner.exception

import com.thiyagu06.runner.reporter.ConsoleReporter
import picocli.CommandLine
import picocli.CommandLine.IExecutionExceptionHandler
import picocli.CommandLine.ParseResult

class GlobalExceptionHandler : IExecutionExceptionHandler {
    override fun handleExecutionException(
        ex: Exception,
        cmd: CommandLine,
        parseResult: ParseResult
    ): Int {
        cmd.err.println(cmd.colorScheme.errorText("Failed run the pipeline."))
        cmd.err.println(cmd.colorScheme.errorText(ex.message))
        return when (ex) {
            is StepNotFoundException -> ex.exitCode.also { ConsoleReporter.printSteps(ex.availableSteps, ex.stage) }
            is WorkFlowNotFoundException -> ex.exitCode.also { ConsoleReporter.printWorkflows(ex.availableWorkflows) }
            is CommandRunnerException -> ex.exitCode
            else -> CommandLine.ExitCode.USAGE
        }
    }
}
