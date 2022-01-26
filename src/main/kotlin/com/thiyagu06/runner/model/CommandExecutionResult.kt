package com.thiyagu06.installer.model

sealed class CommandExecutionResult(val exitCode: Int, val commandOutput: String) {

    class Success(exitCode: Int, commandOutput: String) : CommandExecutionResult(exitCode, commandOutput)

    class Failure(exitCode: Int, commandOutput: String) : CommandExecutionResult(exitCode, commandOutput)
}
