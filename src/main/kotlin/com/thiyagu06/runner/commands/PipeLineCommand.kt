package com.thiyagu06.runner.commands

import com.thiyagu06.runner.exception.WorkFlowNotFoundException
import com.thiyagu06.runner.exception.StepNotFoundException
import com.thiyagu06.runner.model.AnsiCodes
import com.thiyagu06.runner.model.Pipeline
import com.thiyagu06.runner.model.RunnerGlobalSettings
import com.thiyagu06.runner.model.Step
import com.thiyagu06.runner.reporter.ConsoleReporter
import com.thiyagu06.runner.service.InitializerService
import com.thiyagu06.runner.service.PipelineManager
import com.thiyagu06.runner.service.StepsExecutor
import picocli.CommandLine
import picocli.CommandLine.Option

@CommandLine.Command(
    name = "pipeline",
    description = ["execute pipeline"]
)
class PipeLineCommand : Runnable {

    @Option(names = ["--file", "-f"], required = true, description = ["absolute path of the pipeline yaml"])
    var pipeline: String = ""

    @Option(names = ["--step", "-s"], required = false, description = ["run the specific step from the pipeline yaml"])
    var stepName: String? = null

    @Option(
        names = ["--workflow", "-w"],
        required = true,
        description = ["run the specific step from the pipeline yaml"]
    )
    var string: String = ""

    @Option(
        names = ["--no-printOutput", "--no-p"],
        required = false,
        description = ["whether to print the output of each step to console"],
        negatable = true
    )
    var printOutput: Boolean = true

    private val canExecuteStep: (Step, String) -> Boolean = { step, compareTo -> compareTo == step.name }

    override fun run() {
        InitializerService.initGlobalDirectory()
        val pipelineYaml = PipelineManager.toPipeline(pipeline)
        val commandsToRun = getCommands(pipelineYaml, string)
        ConsoleReporter.header("Running pipeline: ${pipelineYaml.name} for stage: $string, description: ${pipelineYaml.description}")
        StepsExecutor.runSteps(commandsToRun, RunnerGlobalSettings(printOutput))
        ConsoleReporter.info("${AnsiCodes.GREEN} ${AnsiCodes.PURPLE}---- Summary for Stage: $string --- ${AnsiCodes.RESET}")
        StepsExecutor.printSummary()
        ConsoleReporter.info("${AnsiCodes.GREEN} ${AnsiCodes.PURPLE}---- Summary end --- ${AnsiCodes.RESET}")
        StepsExecutor.printStatusLegend()
    }

    private fun getCommands(pipeline: Pipeline, stage: String): List<Step> {
        val steps = getStepsInWorkflow(stage, pipeline)
        return stepName?.let { step ->
            steps.filter { canExecuteStep(it, step) }.takeIf { it.isNotEmpty() }
                ?: throw StepNotFoundException(
                    "step: \"$step\" is not specified in the stage: $stage in pipeline: ${pipeline.name}",
                    stage,
                    steps.map { it.name },
                    CommandLine.ExitCode.USAGE
                )
        } ?: steps
    }

    private fun getStepsInWorkflow(workflow: String, pipeline: Pipeline): List<Step> {
        val steps = pipeline.workflows[workflow]
        return steps?.let { steps }
            ?: throw WorkFlowNotFoundException(
                "workflow: \"$workflow\" is not specified in the in pipeline: ${pipeline.name}",
                pipeline.workflows.map { it.key }, CommandLine.ExitCode.USAGE
            )
    }
}
