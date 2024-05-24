package de.PostBusters.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.*

object Users : Table<User>("t_users") {
    val id = int("id").primaryKey().bindTo { it.id }
    val login = text("login").bindTo { it.login }
    val password = text("password").bindTo { it.password }
    val email = text("email").bindTo { it.email }
    val name = text("name").bindTo { it.name }
    val surname = text("surname").bindTo { it.surname }
}

interface User : Entity<User> {
    companion object : Entity.Factory<User>()
    val id : Int?
    var login : String
    var password : String
    var email : String
    var name : String
    var surname : String
}

val Database.users get() = this.sequenceOf(Users)