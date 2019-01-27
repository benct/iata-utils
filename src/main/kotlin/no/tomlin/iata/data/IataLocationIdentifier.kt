package no.tomlin.iata.data

data class IataLocationIdentifier(
        val cityCode: String,
        val cityName: String,
        val stateCode: String,
        val countryCode: String,
        val timezoneCode: String,
        val airportCode: String,
        val airportName: String,
        val locationType: LocationType
) {
    constructor(split: List<String>) : this(
            cityCode = split[0],
            cityName = split[1],
            stateCode = split[2],
            countryCode = split[3],
            timezoneCode = split[4],
            airportCode = split[6],
            airportName = split[7],
            locationType = LocationType.parse(split[9])
    )
}