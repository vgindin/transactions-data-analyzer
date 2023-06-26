package com.anonos.transactionsdataanalyzer.service

import com.anonos.dto.ResponseDTO
import com.anonos.transactionsdataanalyzer.service.impl.Format

interface StatisticsFormatterService {
    fun format(outputFormat: Format, responseDTO: ResponseDTO): String
}
