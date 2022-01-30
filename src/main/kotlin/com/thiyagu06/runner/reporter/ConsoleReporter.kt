package com.thiyagu06.runner.reporter

import com.thiyagu06.runner.model.AnsiCodes.GREEN
import com.thiyagu06.runner.model.AnsiCodes.PURPLE
import com.thiyagu06.runner.model.AnsiCodes.RED_BOLD
import com.thiyagu06.runner.model.AnsiCodes.RESET
import com.thiyagu06.runner.model.AnsiCodes.YELLOW

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

    fun printSteps(steps: List<String>, string: String) {
        info("The following steps are available in the stage : $string")
        steps.forEachIndexed { index, stepName ->
            info("${index + 1}. $YELLOW$stepName $RESET")
        }
    }

    fun printWorkflows(workflows: List<String>) {
        info("The following workflows are available in the pipeline")
        workflows.forEachIndexed { index, workflowName ->
            info("${index + 1}. $YELLOW$workflowName $RESET")
        }
    }
}
