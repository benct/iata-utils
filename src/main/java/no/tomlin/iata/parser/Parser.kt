package no.tomlin.iata.parser

import no.tomlin.iata.data.IataLocationIdentifier
import no.tomlin.iata.data.LocationType.UNSUPPORTED
import no.tomlin.iata.data.OptdLocationIdentifier
import java.io.File

object Parser {

    private const val IATA_AIRPORT_LIST = "data/iata_airport_list.csv"
    private const val OPTD_AIRPORT_LIST = "data/optd_por_public.csv"
    private const val DEFAULT_SEPARATOR = '^'

    fun iataLocations(): Map<String, List<IataLocationIdentifier>> {
        val locations = mutableMapOf<String, MutableList<IataLocationIdentifier>>()

        parseIataLocations().forEach { locations.getOrPut(it.airportCode) { mutableListOf() }.add(it) }

        return locations.toMap()
    }

    fun optdLocations(): Map<String, List<OptdLocationIdentifier>> {
        val locations = mutableMapOf<String, MutableList<OptdLocationIdentifier>>()

        parseOptdLocations().forEach { locations.getOrPut(it.iataCode) { mutableListOf() }.add(it) }

        return locations.toMap()
    }

    fun parseIataLocations(): List<IataLocationIdentifier> = parse(IATA_AIRPORT_LIST) { IataLocationIdentifier(it) }
            .filter { it.locationType != UNSUPPORTED }

    fun parseOptdLocations(): List<OptdLocationIdentifier> = parse(OPTD_AIRPORT_LIST) { OptdLocationIdentifier(it) }
            .filter { it.locationType != UNSUPPORTED }

    private fun <T : Any> parse(fileName: String, mapping: (List<String>) -> T): List<T> {
        val errors = mutableListOf<String>()
        val entries = File(fileName).readLines()
        val parsed = entries.subList(1, entries.size)
                .mapNotNull {
                    try {
                        mapping.invoke(split(it))
                    } catch (e: IndexOutOfBoundsException) {
                        errors.add("Could not parse line '$it': $e")
                        null
                    }
                }
        if (errors.size > 0) {
            throw ParserException("Parsed file is malformed: $errors")
        }
        if (parsed.isEmpty()) {
            throw ParserException("Parsed file is empty")
        }
        return parsed
    }

    private fun split(string: String, separator: Char = DEFAULT_SEPARATOR) = string.split(separator)

    internal class ParserException(msg: String) : RuntimeException(msg)
}