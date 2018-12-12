package no.tomlin.iata.generator

import no.tomlin.iata.data.IataLocationIdentifier
import no.tomlin.iata.data.OptdLocationIdentifier
import java.io.File

object Generator {

    private const val IATA_TIMEZONES = "generated/iata_tz.csv"
    private const val IATA_SSIM_TIMEZONES = "generated/iata_ssim_tz.csv"
    private const val DEFAULT_SEPARATOR = "^"

    fun generateTimezones(iataList: List<IataLocationIdentifier>, optdList: List<OptdLocationIdentifier>) {
        val iataCodes = iataList.map { it.airportCode }.distinct()

        write(IATA_TIMEZONES, header("iata_code", "time_zone"),
                optdList
                        .distinctBy { it.iataCode }
                        .filter { iataCodes.contains(it.iataCode) && it.timezone.isNotBlank() }
                        .map { "${it.iataCode}$DEFAULT_SEPARATOR${it.timezone}" }
                        .sorted())
    }

    fun generateSSIMTimezones(iataList: List<IataLocationIdentifier>, optdList: List<OptdLocationIdentifier>) {
        write(IATA_SSIM_TIMEZONES, header("ssim_id", "time_zone"),
                iataList
                        .filter { it.airportCode.isNotBlank() && it.countryCode.isNotBlank() }
                        .mapNotNull { iata ->
                            optdList
                                    .firstOrNull { it.iataCode == iata.airportCode && it.timezone.isNotBlank() }
                                    ?.let { iata.countryCode + iata.timezoneCode to it.timezone }
                        }
                        .groupBy { it.first }
                        .map { pair -> pair.key + DEFAULT_SEPARATOR + pair.value.groupBy { it.second }.maxBy { it.value.size }?.key }
                        .sorted())
    }

    private fun header(vararg keys: String) = keys.joinToString(DEFAULT_SEPARATOR)

    private fun write(fileName: String, header: String, lines: List<String>) {
        val file = File(fileName)

        if (!file.exists()) {
            throw GeneratorException("File '$fileName' does not exist")
        }
        if (!file.canWrite()) {
            throw GeneratorException("File '$fileName' is not writable")
        }
        file.writeText("$header\n${lines.joinToString("\n")}")
    }

    internal class GeneratorException(msg: String) : RuntimeException(msg)
}