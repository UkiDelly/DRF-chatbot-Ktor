package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.common.TokenType
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.slf4j.LoggerFactory

fun Route.withAuth(tokenType: TokenType, route: Route.() -> Unit): Route {
	val child = createChild(AuthorizateRouteSelector()).apply {
		install(JwtVerifierRoutePlugin) {
			type = tokenType
			route()
		}
	}
	return child
}

class AuthorizateRouteSelector : RouteSelector() {
	override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
		RouteSelectorEvaluation.Transparent
	
	override fun toString(): String = "( Token Required )"
	
}


class JwtVerifierRoutePluginConfig {
	lateinit var type: TokenType
}

private val JwtVerifierRoutePlugin =
	createRouteScopedPlugin("jwt-verification", createConfiguration = ::JwtVerifierRoutePluginConfig) {
		
		val config = environment?.config !!
		val logger = LoggerFactory.getLogger("JwtVerifierRoutePlugin")
		
		pluginConfig.apply {
			val jwtAudience = config.property("jwt.audience").getString()
			val jwtIssuer = config.property("jwt.issuer").getString()
			val jwtSecret = config.property("jwt.secret").getString()
			
			val jwtVerifier = JWT
				.require(Algorithm.HMAC256(jwtSecret))
				.withAudience(jwtAudience)
				.withIssuer(jwtIssuer)
				.build()
			
			onCall { call ->
				// 토큰이 존재하지 않을때
				if (call.request.header("Authorization") == null) {
					call.respond(HttpStatusCode.Unauthorized, "Authorization header is missing")
					return@onCall
				}
				
				// 토큰 검증
				val token = call.request.authorization() !!.split(" ").last()
				val credential = jwtVerifier.verify(token)
				val tokenType = TokenType.valueOf(credential.getClaim("type").asString())
				
				// 토큰 타입이 일치하지 않을때
				if (type != tokenType) {
					call.respond(HttpStatusCode.Unauthorized, "Invalid token type")
					return@onCall
				}
				
				val userId = credential.subject.toInt()
				call.attributes.put(userIdKey, userId)
			}
		}
	}


private val userIdKey = AttributeKey<Int>("userId")
fun ApplicationCall.getUserId() = attributes[userIdKey]