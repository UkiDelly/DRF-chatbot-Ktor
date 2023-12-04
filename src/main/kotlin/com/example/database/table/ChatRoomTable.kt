package com.example.database.table

object ChatRoomTable : BaseTimeTable("chat_room") {
  val name = varchar("name", length = 100)
  val userId = reference("user_id", UserTable)
}