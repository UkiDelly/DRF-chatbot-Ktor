package com.example.service

import com.example.common.ConflictException
import com.example.database.entity.UserEntity
import com.example.database.table.UserTable
import com.example.models.User
import com.example.models.reqeust.RegisterRequestDto
import com.example.plugins.DatabaseFactory.query
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory


@Single
class AccountService {
  
  private val logger = LoggerFactory.getLogger(this::class.java)
  
  suspend fun register(request: RegisterRequestDto): User {
    val newUser = query {
      userExist(request.email).run { if (this) throw ConflictException("이미 존재하는 유저입니다.") }
      UserEntity.new {
        nickname = request.nickname
        email = request.email
        socialType = request.socialType
        snsId = request.snsId
        password = request.password
      }
    }
    return User(newUser)
  }
  
  private fun userExist(email: String): Boolean {
    UserEntity.find { UserTable.email eq email }.firstOrNull().run {
      return this != null
    }
  }
}