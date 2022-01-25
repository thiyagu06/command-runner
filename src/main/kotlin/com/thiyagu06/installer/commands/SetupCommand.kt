package com.thiyagu06.installer.commands

import com.thiyagu06.installer.Stage
import picocli.CommandLine.Command


@Command(
    name = "setup",
    description = ["runs setup stages in pipeline Yaml."]
)
class SetupCommand : OptionsCommand(), Runnable {

    override fun run() {
        run(Stage.SETUP)
    }
}