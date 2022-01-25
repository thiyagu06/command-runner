package com.thiyagu06.installer.model

sealed class CommandExecutionResult(exitCode:Int, commandOutput:String)  {

    class Success(exitCode:Int, commandOutput:String) : CommandExecutionResult(exitCode, commandOutput)

    class Failure(exitCode: Int, commandOutput: String): CommandExecutionResult(exitCode, commandOutput)
}
