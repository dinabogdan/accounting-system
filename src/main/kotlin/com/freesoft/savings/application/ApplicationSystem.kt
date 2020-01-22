package com.freesoft.savings.application

import com.freesoft.savings.Router
import com.freesoft.savings.domain.repository.Repository
import com.freesoft.savings.domain.system.Configuration
import com.freesoft.savings.domain.system.FreesoftSystem
import com.freesoft.savings.infrastructure.database.AccountHolders
import com.freesoft.savings.infrastructure.database.Accounts
import com.freesoft.savings.infrastructure.database.TransactionManagerImpl
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory


/**
 * The base class which should be extended by all system implementation.
 */

abstract class AccountingFreesoftSystem(
    open val configuration: Configuration
) : FreesoftSystem {

    override val txManager: TransactionManager<Repository> by lazy {
        val db = initDb(configuration.dbUrl, configuration.dbDriver)
        TransactionManagerImpl(db, AccountRepository())
    }

    fun initDb(url: String, driver: String): Database {
        val database = Database.connect(url, driver = driver)
        transaction(database) {
            SchemaUtils.create(AccountHolders)
            SchemaUtils.create(Accounts)
        }
        return database
    }

    private val logger = LoggerFactory.getLogger(AccountingFreesoftSystem::class.java)

    override infix fun `start as server`(router: Router): Http4kServer {
        val server = router(this).asServer(Jetty(configuration.serverPort)).start()

        logger.info("Server started on port ${configuration.serverPort} by system $name")

        return server
    }

}

data class SavingsAccountFreesoftSystem(
    override val configuration: Configuration
) : AccountingFreesoftSystem(configuration) {

    override val name: String
        get() = "Production Savings Account FreesoftSystem"

}
