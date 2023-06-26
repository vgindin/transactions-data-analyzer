package com.anonos.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ProcessingResultDTO(
    val totalRecords: Long,
    val totalTransactionAmount: BigDecimal,
    val transactionAmountsPerDay: Map<String, BatchStatDTO>,
)

data class ResponseDTO(
    @get:JsonProperty("Input filename") val inputFilename: String,
    @get:JsonProperty("Total records processed") val totalRecords: Long,
    @get:JsonProperty("Total transaction amount") val totalTransactionAmount: BigDecimal,
    @get:JsonProperty("Transactions amount per day")val transactionAmountsPerDay: Map<String, BigDecimal>
)

data class BatchStatDTO(
    val key: String,
    val totalRecords: Long,
    val totalTransactionAmount: BigDecimal,
)
