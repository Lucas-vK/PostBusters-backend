package de.PostBusters.model

import org.ktorm.schema.Table
import org.ktorm.schema.*

object DoorState : Table<Nothing>("t_door_state") {
    val id = int("id").primaryKey()
    val postboxId = int("postbox_id")
    val timestamp = date("timestamp")
    val isOpen = int("is_open")
}