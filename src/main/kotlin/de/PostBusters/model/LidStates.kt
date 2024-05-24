package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*
import java.time.LocalDate

object LidStates : Table<LidState>("t_lid_state") {
    val id = int("id").primaryKey().bindTo { it.id }
    val postboxId = int("postbox_id").references(PostBoxes) { it.postbox }
    val timestamp = date("timestamp")
    val isOpen = boolean("is_open")
}

interface LidState : Entity<LidState> {
    val id : Int
    val postbox : PostBox
    val timestamp : LocalDate
    val isOpen : Boolean
}

val Database.lidState get() = this.sequenceOf(LidStates)