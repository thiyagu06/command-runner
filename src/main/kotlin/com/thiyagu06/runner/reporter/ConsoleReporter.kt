package com.thiyagu06.runner.reporter

import com.thiyagu06.runner.Stage
import com.thiyagu06.runner.model.AnsiCodes.YELLOW
import com.thiyagu06.runner.model.AnsiCodes.RED_BOLD
import com.thiyagu06.runner.model.AnsiCodes.PURPLE
import com.thiyagu06.runner.model.AnsiCodes.GREEN
import com.thiyagu06.runner.model.AnsiCodes.RESET

object ConsoleReporter {

    fun header(message: String) {
        println("$PURPLE [INFO] $GREEN $message $RESET")
    }

    fun info(message: String) {
        println(message)
    }

    fun error(message: String) {
        println("$PURPLE [ERROR] $RED_BOLD $message $RESET")
    }

    fun printSteps(steps: List<String>, stage: Stage) {
        info("The following steps are available in the stage : $stage")
        steps.forEachIndexed { index, stepName ->
            info("${index + 1}. $YELLOW$stepName $RESET")
        }
    }
}
