package com.thiyagu06.installer.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.thiyagu06.installer.CommandRunnerException
import com.thiyagu06.installer.model.Pipeline
import picocli.CommandLine
import java.io.File

object PipelineManager {

    fun toPipeline(pipeLineLocation: String): Pipeline {
        val mapper = ObjectMapper(YAMLFactory()).apply { findAndRegisterModules() }
        try {
            val pipeLineFile = getPipeline(pipeLineLocation)
            return mapper.readValue(pipeLineFile.inputStream())
        } catch (exception: JsonProcessingException) {
            println(exception.message)
            throw CommandRunnerException("could not load pipeline definition", CommandLine.ExitCode.USAGE)
        }
    }

    private fun getPipeline(location: String): File {
        val pipelineFile = File(location)
        if (pipelineFile.isFile.not() && pipelineFile.exists().not()) {
            throw CommandRunnerException(
                "could not find file at: ${pipelineFile.absolutePath}",
                CommandLine.ExitCode.USAGE
            )
        }
        return pipelineFile
    }
}
