package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*

object User : Table<IUser>("t_users") {
    val id = int("id").primaryKey().bindTo { it.id }
    val login = text("login").bindTo { it.login }
    val email = text("email").bindTo { it.email }
    val name = text("name").bindTo { it.name }
    val surname = text("surname").bindTo { it.surname }
}

interface IUser : Entity<IUser> {
    companion object : Entity.Factory<IUser>()
    val id : Int
    val login : String
    val email : String
    val name : String
    val surname : String
}

val Database.user get() = this.sequenceOf(User)