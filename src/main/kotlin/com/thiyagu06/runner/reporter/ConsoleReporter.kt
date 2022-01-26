package com.thiyagu06.runner.reporter

import com.thiyagu06.runner.model.AnsiCodes.RED_BOLD
import com.thiyagu06.runner.model.AnsiCodes.PURPLE
import com.thiyagu06.runner.model.AnsiCodes.BOLD
import com.thiyagu06.runner.model.AnsiCodes.RESET

object ConsoleReporter {

    fun header(message: String) {
        println("$PURPLE [INFO] $BOLD $message $RESET")
    }

    fun info(message: String) {
        println(message)
    }

    fun error(message: String) {
        println("$PURPLE [ERROR] $RED_BOLD $message $RESET")
    }
}
