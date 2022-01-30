package com.thiyagu06.runner.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
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
    @JsonProperty("workflows")
    val workflows: Map<String, List<Step>> = emptyMap()
)
