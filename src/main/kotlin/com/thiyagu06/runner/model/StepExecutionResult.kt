package com.thiyagu06.runner.model

import java.time.Duration

data class StepExecutionResult(
    val name: String,
    val duration: Duration,
    val status: StepStatus,
    val commandOutput: String
)
