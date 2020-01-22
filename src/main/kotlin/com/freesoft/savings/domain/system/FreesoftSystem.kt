package com.freesoft.savings.domain.system

import com.freesoft.savings.Router
import com.freesoft.savings.application.TransactionManager
import com.freesoft.savings.domain.repository.Repository

interface FreesoftSystem {
    val name: String

    val txManager: TransactionManager<Repository>

    infix fun `start as server`(router: Router)
}
