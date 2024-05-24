package de.PostBusters

import de.PostBusters.model.user
import de.PostBusters.plugins.configureRouting
import de.PostBusters.plugins.configureSecurity
import io.ktor.server.application.*
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.forEach
import org.ktorm.entity.singleOrNull

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureRouting()
}
