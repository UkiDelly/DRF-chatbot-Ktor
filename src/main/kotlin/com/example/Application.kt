package com.example

import com.example.plugins.*
import com.example.plugins.DatabaseFactory.init
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)


fun Application.module() {
  val url = environment.config.property("database.url").getString()
  val user = environment.config.property("database.user").getString()
  val password = environment.config.property("database.password").getString()
  init(url, user, password)
  
  configureKoin()
  configureMonitoring()
  configureSerialization()
  configureSockets()
  configureRouting()
  configureRequestValidation()
  configureStatusPage()
}
