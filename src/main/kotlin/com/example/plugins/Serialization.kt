@file:OptIn(ExperimentalSerializationApi::class)

package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy.Builtins.SnakeCase

fun Application.configureSerialization() {
	install(ContentNegotiation) {
		json(
			Json {
				encodeDefaults = true
				isLenient = true
				prettyPrint = true
				namingStrategy = SnakeCase
			}
		)
	}
}
