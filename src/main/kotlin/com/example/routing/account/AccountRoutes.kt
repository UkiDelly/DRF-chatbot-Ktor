package com.example.routing.account

import io.ktor.resources.*
import kotlinx.serialization.Serializable


@Serializable
@Resource("/account")
class AccountRoutes {
  
  @Serializable
  @Resource("login")
  class Login(val parent: AccountRoutes = AccountRoutes())
  
  @Serializable
  @Resource("register")
  class Register(val parent: AccountRoutes = AccountRoutes())
  
  
  @Serializable
  @Resource("auto-login")
  class AutoLogin(val parent: AccountRoutes = AccountRoutes())
  
  
  @Serializable
  @Resource("refresh")
  class RefreshToken(val parent: AccountRoutes = AccountRoutes(), val withUser: Boolean = false)
  
  
}