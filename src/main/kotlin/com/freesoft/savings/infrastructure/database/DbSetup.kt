package com.freesoft.savings.infrastructure.database

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object Accounts : Table("accounts") {
    val id = varchar("id", 255).primaryKey()
    val holder = varchar("holder", 255).uniqueIndex().references(AccountHolders.accHolderKey)
    val name = varchar("name", 255)
    val type = varchar("type", 255)
    val state = varchar("state", 255)
    val balance = decimal("balance", 9, 2).nullable()
    val accruedInterest = decimal("accrued_interest", 9, 2).nullable()
    val maturityDate = varchar("maturity_date", 255).nullable()
    val interestPmtPoint = varchar("interest_pmt_point", 255)
    val currencyCode = varchar("currency_code", 3)
}

object AccountHolders : IntIdTable() {
    val accHolderType = varchar("acc_holder_type", 255)
    val accHolderKey = varchar("acc_holder_key", 255).uniqueIndex()
}

fun createDb(url: String, driver: String): Database {
    val database = Database.connect(url, driver = driver)
    transaction(database) {
        SchemaUtils.create(AccountHolders)
        SchemaUtils.create(Accounts)
    }
    return database
}