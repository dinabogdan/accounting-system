package com.freesoft.savings.application

interface TransactionManager<out Repository> {

    fun <T> tx(block: Repository.() -> T): T
}