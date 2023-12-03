package com.example.plugins

import com.example.models.reqeust.LoginRequestDto
import com.example.models.reqeust.RegisterRequestDto
import com.example.models.reqeust.isValidEmail
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*


fun Application.configureRequestValidation() {
  install(RequestValidation) {
    validate<RegisterRequestDto> {
      
      if (! it.isValidEmail()) {
        ValidationResult.Invalid("이메일 형식이 맞지 않습니다.")
      }
      ValidationResult.Valid
    }
    
    validate<LoginRequestDto> {
      if (! it.isValidEmail()) {
        ValidationResult.Invalid("이메일 형식이 맞지 않습니다.")
      }
      ValidationResult.Valid
    }
  }
  
}