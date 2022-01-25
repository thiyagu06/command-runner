package com.thiyagu06.installer.reporter

import com.thiyagu06.installer.model.Emoji
import picocli.CommandLine

object ConsoleReporter {


    fun info(message: String) {
        println(message)
    }

    fun error(message: String) {
        System.err.println("$message :(")
    }

    fun printAnsi(message: String) {
        println(CommandLine.Help.Ansi.AUTO.string(message))
    }
}