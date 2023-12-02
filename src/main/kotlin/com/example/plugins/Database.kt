package com.example.plugins

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
	
	private lateinit var database: Database
	fun init(url: String, user: String, password: String) {
		database = Database.connect(
			url,
			user = user,
			password = password,
			driver = "org.mariadb.jdbc.Driver"
		)
		
		transaction {
			addLogger(StdOutSqlLogger)
			SchemaUtils.createMissingTablesAndColumns()
		}
		
		
		suspend fun <T> query(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO, database) {
			addLogger(StdOutSqlLogger)
			block()
		}
	}
}
