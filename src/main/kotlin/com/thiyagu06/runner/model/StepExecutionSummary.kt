package com.thiyagu06.runner.model

data class StepExecutionSummary(
    val name: String,
    val status: StepStatus,
    val commandOutput: String
)
