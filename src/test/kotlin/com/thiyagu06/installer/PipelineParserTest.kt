package com.thiyagu06.installer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.thiyagu06.installer.model.Emoji
import com.thiyagu06.installer.model.Pipeline
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import picocli.CommandLine
import java.nio.file.Paths


class PipelineParserTest {

    @Test
    fun `should be able to parse the pipeline yaml`() {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.findAndRegisterModules()
        val pipeline: Pipeline = mapper.readValue(Paths.get("src", "test", "resources", "pipeline.yaml").toFile())
        assertThat(pipeline.steps.size).isEqualTo(2)
        assertThat(pipeline.steps).containsKeys("install gh", "install jq")
    }

    @Test
    fun test() {
        println(CommandLine.Help.Ansi.AUTO.string(Emoji.RED_X_MARK.value))
    }
}