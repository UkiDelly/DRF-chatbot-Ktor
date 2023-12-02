package com.example.database.entity

import com.example.database.table.UserTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
	
	var nickname by UserTable.nickname
	var email by UserTable.email
	var socialType by UserTable.socialType
	var snsId by UserTable.snsId
	var password by UserTable.password
	var chatCount by UserTable.chatCount
	var isActive by UserTable.isActive
	var isAdmin by UserTable.isAdmin
	var lastLogin by UserTable.lastLogin
	var createdAt by UserTable.createdAt
	
}