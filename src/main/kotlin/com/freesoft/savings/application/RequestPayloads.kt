package com.freesoft.savings.application

import com.freesoft.savings.domain.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

enum class AccHolderType { CLIENT, GROUP }

enum class AccType {
    currentAccount,
    regularSavings,
    fixedDeposit,
    savingsPlan,
    investorAccount
}

enum class InterestPmtPoint {
    firstDayOfMonth,
    everyWeek,
    everyOtherWeek,
    everyMonth,
    every3Months,
    onFixedDates,
    onAccountMaturity
}

enum class Currency {
    EUR, USD, RON
}

data class RetrieveSavingAccountReq(
    val accHolderKey: String
)

data class OpenSavingAccountReq(
    val accHolderType: AccHolderType,
    val accHolderKey: String,
    val name: String,
    val accType: AccType,
    val accruedInterest: BigDecimal,
    val maturityDate: LocalDateTime?,
    val interestPmtPoint: InterestPmtPoint,
    val currency: Currency
) {

    fun toAccount(): Account =
        when (accType) {
            AccType.currentAccount -> aCurrentAccount()
            AccType.fixedDeposit -> aFixedDepositAccount()
            AccType.investorAccount -> anInvestorAccount()
            AccType.regularSavings -> aRegularSavingsAccount()
            AccType.savingsPlan -> aSavingsPlanAccount()
        }

    private fun aSavingsPlanAccount(): Account {
        return SavingsPlanAccount(
            id = UUID.randomUUID(),
            accountHolder = anAccountHolder(),
            name = AccountName(this.name),
            state = AccountState.PENDING,
            accruedInterest = AccruedInterest(this.accruedInterest),
            interestPmtPoint = InterestPaymentPoint.valueOf(this.interestPmtPoint.name),
            currencyCode = CurrencyCode.valueOf(this.currency.name),
            maturityDate = MaturityDate(this.maturityDate!!)
        )
    }

    private fun aRegularSavingsAccount(): Account {
        return RegularSavingsAccount(
            id = UUID.randomUUID(),
            accountHolder = anAccountHolder(),
            name = AccountName(this.name),
            state = AccountState.PENDING,
            accruedInterest = AccruedInterest(this.accruedInterest),
            interestPmtPoint = InterestPaymentPoint.valueOf(this.interestPmtPoint.name),
            currencyCode = CurrencyCode.valueOf(this.currency.name)
        )
    }

    private fun anInvestorAccount(): Account {
        return InvestorAccount(
            id = UUID.randomUUID(),
            accountHolder = anAccountHolder(),
            name = AccountName(this.name),
            state = AccountState.PENDING,
            accruedInterest = AccruedInterest(this.accruedInterest),
            interestPmtPoint = InterestPaymentPoint.valueOf(this.interestPmtPoint.name),
            currencyCode = CurrencyCode.valueOf(this.currency.name)
        )
    }

    private fun aFixedDepositAccount(): Account {
        return FixedDepositAccount(
            id = UUID.randomUUID(),
            accountHolder = anAccountHolder(),
            name = AccountName(this.name),
            state = AccountState.PENDING,
            accruedInterest = AccruedInterest(this.accruedInterest),
            interestPmtPoint = InterestPaymentPoint.valueOf(this.interestPmtPoint.name),
            currencyCode = CurrencyCode.valueOf(this.currency.name),
            maturityDate = MaturityDate(this.maturityDate!!)
        )
    }

    private fun aCurrentAccount(): Account {
        return CurrentAccount(
            id = UUID.randomUUID(),
            accountHolder = anAccountHolder(),
            name = AccountName(this.name),
            state = AccountState.PENDING,
            accruedInterest = AccruedInterest(this.accruedInterest),
            interestPmtPoint = InterestPaymentPoint.valueOf(this.interestPmtPoint.name),
            currencyCode = CurrencyCode.valueOf(this.currency.name)
        )
    }

    private fun anAccountHolder(): AccountHolder {
        return when (this.accHolderType) {
            AccHolderType.CLIENT -> ClientHolder(AccountHolderKey(this.accHolderKey))
            AccHolderType.GROUP -> GroupHolder(AccountHolderKey(this.accHolderKey))
        }
    }
}