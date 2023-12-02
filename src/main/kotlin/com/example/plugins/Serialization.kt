@file:OptIn(ExperimentalSerializationApi::class)

package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy.Builtins.SnakeCase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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


@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
	override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)
	
	
	override fun deserialize(decoder: Decoder): LocalDateTime {
		val formattedValue = decoder.decodeString()
		return LocalDateTime.parse(formattedValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	}
	
	override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: LocalDateTime) {
		val formattedValue = value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
		encoder.encodeString(formattedValue)
	}
	
	
}
