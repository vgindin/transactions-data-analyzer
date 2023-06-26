package com.anonos.dto

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZonedDateTime

data class TransactionDTO(
    val address: String,
    val age: Int,
    val email: String,
    val firstName: String,
    val ip: String,
    val joinDate: ZonedDateTime,
    val lastName: String,
    val leaveDate: ZonedDateTime,
    val referral: Boolean,
    val transactionAmount: BigDecimal,
    val transactionDate: ZonedDateTime,
    val zip: String
)
