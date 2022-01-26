package com.thiyagu06.installer

class CommandRunnerException(override val message: String, val exitCode: Int) : RuntimeException(message)