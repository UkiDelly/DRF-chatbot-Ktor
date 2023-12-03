package com.example.models.reqeust

import com.example.common.SocialType
import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequestDto(
	val nickname: String,
	val email: String,
	val socialType: SocialType,
	val snsId: String?,
	val password: String?
)


fun RegisterRequestDto.isValidEmail(): Boolean {
	val pattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
	return pattern.matches(this.email)
}
