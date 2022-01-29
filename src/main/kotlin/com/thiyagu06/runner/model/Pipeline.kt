package com.thiyagu06.runner.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.thiyagu06.runner.Stage
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class Step @JsonCreator constructor(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("command")
    val command: String,
    @JsonProperty("continueOnFail")
    val continueOnFail: Boolean = false
) {
    @JsonProperty("expectedOutput")
    val expectedOutput: String = ""

    @JsonProperty("failureMessage")
    val failureMessage: String = ""
}

@RegisterForReflection
data class Pipeline @JsonCreator constructor(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("steps")
    val steps: Map<Stage, List<Step>> = emptyMap()
)
