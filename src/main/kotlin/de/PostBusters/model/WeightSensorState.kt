package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*
import java.math.BigDecimal
import java.time.LocalDate

object WeightSensorState : Table<IWeightSensorState>("t_weight_sensor_state") {
    val id = int("id").primaryKey().bindTo { it.id }
    val postboxId = int("postbox_id").references(PostBoxes) { it.postbox }
    val timestamp = date("timestamp").bindTo { it.timestamp }
    val weight = decimal("is_open").bindTo { it.weight }
}

interface IWeightSensorState : Entity<IWeightSensorState> {
    val id: Int
    val postbox: PostBox
    val timestamp: LocalDate
    val weight: BigDecimal
}

val Database.weightSensorState get() = this.sequenceOf(WeightSensorState)