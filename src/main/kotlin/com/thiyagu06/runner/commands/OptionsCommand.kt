package com.thiyagu06.runner.commands

import com.thiyagu06.runner.Stage
import com.thiyagu06.runner.exception.StepNotFoundException
import com.thiyagu06.runner.model.Command
import com.thiyagu06.runner.model.Pipeline
import com.thiyagu06.runner.reporter.ConsoleReporter
import com.thiyagu06.runner.service.InitializerService
import com.thiyagu06.runner.service.PipelineManager
import com.thiyagu06.runner.service.StepsExecutor
import picocli.CommandLine
import picocli.CommandLine.Option

open class OptionsCommand {

    @Option(names = ["--file", "-f"], required = true, description = ["absolute path of the pipeline yaml"])
    var pipeline: String = ""

    @Option(names = ["--step", "-s"], required = false, description = ["run the specific step from the pipeline yaml"])
    var stepName: String? = null

    private val canExecuteCommand: (Command, String) -> Boolean = { command, compareTo -> compareTo == command.name }

    fun run(stage: Stage) {
        InitializerService.initGlobalDirectory()
        val pipelineYaml = PipelineManager.toPipeline(pipeline)
        val commandsToRun = getCommands(pipelineYaml, stage)
        ConsoleReporter.header("Running pipeline: ${pipelineYaml.name} for stage: $stage, description: ${pipelineYaml.description}")
        StepsExecutor.runSteps(commandsToRun)
    }

    private fun getCommands(pipeline: Pipeline, stage: Stage): List<Command> {
        val commands = when (stage) {
            Stage.SETUP -> pipeline.steps.setup
            Stage.TEARDOWN -> pipeline.steps.tearDown
        }
        return stepName?.let { step ->
            commands.filter { canExecuteCommand(it, step) }.takeIf { it.isNotEmpty() }
                ?: throw StepNotFoundException(
                    "step: \"$step\" is not specified in the stage: $stage in pipeline: ${pipeline.name}",
                    stage,
                    commands.map { it.name },
                    CommandLine.ExitCode.USAGE
                )
        } ?: commands
    }
}
