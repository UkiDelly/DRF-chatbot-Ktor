package com.example.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object SystemPromptTable : IntIdTable("system_prompt") {
  val prompt = text("prompt")
  val chatRoomId = reference("chat_room_id", ChatRoomTable)
  val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
}