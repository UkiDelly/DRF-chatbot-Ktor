package com.example.openAi

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy.Builtins.SnakeCase
import org.slf4j.LoggerFactory

@OptIn(ExperimentalSerializationApi::class)
class OpenAiClient(private val token: String) {
  
  private val logger = LoggerFactory.getLogger("OpenAiClient")
  private val url: String = "https://api.openai.com/v1/chat/completions"
  private val gptModel: String = "gpt-3.5-turbo-1106"
  
  
  suspend fun sendChat(chats: List<BaseModel>) {
    
    val client = HttpClient(OkHttp) {
      install(ContentNegotiation) {
        json(Json {
          encodeDefaults = true
          isLenient = true
          prettyPrint = true
          namingStrategy = SnakeCase
        })
      }
      
      engine {
        config {
          build()
        }
      }
    }
    
    val jsonBuilder = Json {
      encodeDefaults = true
      isLenient = true
      prettyPrint = true
      namingStrategy = SnakeCase
    }
    
    
    
    logger.debug("chats: {}", jsonBuilder.encodeToString(chats))
    //   val res = client.post(url) {
    //     headers {
    //       append("Authorization", "Bearer $token")
    //       append("content-type", ContentType.Application.Json)
    //       build()
    //     }
    //
    //     setBody(OpenApiRequest(gptModel, chats))
    //   }
    //
    //   logger.debug("response: {}", res.bodyAsText())
  }
}