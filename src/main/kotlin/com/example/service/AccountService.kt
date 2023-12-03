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
      UserEntity.find { UserTable.email eq request.email }.firstOrNull() ?: throw ConflictException("이미 존재하는 유저입니다.")
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
  
  private fun findUser(email: String): User? {
    val userEntity = UserEntity.find { UserTable.email eq email }.firstOrNull()
    return userEntity?.let { User(it) }
  }
}