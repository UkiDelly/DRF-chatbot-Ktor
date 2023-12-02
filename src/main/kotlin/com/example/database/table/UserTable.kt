package com.example.database.table

import com.example.common.SocialType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserTable : IntIdTable("users") {
	val nickname = varchar("nickname", length = 100)
	val email = varchar("email", length = 255)
	val socialType = enumerationByName<SocialType>("social_type", 10)
	val snsId = varchar("sns_id", length = 255)
	val password = text("password").nullable()
	val chatCount = integer("chat_count").default(0)
	val isActive = bool("is_active").default(true)
	val isAdmin = bool("is_admin").default(false)
	val lastLogin = datetime("last_login").clientDefault { LocalDateTime.now() }
	val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
}