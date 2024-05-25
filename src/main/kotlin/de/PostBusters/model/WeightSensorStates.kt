package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import java.math.BigDecimal
import java.time.LocalDateTime

object WeightSensorStates : Table<WeightSensorState>("t_weight_sensor_states") {
    val id = int("id").primaryKey().bindTo { it.id }
    val postboxId = int("postbox_id").bindTo { it.postboxId }
    val timestamp = datetime("timestamp").bindTo { it.timestamp }
    val weight = decimal("weight").bindTo { it.weight }
    val mac = text("mac").bindTo { it.mac }
}

interface WeightSensorState : Entity<WeightSensorState> {
    companion object : Entity.Factory<WeightSensorState>()
    val id: Int
    var postboxId: Int
    var timestamp: LocalDateTime
    var weight: BigDecimal
    var mac : String
}

val Database.weightSensorStates get() = this.sequenceOf(WeightSensorStates)