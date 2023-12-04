package com.example.models

import com.example.common.SocialType
import com.example.database.entity.UserEntity
import com.example.plugins.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class User(
  val id: Int,
  val nickname: String,
  val email: String,
  val socialType: SocialType,
  val snsId: String?,
  val chatCount: Int,
  val isActive: Boolean,
  val isAdmin: Boolean,
  @Serializable(with = LocalDateTimeSerializer::class)
  val lastLogin: LocalDateTime,
  @Serializable(with = LocalDateTimeSerializer::class)
  val createdAt: LocalDateTime,
  @Serializable(with = LocalDateTimeSerializer::class)
  val updatedAt: LocalDateTime
) {
  constructor(entity: UserEntity) : this(
    id = entity.id.value,
    nickname = entity.nickname,
    email = entity.email,
    socialType = entity.socialType,
    snsId = entity.snsId,
    chatCount = entity.chatCount,
    isActive = entity.isActive,
    isAdmin = entity.isAdmin,
    lastLogin = entity.lastLogin,
    createdAt = entity.createdAt,
    updatedAt = entity.updatedAt
  )
}
