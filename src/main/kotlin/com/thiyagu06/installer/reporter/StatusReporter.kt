package com.thiyagu06.installer.reporter

object StatusReporter {


    fun info(message: String) {
        println("$message!!")
    }

    fun error(message: String) {
        System.err.println("$message :(")
    }
}