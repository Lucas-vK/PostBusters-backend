package de.PostBusters.model

import org.ktorm.schema.Table
import org.ktorm.schema.*

object UsersPostboxes : Table<Nothing>("t_users") {
    val postboxId = int("postbox_id").primaryKey()
    val userId = int("user_id").primaryKey()
}