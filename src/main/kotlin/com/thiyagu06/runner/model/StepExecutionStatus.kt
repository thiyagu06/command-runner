package com.thiyagu06.runner.model

import java.lang.IllegalArgumentException

enum class StepExecutionStatus {
    SUCCESS, FAILURE, SKIPPED;

    fun convertToEmoji(): Emoji {
        return when (this) {
            SUCCESS -> Emoji.GREEN_CHECK_MARK
            FAILURE -> Emoji.RED_X_MARK
            else -> throw IllegalArgumentException("")
        }
    }
}
