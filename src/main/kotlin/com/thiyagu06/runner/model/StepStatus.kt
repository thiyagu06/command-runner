package com.thiyagu06.runner.model

enum class StepStatus(val uniCode: String, val displayName: String) {
    SUCCESS("✅", "Success"),
    FAILED("❌", "Failed"),
    SKIPPED("⏸️", "Skipped"),
    VERIFICATION_FAILED("\uD83D\uDEA8", "Verification Failed");
}
