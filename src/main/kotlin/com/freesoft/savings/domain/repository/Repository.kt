package com.freesoft.savings.domain.repository

import com.freesoft.savings.domain.model.Account
import com.freesoft.savings.domain.model.AccountHolder

interface Repository {
    fun getAccountByHolderKey(key: String): Account?

    fun addAccount(account: Account, holderKey: String)

    fun addHolder(holder: AccountHolder): String
}