package de.PostBusters.plugins

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import de.PostBusters.Db
import de.PostBusters.model.users
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.ktorm.dsl.eq
import org.ktorm.entity.singleOrNull
import org.ktorm.jackson.KtormModule
import org.mindrot.jbcrypt.BCrypt

data class UserSession(val userId: Int?) : Principal
fun Application.configureSecurity() {
    data class CustomLogin(val login: String, val password: String)
    install(Sessions) {
        cookie<UserSession>("POSTBUSTERS") {
            cookie.path = "/"
            cookie.extensions["SameSite"] = "lax"
        }
    }
    install(ContentNegotiation) {
        jackson {
            registerModule(KtormModule())
            registerModule(JavaTimeModule())
        }
    }
    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { session ->
                if(session.userId != null) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.response.status(HttpStatusCode.Unauthorized)
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
    routing {
        post("/login") {
            val login = call.receive<CustomLogin>()
            val db = Db.connect()
            val user = db.users.singleOrNull { user -> user.login eq login.login }
            if (user == null || !BCrypt.checkpw(login.password, user.password)) {
                call.response.status(HttpStatusCode.Unauthorized)
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }
            call.sessions.set(UserSession(user.id))
            call.respond(user)
        }
    }
}
