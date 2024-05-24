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
import org.ktorm.dsl.*
import org.ktorm.entity.add

fun Route.sensorRouting() {
    route("/sensor") {

        // get all lid entries of the specified box
        get("{box_id?}") {
            //authenticate("auth-session")
            val boxId = call.parameters["box_id"] ?: return@get call.respondText(
                "Missing box-id",
                status = HttpStatusCode.BadRequest
            )

            val db = Db.connect()
            val lidc = db.from(LidStates).select().where { LidStates.id eq boxId.toInt() }
            //val lidchanges = db.lidState.find { it.postboxId eq boxId.toInt() }


            call.respond(lidc)
            //val customer =
            //    customerStorage.find { it.id == id } ?: return@get call.respondText(
            //        "No customer with id $id",
            //        status = HttpStatusCode.NotFound
            //)
            //call.respond(customer)
        }


        //get{
        //    if (customerStorage.isNotEmpty()) {
        //        call.respond(customerStorage)
        //    } else {
        //        call.respondText("No customers found", status = HttpStatusCode.OK)
        //    }
        //}
    }

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

    route("/update-batteryState") {
        // add a battery-state for the specified box
        post() {
            val newBatteryState = call.receive<BatteryState>()
            val db = Db.connect()
            db.batteryStates.add(newBatteryState)
            call.respond(HttpStatusCode.OK)
            return@post
        }
    }

    route("/update-weightState") {
        // add a weight-state for the specified box
        post() {
            val newWeightState = call.receive<WeightSensorState>()
            val db = Db.connect()
            db.weightSensorStates.add(newWeightState)
            call.respond(HttpStatusCode.OK)
            return@post
        }
    }
}