package de.PostBusters.plugins

import de.PostBusters.Db
import de.PostBusters.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.*
import org.mindrot.jbcrypt.BCrypt

fun Application.configureRouting() {
    routing {
        authenticate("auth-session") {
            get("/{type}") {
                val session = call.principal<UserSession>()
                val db = Db.connect()
                if (call.parameters["type"] == "PostBoxes")
                {
                    val ids = db.usersPostboxes.filter { pb -> pb.userId eq session!!.userId!! }
                        .filterColumns { it.columns - it.userId }.map { it.postboxId }
                    if (ids.isNotEmpty()) {
                        db.postBoxes.filter { upb -> upb.id.inList(ids) }.toList()
                        call.respond(db.postBoxes.filter { upb -> upb.id.inList(ids) }.toList())
                    } else {
                        call.respond(emptyList<PostBox>())
                    }
                }
            }
            post("/put/{type}") {
                val session = call.principal<UserSession>()
                val db = Db.connect()
                if (call.parameters["type"] == "PostBoxes") {
                    val newPb = call.receive<PostBox>()
                    db.postBoxes.add(newPb)
                    db.usersPostboxes.add(UserPostbox {
                        userId = session!!.userId!!
                        postboxId = newPb.id!!
                    })
                    call.respond(newPb)
                }
            }
        }
        post("create-user") {
            val newUser = call.receive<User>()
            val db = Db.connect()
            val user = db.users.singleOrNull { user -> user.login eq newUser.login }
            if (user == null) {
                newUser.password = BCrypt.hashpw(newUser.password, BCrypt.gensalt())
                db.users.add(newUser)
                call.respond(newUser)
                return@post
            }
            call.response.status(HttpStatusCode.Conflict)
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
    }
}
