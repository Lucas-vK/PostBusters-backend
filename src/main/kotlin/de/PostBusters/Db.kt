package de.PostBusters

import org.ktorm.database.Database

class Db {
    companion object {
        val connectionString: String = "jdbc:postgresql://localhost:5432/postgres"
        val user: String = "postgres"
        val password: String = "root"
        fun connect() : Database {
            return Database.connect(connectionString, user = user, password = password)
        }
    }

}