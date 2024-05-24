package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*

object UsersPostboxes : Table<UserPostbox>("t_users_postboxes") {
    val postboxId = int("postbox_id").primaryKey().bindTo { it.postboxId }
    val userId = int("user_id").primaryKey().bindTo { it.userId }
}

interface UserPostbox : Entity<UserPostbox> {
    companion object : Entity.Factory<UserPostbox>()
    var postboxId : Int
    var userId : Int
}

val Database.usersPostboxes get() = this.sequenceOf(UsersPostboxes)