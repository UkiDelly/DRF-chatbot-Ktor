package com.example.models.response

import com.example.common.Token
import com.example.models.User
import kotlinx.serialization.Serializable


@Serializable
class UserInfoWithTokenResponseDto(val user: User, val token: Token)