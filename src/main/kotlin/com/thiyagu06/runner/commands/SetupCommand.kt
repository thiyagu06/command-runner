package com.thiyagu06.runner.commands

import com.thiyagu06.runner.Stage
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
