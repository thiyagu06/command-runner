package com.thiyagu06.runner.commands

import com.thiyagu06.runner.Stage
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
