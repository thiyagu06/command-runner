package com.thiyagu06.installer.model

import java.time.Duration

data class StepExecutionSummary(
    val stepName: String,
    val duration: Duration,
    val message: String,
    val status: StepExecutionStatus
)