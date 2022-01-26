package com.thiyagu06.runner

class CommandRunnerException(override val message: String, val exitCode: Int) : RuntimeException(message)
