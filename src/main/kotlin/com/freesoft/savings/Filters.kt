package com.freesoft.savings

import com.freesoft.savings.application.AccountingFreesoftSystem
import com.freesoft.savings.application.error.HttpException
import org.http4k.core.Filter
import org.http4k.core.Status
import org.http4k.filter.RequestFilters
import java.time.LocalDateTime

object WorkingTimeFilter {

    object Check {
        operator fun invoke(system: AccountingFreesoftSystem): Filter = RequestFilters.Tap {
            val currentHour = LocalDateTime.now().hour
            if (currentHour >= system.configuration.startHour && currentHour <= system.configuration.endHour) {
                throw HttpException(Status.BAD_REQUEST, "Not in working time!")
            }
        }
    }

}