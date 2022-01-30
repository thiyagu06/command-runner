package com.thiyagu06.runner.commands

import com.thiyagu06.runner.exception.GlobalExceptionHandler
import io.quarkus.picocli.runtime.annotations.TopCommand
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import picocli.CommandLine
import picocli.CommandLine.IFactory
import javax.inject.Inject

@TopCommand
@CommandLine.Command(
    name = "command-runner",
    mixinStandardHelpOptions = true,
    subcommands = [PipeLineCommand::class],
    description = ["CLI for executing shell commands"]
)
@QuarkusMain
class EntryCommand : QuarkusApplication {
    @Inject
    lateinit var factory: IFactory

    @Throws(Exception::class)
    override fun run(vararg args: String?): Int {
        return CommandLine(this, factory)
            .setExecutionExceptionHandler(GlobalExceptionHandler())
            .execute(*args)
    }
}
