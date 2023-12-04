package com.example.plugins

import com.example.database.table.ChatHistoryTable
import com.example.database.table.ChatRoomTable
import com.example.database.table.SystemPromptTable
import com.example.database.table.UserTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
  
  private lateinit var database: Database
  fun init(url: String, user: String, password: String) {
    database = Database.connect(
      url,
      user = user,
      password = password,
      driver = "org.mariadb.jdbc.Driver",
      databaseConfig = DatabaseConfig.invoke { useNestedTransactions = true }
    )
    transaction {
      SchemaUtils.createMissingTablesAndColumns(
        UserTable,
        ChatRoomTable,
        ChatHistoryTable,
        SystemPromptTable,
      )
    }
  }
  
  suspend fun <T> query(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO, database) {
    block()
  }
}
