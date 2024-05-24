package de.PostBusters.plugins

import de.PostBusters.Db
import de.PostBusters.model.user
import io.ktor.http.*
import io.ktor.network.sockets.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.singleOrNull
import org.ktorm.jackson.KtormModule

fun Application.configureSecurity() {
    data class MySession(val id: Int = 0)
    data class CustomLogin(val login: String, val password: String)
    install(Sessions) {
        cookie<MySession>("POSTBUSTERS") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
    install(ContentNegotiation) {
        jackson {
            registerModule(KtormModule())
        }
    }
    routing {
        post("/login") {
            val text = call.receive<CustomLogin>()
            val db = Db.connect()
            val user = db.user.singleOrNull { user -> user.login eq text.login}
            if (user == null) {
                call.response.status(HttpStatusCode.Unauthorized)
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }
            call.respond(user)
        }
    }
}
