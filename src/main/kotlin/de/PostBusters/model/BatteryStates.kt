package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*
import java.time.LocalDate

object BatteryStates : Table<BatteryState>("t_battery_state") {
    val id = int("id").primaryKey().bindTo { it.id }
    val postboxId = int("department_id").references(PostBoxes) { it.postbox }
    val timestamp = date("timestamp").bindTo { it.timestamp }
    val charge = int("charge").bindTo { it.charge }
}

interface BatteryState : Entity<BatteryState> {
    val id: Int
    var postbox: PostBox
    var timestamp: LocalDate
    var charge: Int
}

val Database.batteryStates get() = this.sequenceOf(BatteryStates)