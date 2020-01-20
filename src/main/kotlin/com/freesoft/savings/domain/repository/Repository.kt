package com.freesoft.savings.domain.repository

import com.freesoft.savings.domain.Account
import com.freesoft.savings.domain.AccountHolder

interface Repository {
    fun getAccountByHolderKey(key: String): Boolean

    fun addAccount(account: Account, holderKey: String)

    fun addHolder(holder: AccountHolder): String
}