package com.example.common

class ConflictException(message: String) : RuntimeException(message)


class ReachedLimitException : RuntimeException("하루 이용 가능한 채팅 횟수에 도달했습니다.")