package com.freesoft.savings.infrastructure

import java.math.BigDecimal
import java.time.LocalDateTime

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

data class OpenSavingAccountReq(
    val accHolderType: AccHolderType,
    val accHolderKey: String,
    val name: String,
    val accType: AccType,
    val accruedInterest: BigDecimal,
    val maturityDate: LocalDateTime,
    val interestPmtPoint: InterestPmtPoint,
    val currency: Currency
)