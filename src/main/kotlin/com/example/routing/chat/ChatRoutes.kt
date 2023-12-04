package com.example.routing.chat

import io.ktor.resources.*
import kotlinx.serialization.Serializable


@Serializable
@Resource("/chat")
class ChatRoutes {
  
  
  @Serializable
  @Resource("{id}")
  class Id(val parent: ChatRoutes = ChatRoutes(), val id: Int) {
    
    
    @Serializable
    @Resource("history")
    class History(val chatRoom: Id)
  }
}