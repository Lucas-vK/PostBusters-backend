package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*

object PostBoxes : Table<PostBox>("t_users") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = text("login").bindTo { it.name }
    val address = text("email").bindTo { it.address }
    val notes = text("name").bindTo { it.notes }
}

interface PostBox : Entity<PostBox> {
    val id : Int
    val name : String
    val address : String
    val notes : String
}

val Database.postBox get() = this.sequenceOf(PostBoxes)