package com.anonos.transactionsdataanalyzer.service

import com.anonos.dto.TransactionDTO
import java.io.InputStream

interface TransactionsCsvParser {
    fun readStrictCsv(inputStream: InputStream): List<TransactionDTO>
}
