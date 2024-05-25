package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*
import java.time.LocalDateTime

object BatteryStates : Table<BatteryState>("t_battery_states") {
    val id = int("id").primaryKey().bindTo { it.id }
    val postboxId = int("postbox_id").bindTo { it.postboxId }
    val timestamp = datetime("timestamp").bindTo { it.timestamp }
    val charge = int("charge").bindTo { it.charge }
}

interface BatteryState : Entity<BatteryState> {
    val id: Int
    var postboxId: Int
    var timestamp: LocalDateTime
    var charge: Int
}

val Database.batteryStates get() = this.sequenceOf(BatteryStates)