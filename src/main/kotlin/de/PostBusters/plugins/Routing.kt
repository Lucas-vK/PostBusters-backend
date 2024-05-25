package de.PostBusters.plugins

import de.PostBusters.Db
import de.PostBusters.model.*
import de.PostBusters.routes.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime

fun Application.configureRouting() {
    data class CustomLogin(val username: String, val newLogin: String?, val newPassword: String?)
    routing {
        post("/{type}") {
            val login = call.receive<CustomLogin>()
            val db = Db.connect()
            val loggedInUser = db.users.single { it.login eq login.username }
            when (call.parameters["type"]?.toLowerCasePreservingASCIIRules()) {
                "postboxes" -> {
                    val ids = db.usersPostboxes.filter { pb -> pb.userId eq loggedInUser.id!! }
                        .filterColumns { it.columns - it.userId }.map { it.postboxId }
                    if (ids.isNotEmpty()) {
                        val postBoxes = db.postBoxes.filter { upb -> upb.id.inList(ids) }.toList()
                        postBoxes.forEach { pb ->
                            pb.LidState = db.lidStates.filter { ls -> (ls.postboxId eq pb.id!!) and (ls.timestamp greater LocalDateTime.now().minusDays(7)) }.toList()
                            pb.DoorState = db.doorStates.filter { ls -> (ls.postboxId eq pb.id!!) and (ls.timestamp greater LocalDateTime.now().minusDays(7)) }.toList()
                            pb.BatteryState = db.batteryStates.filter { ls -> (ls.postboxId eq pb.id!!) and (ls.timestamp greater LocalDateTime.now().minusDays(7)) }.toList()
                            pb.WeightSensorState = db.weightSensorStates.filter { ls -> (ls.postboxId eq pb.id!!) and (ls.timestamp greater LocalDateTime.now().minusDays(7)) }.toList()
                        }
                        call.respond(postBoxes)
                        return@post
                    } else {
                        call.respond(emptyList<PostBox>())
                        return@post
                    }
                }
                else -> call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/{type}/{postBoxId}") {
            val db = Db.connect()
            when (call.parameters["type"]?.toLowerCasePreservingASCIIRules()) {
                "lidstate" -> {
                    val pbId = call.parameters["postBoxId"]
                    try {
                        val intVal: Int = Integer.parseInt(pbId)
                        call.respond(db.lidStates.filter { it.postboxId eq intVal }.toList())
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }


        post("/put/{type}/{userName}") {
            val db = Db.connect()
            val user = call.parameters["userName"]
            val loggedInUser = db.users.single { it.login eq user!! }
            when (call.parameters["type"]?.toLowerCasePreservingASCIIRules()) {
                "postboxes" -> {
                    val newPb = call.receive<PostBox>()
                    db.postBoxes.add(newPb)
                    db.usersPostboxes.add(UserPostbox {
                        userId = loggedInUser.id!!
                        postboxId = newPb.id!!
                    })
                    call.respond(newPb)}
                "lidstates" -> {
                    val newLs = call.receive<LidState>()
                    db.lidStates.add(newLs)
                    call.respond(newLs)
                }
                else -> call.respond(HttpStatusCode.BadRequest)
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

        post("/change-login") {
            val login = call.receive<CustomLogin>()
            val db = Db.connect()
            val currentUser = db.users.single { it.login eq login.username }
            val existingUser = db.users.singleOrNull { user -> user.login eq login.newLogin!! }
            if (existingUser != null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            currentUser.login = login.newLogin!!
            currentUser.flushChanges()
            call.respond(HttpStatusCode.OK)
            return@post
        }

        post("/change-password") {
            val login = call.receive<CustomLogin>()
            val db = Db.connect()
            val currentUser = db.users.single { it.login eq login.username }
            currentUser.password = BCrypt.hashpw(login.newPassword!!, BCrypt.gensalt())
            currentUser.flushChanges()
            call.respond(HttpStatusCode.OK)
            return@post
        }

        sensorRouting()
    }
}
