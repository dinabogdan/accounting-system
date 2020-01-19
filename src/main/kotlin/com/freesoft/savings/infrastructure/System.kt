package com.freesoft.savings.infrastructure

interface System {
    val name: String
    fun checkStarted()
}

interface AccountingSystem: System {

}