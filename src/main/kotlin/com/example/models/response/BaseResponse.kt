package com.example.models.response

import kotlinx.serialization.Serializable


@Serializable
data class SuccessResponse<T>(val data: T, val message: String = "성공")
@Serializable
data class ErrorResponse<T>(val error: T, val message: String = "실패")