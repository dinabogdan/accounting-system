package com.freesoft.savings.application

import com.freesoft.savings.domain.Account
import com.freesoft.savings.domain.AccountHolder
import com.freesoft.savings.domain.repository.Repository
import com.freesoft.savings.infrastructure.database.AccountHolders
import com.freesoft.savings.infrastructure.database.Accounts
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.math.BigDecimal
import java.time.LocalDateTime

class AccountRepository : Repository {
    override fun addHolder(holder: AccountHolder): String {
        val affectedRows = AccountHolders.insert {
            it[AccountHolders.accHolderKey] = holder.key.value
            it[AccountHolders.accHolderType] = holder.type.name
        }
        if (affectedRows.resultedValues?.size != 0) {
            return holder.key.value
        }
        throw IllegalArgumentException()
    }

    override fun getAccountByHolderKey(key: String): Boolean = Accounts.select {
        Accounts.holder eq key
    }.firstOrNull() != null

    override fun addAccount(account: Account, holderKey: String) {
        Accounts.insert {
            it[Accounts.id] = account.id.toString()
            it[Accounts.holder] = holderKey
            it[Accounts.accruedInterest] = account.accruedInterest?.value
            it[Accounts.name] = account.name.value
            it[Accounts.type] = account.type.name
            it[Accounts.state] = account.state.name
            it[Accounts.balance] = account.balance?.value ?: BigDecimal.ZERO
            if (account.maturityDate != null) {
                it[Accounts.maturityDate] = account.maturityDate!!.value.toString()
            } else it[Accounts.maturityDate] = LocalDateTime.now().toString()
            it[Accounts.interestPmtPoint] = account.interestPmtPoint.name
            it[Accounts.currencyCode] = account.currencyCode.name
        }
    }
}

