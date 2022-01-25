package com.thiyagu06.installer.model

data class Pipeline(val name: String, val description: String, val steps: Map<String, Step> = emptyMap())


data class Step(
    val description: String? = null,
    val setup: List<Command> = emptyList(),
    val tearDown: List<Command> = emptyList()
)

data class Command(val command: String, val onFailure: String? = null)
