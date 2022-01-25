package com.thiyagu06.installer.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.thiyagu06.installer.CommandRunnerException
import com.thiyagu06.installer.model.Pipeline
import picocli.CommandLine
import java.io.File

object PipelineConverter {
    private val mapper = ObjectMapper(YAMLFactory()).apply { findAndRegisterModules() }

    fun toPipeline(pipeLineYaml: File): Pipeline {
        try {
            return mapper.readValue(pipeLineYaml)
        } catch (exception: JsonProcessingException) {
            throw CommandRunnerException("could load pipeline definition", CommandLine.ExitCode.USAGE)
        }
    }
}