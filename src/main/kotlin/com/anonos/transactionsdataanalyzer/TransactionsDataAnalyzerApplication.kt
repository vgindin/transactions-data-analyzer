package com.anonos.transactionsdataanalyzer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransactionsDataAnalyzerApplication

fun main(args: Array<String>) {
    runApplication<TransactionsDataAnalyzerApplication>(*args)
}
