package de.PostBusters.routes

import de.PostBusters.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import de.PostBusters.Db
import de.PostBusters.model.lidStates
import de.PostBusters.model.doorStates
import de.PostBusters.model.batteryStates
import de.PostBusters.model.weightSensorStates
import org.ktorm.entity.add
import java.time.LocalDateTime

fun Route.sensorRouting() {

    route("/update-lidState") {
        // add a lid-state for the specified box
        post() {
            val newLidState = call.receive<LidState>()
            val db = Db.connect()
            db.lidStates.add(newLidState)
            call.respond(HttpStatusCode.OK)
            return@post
        }
    }

    route("/update-doorState") {
        // add a door-state for the specified box
        post() {
            val newDoorState = call.receive<DoorState>()
            val db = Db.connect()
            db.doorStates.add(newDoorState)
            call.respond(HttpStatusCode.OK)
            return@post
        }
    }

    route("/update-weightState") {
        // add a weight-state for the specified box
        post() {
            val newWeightState = call.receiveText()
            val db = Db.connect()
            db.weightSensorStates.add(WeightSensorState {
                weight = newWeightState.toBigDecimal()
                timestamp = LocalDateTime.now()

            })
            call.respond(HttpStatusCode.OK)
            return@post
        }
    }
}