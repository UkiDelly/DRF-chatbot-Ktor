package com.example.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

open class BaseTimeTable(override val tableName: String) : IntIdTable(tableName) {
	val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
	val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
}