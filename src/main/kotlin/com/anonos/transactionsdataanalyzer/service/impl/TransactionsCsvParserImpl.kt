package com.anonos.transactionsdataanalyzer.service.impl

import com.anonos.dto.TransactionDTO
import com.anonos.transactionsdataanalyzer.service.TransactionsCsvParser
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.io.InputStream
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val DATE_FORMATTER = "E MMM d yyyy HH:mm:ss 'GMT'X"

@Component
class TransactionsCsvParserImpl : TransactionsCsvParser {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMATTER, Locale.US)
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun readStrictCsv(inputStream: InputStream): List<TransactionDTO> = csvReader().open(inputStream) {
        readAllWithHeaderAsSequence().map {
            try {
                TransactionDTO(
                    address = it["address"]!!,
                    age = it["age"]!!.toInt(),
                    email = it["email"]!!,
                    firstName = it["firstName"]!!,
                    ip = it["ip"]!!,
                    joinDate = parseDate(it["joinDate"]!!),
                    lastName = it["lastName"]!!,
                    leaveDate = parseDate(it["leaveDate"]!!),
                    referral = it["referral"].toBoolean(),
                    transactionAmount = it["transactionAmount"]!!.toBigDecimal(),
                    transactionDate = parseDate(it["transactionDate"]!!),
                    zip = it["zip"]!!,
                )
            } catch (e: Exception) {
                log.debug("Parsing error: ${e.message}")
                null
            }
        }.filterNotNull().toList()
    }

    // Fri Aug 17 2018 00:47:27 GMT-0600 (Mountain Daylight Time)
    private fun parseDate(input: String): ZonedDateTime {
        return ZonedDateTime.parse(input.subSequence(0, 33), dateFormatter)
    }
}
