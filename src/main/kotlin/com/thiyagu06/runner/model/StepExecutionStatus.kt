package com.thiyagu06.runner.model

enum class StepExecutionStatus {
    SUCCESS, FAILURE, SKIPPED;

    fun convertToEmoji(): Emoji {
        return when (this) {
            SUCCESS -> Emoji.GREEN_CHECK_MARK
            FAILURE -> Emoji.RED_X_MARK
            SKIPPED -> Emoji.SKIPPED_MARK
        }
    }
}
