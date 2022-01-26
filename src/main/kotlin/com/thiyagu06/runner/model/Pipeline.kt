package com.thiyagu06.runner.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class Command @JsonCreator constructor(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("command")
    val command: String,
    @JsonProperty("abortIfFailed")
    val abortIfFailed: Boolean = true
)

@RegisterForReflection
data class Pipeline @JsonCreator constructor(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("steps")
    val steps: Step
)

@RegisterForReflection
data class Step @JsonCreator constructor(
    @JsonProperty("setup")
    val setup: List<Command> = emptyList(),
    @JsonProperty("tearDown")
    val tearDown: List<Command> = emptyList()
)
