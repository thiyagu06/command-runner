package com.thiyagu06.runner

import com.thiyagu06.runner.exception.CommandRunnerException
import com.thiyagu06.runner.model.Pipeline
import com.thiyagu06.runner.service.PipelineManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class PipelineParserTest {

    @Test
    fun `should be able to parse the pipeline yaml`() {
        val pipeline: Pipeline = PipelineManager.toPipeline(
            Paths.get("src", "test", "resources", "pipeline.yaml").toAbsolutePath().toString()
        )
        assertThat(pipeline.steps.setup.size).isEqualTo(2)
        assertThat(pipeline.steps.tearDown.size).isEqualTo(1)
    }

    @Test
    fun `should throw exception for parsing error in pipeline yaml`() {
        val pipelineYamlLocation = Paths.get("src", "test", "resources", "error.yaml").toAbsolutePath().toString()
        assertThatExceptionOfType(CommandRunnerException::class.java).isThrownBy {
            PipelineManager.toPipeline(pipelineYamlLocation)
        }.withMessageContaining("could not load pipeline definition")
    }

    @Test
    fun `should throw exception for parsing error for invalid yaml file location`() {
        val pipelineYamlLocation = Paths.get("src", "test", "resources", "nonexists.yaml").toAbsolutePath().toString()
        assertThatExceptionOfType(CommandRunnerException::class.java).isThrownBy {
            PipelineManager.toPipeline(pipelineYamlLocation)
        }.withMessageContaining("could not find file at")
    }
}
