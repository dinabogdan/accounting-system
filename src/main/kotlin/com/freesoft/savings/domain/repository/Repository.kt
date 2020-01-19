package com.freesoft.savings.domain.repository

import com.freesoft.savings.domain.Account

interface Repository {
    fun getAccountByHolderKey(key: String): Boolean

    fun addAccount(account: Account)
}