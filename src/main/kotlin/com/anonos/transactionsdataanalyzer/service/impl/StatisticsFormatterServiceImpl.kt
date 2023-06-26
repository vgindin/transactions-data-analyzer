package com.anonos.transactionsdataanalyzer.service.impl

import com.anonos.dto.ResponseDTO
import com.anonos.transactionsdataanalyzer.service.StatisticsFormatterService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class StatisticsFormatterServiceImpl : StatisticsFormatterService {
    override fun format(outputFormat: Format, responseDTO: ResponseDTO): String {
        return outputFormat.format(responseDTO)
    }
}

val objectMapper = ObjectMapper()

enum class Format {
    JSON {
        override fun format(responseDTO: ResponseDTO): String {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO)
        }
    },
    TEXT {
        /**
         ==== Run Summary ====
         Input filename: src/main/test/resources/transaction_data.csv
         Total records processed: X
         Total transaction amount: N
         Transaction amount per day:
         yyyy-mm-dd: N1
         yyyy-mm-dd: N2
         ...
         yyyy-mm-dd: NX

         ==== End Run Summary ====
         */
        override fun format(responseDTO: ResponseDTO): String {
            val result = StringBuffer()
            result.append("==== Run Summary ====\n")
            result.append("Input filename: ${responseDTO.inputFilename}\n")
            result.append("Total records processed: ${responseDTO.totalRecords}\n")
            result.append("Total transaction amount: ${responseDTO.totalTransactionAmount}\n")
            responseDTO.transactionAmountsPerDay.forEach {
                result.append("${it.key}: ${it.value}\n")
            }
            result.append("==== End Run Summary ====")
            return result.toString()
        }
    }, ;

    abstract fun format(responseDTO: ResponseDTO): String
}
