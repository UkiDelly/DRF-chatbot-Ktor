package com.example.plugins

import com.example.service.AccountService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
  install(Koin) {
    val services = module { AccountService() }
    modules(services)
  }
}