package com.thiyagu06.runner

import com.thiyagu06.runner.model.Pipeline
import com.thiyagu06.runner.service.PipelineManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class PipelineParserTest {

    @Test
    fun `should be able to parse the pipeline yaml`() {
        val pipeline: Pipeline = PipelineManager.toPipeline(Paths.get("src", "test", "resources", "pipeline.yaml").toAbsolutePath().toString())
        assertThat(pipeline.steps.setup.size).isEqualTo(2)
        assertThat(pipeline.steps.tearDown.size).isEqualTo(1)
    }
}
