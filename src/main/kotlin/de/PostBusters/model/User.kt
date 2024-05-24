package de.PostBusters.model

import org.ktorm.schema.Table
import org.ktorm.schema.*

object User : Table<Nothing>("t_users") {
    val id = int("id").primaryKey()
    val login = text("login")
    val email = text("email")
    val name = text("name")
    val surname = text("surname")
}