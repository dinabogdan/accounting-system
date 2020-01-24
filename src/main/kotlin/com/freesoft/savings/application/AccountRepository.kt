package com.freesoft.savings.application

import com.freesoft.savings.domain.model.*
import com.freesoft.savings.domain.repository.Repository
import com.freesoft.savings.infrastructure.database.AccountHolders
import com.freesoft.savings.infrastructure.database.Accounts
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

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

    override fun getAccountByHolderKey(key: String): Account? {
        return Accounts.select { Accounts.holder eq key }
            .toList()
            .map { it.toAccount() }
            .firstOrNull()
    }

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

private fun ResultRow.toAccount(): Account {
    return when (AccountType.valueOf(this[Accounts.type])) {
        AccountType.currentAccount -> this.toCurrentAccount()
        AccountType.regularSavings -> this.toRegularSavings()
        AccountType.fixedDeposit -> this.toFixedDeposit()
        AccountType.savingsPlan -> this.toSavingsPlanAccount()
        AccountType.investorAccount -> this.toInvestorAccount()
    }
}

private fun ResultRow.toInvestorAccount(): InvestorAccount {
    return InvestorAccount(
        id = UUID.fromString(this[Accounts.id]),
        currencyCode = CurrencyCode.valueOf(this[Accounts.currencyCode]),
        interestPmtPoint = InterestPaymentPoint.valueOf(this[Accounts.interestPmtPoint]),
        accruedInterest = AccruedInterest(
            this[Accounts.accruedInterest] ?: BigDecimal.ZERO
        ),
        state = AccountState.valueOf(this[Accounts.state]),
        name = AccountName(this[Accounts.name]),
        accountHolder = ClientHolder(
            AccountHolderKey(
                this[Accounts.holder]
            )
        ),
        balance = Balance(this[Accounts.balance] ?: BigDecimal.ZERO)
    )
}

private fun ResultRow.toSavingsPlanAccount(): SavingsPlanAccount {
    return SavingsPlanAccount(
        id = UUID.fromString(this[Accounts.id]),
        currencyCode = CurrencyCode.valueOf(this[Accounts.currencyCode]),
        interestPmtPoint = InterestPaymentPoint.valueOf(this[Accounts.interestPmtPoint]),
        accruedInterest = AccruedInterest(
            this[Accounts.accruedInterest] ?: BigDecimal.ZERO
        ),
        state = AccountState.valueOf(this[Accounts.state]),
        name = AccountName(this[Accounts.name]),
        accountHolder = ClientHolder(
            AccountHolderKey(
                this[Accounts.holder]
            )
        ),
        balance = Balance(this[Accounts.balance] ?: BigDecimal.ZERO),
        maturityDate = MaturityDate(LocalDateTime.parse(this[Accounts.maturityDate]))
    )
}

private fun ResultRow.toFixedDeposit(): FixedDepositAccount {
    return FixedDepositAccount(
        id = UUID.fromString(this[Accounts.id]),
        currencyCode = CurrencyCode.valueOf(this[Accounts.currencyCode]),
        interestPmtPoint = InterestPaymentPoint.valueOf(this[Accounts.interestPmtPoint]),
        accruedInterest = AccruedInterest(
            this[Accounts.accruedInterest] ?: BigDecimal.ZERO
        ),
        state = AccountState.valueOf(this[Accounts.state]),
        name = AccountName(this[Accounts.name]),
        accountHolder = ClientHolder(
            AccountHolderKey(
                this[Accounts.holder]
            )
        ),
        balance = Balance(this[Accounts.balance] ?: BigDecimal.ZERO),
        maturityDate = MaturityDate(LocalDateTime.parse(this[Accounts.maturityDate]))
    )
}

private fun ResultRow.toRegularSavings(): RegularSavingsAccount {
    return RegularSavingsAccount(
        id = UUID.fromString(this[Accounts.id]),
        currencyCode = CurrencyCode.valueOf(this[Accounts.currencyCode]),
        interestPmtPoint = InterestPaymentPoint.valueOf(this[Accounts.interestPmtPoint]),
        accruedInterest = AccruedInterest(
            this[Accounts.accruedInterest] ?: BigDecimal.ZERO
        ),
        state = AccountState.valueOf(this[Accounts.state]),
        name = AccountName(this[Accounts.name]),
        accountHolder = ClientHolder(
            AccountHolderKey(
                this[Accounts.holder]
            )
        ),
        balance = Balance(this[Accounts.balance] ?: BigDecimal.ZERO)
    )
}

private fun ResultRow.toCurrentAccount(): CurrentAccount {
    return CurrentAccount(
        id = UUID.fromString(this[Accounts.id]),
        currencyCode = CurrencyCode.valueOf(this[Accounts.currencyCode]),
        interestPmtPoint = InterestPaymentPoint.valueOf(this[Accounts.interestPmtPoint]),
        accruedInterest = AccruedInterest(
            this[Accounts.accruedInterest] ?: BigDecimal.ZERO
        ),
        state = AccountState.valueOf(this[Accounts.state]),
        name = AccountName(this[Accounts.name]),
        accountHolder = ClientHolder(
            AccountHolderKey(
                this[Accounts.holder]
            )
        ),
        balance = Balance(this[Accounts.balance] ?: BigDecimal.ZERO)
    )
}

