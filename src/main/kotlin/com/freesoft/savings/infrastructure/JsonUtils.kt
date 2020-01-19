package com.freesoft.savings.infrastructure

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.http4k.format.ConfigurableJackson
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings
import java.math.BigDecimal

val mapper: ObjectMapper = KotlinModule()
    .asConfigurable()
    .withStandardMappings()
    .done()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
    .configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, false)
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

object CustomJackson : ConfigurableJackson(mapper) {
    override fun decimal(value: JsonNode): BigDecimal {
        return value.decimalValue()
    }

    override fun integer(value: JsonNode): Long {
        return value.longValue()
    }
}