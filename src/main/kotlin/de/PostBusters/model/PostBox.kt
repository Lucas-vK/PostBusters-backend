package de.PostBusters.model

import org.ktorm.schema.Table
import org.ktorm.schema.*

object PostBox : Table<Nothing>("t_users") {
    val id = int("id").primaryKey()
    val name = text("login")
    val address = text("email")
    val notes = text("name")
}