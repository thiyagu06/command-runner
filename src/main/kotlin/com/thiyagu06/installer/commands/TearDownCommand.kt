package com.thiyagu06.installer.commands

import com.thiyagu06.installer.Stage
import picocli.CommandLine

@CommandLine.Command(
    name = "teardown",
    description = ["runs teardown stage in pipeline Yaml."],
)
class TearDownCommand : OptionsCommand(), Runnable {

    override fun run() {
        run(Stage.TEARDOWN)
    }
}