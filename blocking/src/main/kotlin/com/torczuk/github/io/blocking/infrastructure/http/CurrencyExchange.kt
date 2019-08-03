package com.torczuk.github.io.blocking.infrastructure.http

import java.math.BigDecimal

data class CurrencyExchange(val base: String, val target: String, val ratio: BigDecimal)