package com.thiyagu06.runner.exception

class CommandRunnerException(override val message: String, val exitCode: Int) : RuntimeException(message)
