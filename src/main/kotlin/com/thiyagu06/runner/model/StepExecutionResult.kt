package com.thiyagu06.runner.model

import java.time.Duration

data class StepExecutionResult(
    val stepName: String,
    val duration: Duration,
    val status: StepExecutionStatus,
    val commandOutput: String
)
