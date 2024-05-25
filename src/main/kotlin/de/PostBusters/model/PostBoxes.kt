package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*

object PostBoxes : Table<PostBox>("t_postboxes") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = text("name").bindTo { it.name }
    val address = text("address").bindTo { it.address }
    val notes = text("notes").bindTo { it.notes }
}

interface PostBox : Entity<PostBox> {
    val id : Int?
    var name : String
    var address : String
    var notes : String
    var BatteryStates: List<BatteryState>
    var DoorState: List<DoorState>
    var LidState: List<LidState>
    var WeightSensorState: List<WeightSensorStates>
}

val Database.postBoxes get() = this.sequenceOf(PostBoxes)