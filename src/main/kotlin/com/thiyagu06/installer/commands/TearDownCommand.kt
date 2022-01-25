package com.thiyagu06.installer.commands

import com.thiyagu06.installer.Stage

class TearDownCommand : OptionsCommand(), Runnable {

    override fun run() {
        run(Stage.TEARDOWN)
    }
}