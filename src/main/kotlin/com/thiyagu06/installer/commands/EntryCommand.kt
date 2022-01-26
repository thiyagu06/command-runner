package com.thiyagu06.installer.commands

import io.quarkus.picocli.runtime.annotations.TopCommand
import picocli.CommandLine

@TopCommand
@CommandLine.Command(
    mixinStandardHelpOptions = true,
    subcommands = [SetupCommand::class, TearDownCommand::class],
    description = ["CLI for executing shell commands"]
)
class EntryCommand
