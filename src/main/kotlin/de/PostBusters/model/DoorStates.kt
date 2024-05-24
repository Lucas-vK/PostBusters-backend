package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*
import java.time.LocalDate

object DoorStates : Table<DoorState>("t_door_state") {
    val id = int("id").primaryKey().bindTo { it.id }
    val postboxId = int("postbox_id").references(PostBoxes) { it.postbox }
    val timestamp = date("timestamp").bindTo { it.timestamp }
    val isOpen = boolean("is_open").bindTo { it.isOpen }
}

interface DoorState : Entity<DoorState> {
    val id : Int
    var postbox : PostBox
    var timestamp : LocalDate
    var isOpen : Boolean
}

val Database.doorStates get() = this.sequenceOf(DoorStates)