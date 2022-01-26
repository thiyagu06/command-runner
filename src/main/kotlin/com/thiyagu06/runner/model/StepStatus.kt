package com.thiyagu06.runner.model

import com.thiyagu06.runner.model.AnsiCodes.GREEN_CHECK_MARK
import com.thiyagu06.runner.model.AnsiCodes.RED_X_MARK
import com.thiyagu06.runner.model.AnsiCodes.BLUE_SKIPPED_MARK

enum class StepStatus {
    SUCCESS, FAILURE, SKIPPED;

    fun convertToEmoji(): String {
        return when (this) {
            SUCCESS -> GREEN_CHECK_MARK
            FAILURE -> RED_X_MARK
            SKIPPED -> BLUE_SKIPPED_MARK
        }
    }
}
