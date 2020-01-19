package com.freesoft.savings.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

enum class AccountHolderType {
    CLIENT, GROUP
}

inline class AccountHolderKey(
    val value: String
)

inline class AccountName(
    val value: String
)

sealed class AccountHolder(
    val type: AccountHolderType,
    open val key: AccountHolderKey
)

data class ClientHolder(
    override val key: AccountHolderKey
) : AccountHolder(AccountHolderType.CLIENT, key)

data class GroupHolder(
    override val key: AccountHolderKey
) : AccountHolder(AccountHolderType.GROUP, key)

enum class AccountType { currentAccount, regularSavings, fixedDeposit, savingsPlan, investorAccount }

enum class AccountState { OPEN, CLOSED, PENDING }

inline class Balance(
    val value: BigDecimal
)

inline class AccruedInterest(
    val value: BigDecimal
)

inline class MaturityDate(
    val value: LocalDateTime
)

enum class InterestPaymentPoint {
    firstDayOfMonth,
    everyWeek,
    everyOtherWeek,
    everyMonth,
    every3Months,
    onFixedDate,
    onAccountMaturity
}

enum class CurrencyCode {
    EUR, RON, USD
}

sealed class Account(
    open val id: UUID,
    open val accountHolder: AccountHolder,
    open val name: AccountName,
    open val type: AccountType,
    open val state: AccountState,
    open val balance: Balance?,
    open val accruedInterest: AccruedInterest?,
    open val maturityDate: MaturityDate?,
    open val interestPmtPoint: InterestPaymentPoint,
    open val currencyCode: CurrencyCode
)

data class CurrentAccount(
    override val id: UUID,
    override val accountHolder: AccountHolder,
    override val name: AccountName,
    override val state: AccountState,
    override val balance: Balance?,
    override val accruedInterest: AccruedInterest?,
    override val interestPmtPoint: InterestPaymentPoint,
    override val currencyCode: CurrencyCode
) : Account(
    id,
    accountHolder,
    name,
    AccountType.currentAccount,
    state,
    balance,
    accruedInterest,
    null,
    interestPmtPoint,
    currencyCode
)

data class RegularSavingsAccount(
    override val id: UUID,
    override val accountHolder: AccountHolder,
    override val name: AccountName,
    override val state: AccountState,
    override val balance: Balance?,
    override val accruedInterest: AccruedInterest?,
    override val interestPmtPoint: InterestPaymentPoint,
    override val currencyCode: CurrencyCode
) : Account(
    id,
    accountHolder,
    name,
    AccountType.regularSavings,
    state,
    balance,
    accruedInterest,
    null,
    interestPmtPoint,
    currencyCode
)

data class FixedDepositAccount(
    override val id: UUID,
    override val accountHolder: AccountHolder,
    override val name: AccountName,
    override val state: AccountState,
    override val balance: Balance?,
    override val accruedInterest: AccruedInterest?,
    override val maturityDate: MaturityDate,
    override val interestPmtPoint: InterestPaymentPoint,
    override val currencyCode: CurrencyCode
) : Account(
    id,
    accountHolder,
    name,
    AccountType.fixedDeposit,
    state,
    balance,
    accruedInterest,
    maturityDate,
    interestPmtPoint,
    currencyCode
)

data class SavingsPlanAccount(
    override val id: UUID,
    override val accountHolder: AccountHolder,
    override val name: AccountName,
    override val state: AccountState,
    override val balance: Balance?,
    override val accruedInterest: AccruedInterest?,
    override val maturityDate: MaturityDate,
    override val interestPmtPoint: InterestPaymentPoint,
    override val currencyCode: CurrencyCode
) : Account(
    id,
    accountHolder,
    name,
    AccountType.savingsPlan,
    state,
    balance,
    accruedInterest,
    maturityDate,
    interestPmtPoint,
    currencyCode
)

data class InvestorAccount(
    override val id: UUID,
    override val accountHolder: AccountHolder,
    override val name: AccountName,
    override val state: AccountState,
    override val balance: Balance?,
    override val accruedInterest: AccruedInterest?,
    override val interestPmtPoint: InterestPaymentPoint,
    override val currencyCode: CurrencyCode
) : Account(
    id,
    accountHolder,
    name,
    AccountType.investorAccount,
    state,
    balance,
    accruedInterest,
    null,
    interestPmtPoint,
    currencyCode
)