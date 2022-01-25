package com.thiyagu06.installer.model

sealed class CommandExecutionResult(val commandOutput: String) {

    class Success(commandOutput: String) : CommandExecutionResult(commandOutput)

    class Failure(commandOutput: String) : CommandExecutionResult(commandOutput)
}
