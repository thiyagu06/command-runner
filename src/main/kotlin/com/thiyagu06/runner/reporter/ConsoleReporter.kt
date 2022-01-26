package com.thiyagu06.runner.reporter

object ConsoleReporter {

    private const val ANSI_RED = "\u001B[31m"
    private const val ANSI_RESET = "\u001B[0m"
    private const val ANSI_PURPLE = "\u001B[35m"

    fun info(message: String) {
        println(message)
    }

    fun error(message: String) {
        val ansiString = "$ANSI_PURPLE [ERROR] $ANSI_RED$message$ANSI_RESET"
        println(ansiString)
    }
}
