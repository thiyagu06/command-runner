package com.thiyagu06.runner.exception

open class CommandRunnerException(override val message: String, val exitCode: Int) : RuntimeException(message)

class StepNotFoundException(
    override val message: String,
    val workflow: String,
    val availableSteps: List<String>,
    exitCode: Int
) :
    CommandRunnerException(message, exitCode)

class WorkFlowNotFoundException(override val message: String, val availableWorkflows: List<String>, exitCode: Int) :
    CommandRunnerException(message, exitCode)
