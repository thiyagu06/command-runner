package com.thiyagu06.runner.model

sealed class CommandResult(val output: String) {

    class Success(output: String) : CommandResult(output)

    class Failure(output: String) : CommandResult(output)
}
