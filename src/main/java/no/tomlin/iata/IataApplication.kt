package no.tomlin.iata

import no.tomlin.iata.generator.Generator.generateSSIMTimezones
import no.tomlin.iata.generator.Generator.generateTimezones
import no.tomlin.iata.parser.Parser.parseIataLocations
import no.tomlin.iata.parser.Parser.parseOptdLocations
import java.lang.System.currentTimeMillis

fun main(args: Array<String>) {
    println("Starting application!")
    val start = currentTimeMillis()

    println("Loading IATA list...")
    val iata = parseIataLocations()

    println("Loading OPTD list...")
    val optd = parseOptdLocations()

    println("IATA: ${iata.size} OPTD: ${optd.size}")

    println("Generating IATA location timezone mapping...")
    generateTimezones(iata, optd)

    println("Generating IATA SSIM timezone mapping...")
    generateSSIMTimezones(iata, optd)

    println("Done! Time: ${(currentTimeMillis() - start).toDouble() / 1000}s")
}