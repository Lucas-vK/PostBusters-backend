package de.PostBusters.model

import org.ktorm.schema.Table
import org.ktorm.schema.*

object BatteryState : Table<Nothing>("t_battery_state") {
    val id = int("id").primaryKey()
    val postboxId = int("postbox_id")
    val timestamp = date("timestamp")
    val charge = int("charge")
}