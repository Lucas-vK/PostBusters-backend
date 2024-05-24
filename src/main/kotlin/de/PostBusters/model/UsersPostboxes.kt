package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*

object UsersPostboxes : Table<IUserPostboxes>("t_users") {
    val postboxId = int("postbox_id").primaryKey().bindTo { it.postboxId }
    val userId = int("user_id").primaryKey().bindTo { it.userId }
}

interface IUserPostboxes : Entity<IUserPostboxes> {
    val postboxId : Int
    val userId : Int
}

val Database.userPostboxes get() = this.sequenceOf(UsersPostboxes)