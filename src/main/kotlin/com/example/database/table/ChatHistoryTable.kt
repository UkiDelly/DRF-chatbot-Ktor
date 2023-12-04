package com.example.database.table

import com.example.common.Role


object ChatHistoryTable : BaseTimeTable("chat_history") {
  val chatRoomId = reference("chat_room_id", ChatRoomTable)
  val message = text("message")
  val role = enumerationByName<Role>("role", 11)
}