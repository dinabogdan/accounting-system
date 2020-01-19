package com.freesoft.savings.application

import com.freesoft.savings.domain.Account
import com.freesoft.savings.domain.MaturityDate
import com.freesoft.savings.domain.repository.Repository
import com.freesoft.savings.infrastructure.database.AccountHolders
import com.freesoft.savings.infrastructure.database.Accounts
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.math.BigDecimal
import java.util.*

class AccountRepository : Repository {
    override fun getAccountByHolderKey(key: String): Boolean = Accounts.select {
        Accounts.holder eq (UUID.fromString(key))
    }.firstOrNull() != null

    override fun addAccount(account: Account) {
        Accounts.insert {
            it[Accounts.holder] = EntityID(UUID.fromString(account.accountHolder.key.value), AccountHolders)
            it[Accounts.accruedInterest] = account.accruedInterest?.value
            it[Accounts.name] = account.name.value
            it[Accounts.type] = account.type.name
            it[Accounts.state] = account.state.name
            it[Accounts.balance] = account.balance?.value ?: BigDecimal.ZERO
            if (account.maturityDate != null) {
                it[Accounts.maturityDate] = DateTime.parse(account.maturityDate!!.value.toString())
            }
            it[Accounts.interestPmtPoint] = account.interestPmtPoint.name
            it[Accounts.currencyCode] = account.currencyCode.name
        }
    }
}

