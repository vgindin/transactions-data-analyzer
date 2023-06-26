package com.anonos.mapper

import com.anonos.dto.ProcessingResultDTO
import com.anonos.dto.ResponseDTO

fun ProcessingResultDTO.toResponseDTO(filename: String) = ResponseDTO(
    inputFilename = filename,
    totalRecords = this.totalRecords,
    totalTransactionAmount = this.totalTransactionAmount,
    transactionAmountsPerDay = this.transactionAmountsPerDay.entries.map {
        Pair(it.key, it.value.totalTransactionAmount)
    }.toMap()
)
