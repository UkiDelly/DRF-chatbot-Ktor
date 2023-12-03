package com.example.models.response

import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse<T>(val error: T, val message: String = "실패")