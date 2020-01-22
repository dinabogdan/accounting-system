package com.freesoft.savings.application

import com.freesoft.savings.Router
import com.freesoft.savings.domain.repository.Repository
import com.freesoft.savings.domain.system.Configuration
import com.freesoft.savings.domain.system.FreesoftSystem
import com.freesoft.savings.infrastructure.database.AccountHolders
import com.freesoft.savings.infrastructure.database.Accounts
import com.freesoft.savings.infrastructure.database.TransactionManagerImpl
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

interface AccountingFreesoftSystem : FreesoftSystem {
    override val txManager: TransactionManager<Repository>

}

data class SavingsAccountFreesoftSystem(
    private val configuration: Configuration
) : AccountingFreesoftSystem {

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

    private val logger = LoggerFactory.getLogger(SavingsAccountFreesoftSystem::class.java)

    override val name: String
        get() = "Production Savings Account FreesoftSystem"

    override infix fun `start as server`(router: Router) {
        router(this).asServer(Jetty(configuration.serverPort)).start()
        logger.info("Server started on port ${configuration.serverPort}")
    }
}
