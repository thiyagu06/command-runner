package com.thiyagu06.runner.model

object AnsiCodes {
    const val RED_BOLD = "\u001b[1;31m"
    const val RESET = "\u001B[0m"
    const val PURPLE = "\u001B[35m"
    const val BOLD = "\u001b[1;32m"
    private const val BLUE_BOLD = "\u001b[1;34m"
    const val GREEN_CHECK_MARK = "\u2705"
    const val RED_X_MARK = "\u274C"
    const val BLUE_SKIPPED_MARK = "$BLUE_BOLD⦰$RESET"
}
