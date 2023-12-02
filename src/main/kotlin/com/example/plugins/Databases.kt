package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {
	
	private lateinit var database:Database
	fun init(url:String, user:String, password:String){
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
		
		
		suspend fun <T> query(block: ()->T):T = newSuspendedTransaction(Dispatchers.IO, database){
			addLogger(StdOutSqlLogger)
			block()
		}
	}
}
