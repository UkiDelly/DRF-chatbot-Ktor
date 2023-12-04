package com.example.common

import kotlinx.serialization.Serializable


@Serializable
enum class SocialType {
  email,
  google
}


@Serializable
enum class Role {
  user,
  assistant
}