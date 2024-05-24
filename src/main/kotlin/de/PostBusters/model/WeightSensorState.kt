package de.PostBusters.model

import org.ktorm.schema.Table
import org.ktorm.schema.*

object WeightSensorState : Table<Nothing>("t_weight_sensor_state") {
    val id = int("id").primaryKey()
    val postboxId = int("postbox_id")
    val timestamp = date("timestamp")
    val weight = decimal("is_open")
}