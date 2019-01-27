package no.tomlin.iata.data

import java.time.LocalDate

data class AirlineIdentifier(
        val iataCode: String?,
        val icaoCode: String?,
        val name: String,
        val alias: String,
        val type: String,
        val started: LocalDate?,
        val ended: LocalDate?,
        val wiki: String
) {
    constructor(split: List<String>) : this(
            iataCode = parse(split[5]),
            icaoCode = split[4],
            name = split[7],
            alias = split[8],
            type = split[11],
            started = parseDate(split[2]),
            ended = parseDate(split[3]),
            wiki = split[12]
    )

    companion object {
        fun parse(string: String) = if (string.isNotBlank()) string else null
        fun parseDate(date: String) = if (date.isNotBlank()) LocalDate.parse(date) else null
        fun parseType(type: String) = when (type) {
            "R" -> "Railway"
            "C" -> "Cargo"
            "G" -> "Service Provider"
            else -> "Airline"
        }
    }
}