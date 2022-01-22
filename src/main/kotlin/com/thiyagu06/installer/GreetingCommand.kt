package com.thiyagu06.installer

import org.zeroturnaround.exec.ProcessExecutor
import org.zeroturnaround.exec.ProcessResult
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.io.File
import java.nio.file.Files
import java.nio.file.Path


@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand : Runnable {

    @Parameters(paramLabel = "<name>", defaultValue = "picocli", description = ["Your name."])
    var name: String? = null
    override fun run() {
        System.out.printf("Hello %s, go go commando!\n", name)
    }

}