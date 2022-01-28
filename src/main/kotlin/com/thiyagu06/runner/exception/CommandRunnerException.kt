package com.thiyagu06.runner.exception

import com.thiyagu06.runner.Stage

open class CommandRunnerException(override val message: String, val exitCode: Int) : RuntimeException(message)

class StepNotFoundException(override val message: String, val stage: Stage, val availableSteps: List<String>, exitCode: Int) :
    CommandRunnerException(message, exitCode)
