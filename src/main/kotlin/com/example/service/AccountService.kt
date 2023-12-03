package com.example.service

import com.example.common.ConflictException
import com.example.common.SocialType
import com.example.database.entity.UserEntity
import com.example.database.table.UserTable
import com.example.models.User
import com.example.models.reqeust.RegisterRequestDto
import com.example.plugins.DatabaseFactory.query
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.and
import org.koin.core.annotation.Single
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.LoggerFactory


@Single
class AccountService {
  
  private val logger = LoggerFactory.getLogger(this::class.java)
  
  suspend fun register(request: RegisterRequestDto): User {
    val hashedPassword = BCrypt.hashpw(request.password, "$2a$10$")
    val newUser = query {
      UserEntity.find { UserTable.email eq request.email }.firstOrNull()
        .also { if (it != null) throw ConflictException("이미 존재하는 유저입니다.") }
      UserEntity.new {
        nickname = request.nickname
        email = request.email
        socialType = request.socialType
        snsId = request.snsId
        password = hashedPassword
      }
    }
    return User(newUser)
  }
  
  suspend fun findSocialUser(snsId: String): User {
    val userEntity = query {
      UserEntity.find { (UserTable.snsId eq snsId) and (UserTable.socialType eq SocialType.google) }.firstOrNull()
    }
      ?: throw NotFoundException("유저가 존재하지 않습니다.")
    return User(userEntity)
  }
  
  suspend fun findEmailUser(email: String, password: String): User {
    val hashedPassword = BCrypt.hashpw(password, "$2a$10$")
    val userEntityList = query { UserEntity.find { (UserTable.email eq email) }.toList() }.also {
      if (it.isEmpty()) throw NotFoundException("유저가 존재하지 않습니다.")
    }
    for (userEntity in userEntityList) {
      if (userEntity.password == hashedPassword) {
        return User(userEntity)
      }
    }
    throw NotFoundException("비밀번호가 맞지 않습니다.")
  }
}