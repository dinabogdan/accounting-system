package com.freesoft.savings.infrastructure.database

import com.freesoft.savings.application.AccountRepository
import com.freesoft.savings.application.TransactionManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class TransactionManagerImpl(
    private val database: Database,
    private val repository: AccountRepository
) : TransactionManager<AccountRepository> {
    override fun <T> tx(block: AccountRepository.() -> T): T =
        transaction(database) {
            block(repository)
        }
}