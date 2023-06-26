package com.anonos.transactionsdataanalyzer

import com.anonos.mapper.toResponseDTO
import com.anonos.transactionsdataanalyzer.service.StatisticsFormatterService
import com.anonos.transactionsdataanalyzer.service.TransactionStatisticsService
import com.anonos.transactionsdataanalyzer.service.TransactionsCsvParser
import com.anonos.transactionsdataanalyzer.service.impl.Format
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import java.io.File
import java.io.InputStream
import java.nio.file.Path
import kotlin.io.path.exists

@Service
class AppCommandLineRunner(
    val transactionsCsvParser: TransactionsCsvParser,
    val transactionStatisticsService: TransactionStatisticsService,
    val statisticsFormatterService: StatisticsFormatterService,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val parser = ArgParser("transaction-data-analyzer")
        val input by parser.option(ArgType.String, shortName = "i", description = "Input file").required()

        val outputFormatter by parser.option(ArgType.Choice<Format>(), shortName = "f", description = "Output format")
            .default(Format.TEXT)
//        val debug by parser.option(ArgType.Boolean, shortName = "d", description = "Turn on debug mode").default(false)

        parser.parse(args.filterNotNull().toTypedArray<String>())
        val inputData = transactionsCsvParser.readStrictCsv(readFrom(input))

        runBlocking {
            val result = transactionStatisticsService.calculate(inputData)
            println(statisticsFormatterService.format(outputFormatter, result.toResponseDTO(input)))
        }
    }

    fun readFrom(file: String): InputStream {
        if (!Path.of(file).exists()) {
            throw IllegalArgumentException("File $file doesn't exists")
        }

        if (!file.endsWith(".csv")) {
            throw IllegalArgumentException("Not a CSV file")
        }

        return File(file).inputStream()
    }
}
