package no.tomlin.iata.data

data class OptdLocationIdentifier(
        val iataCode: String,
        val icaoCode: String,
        val name: String,
        val asciiName: String,
        val cityCode: String,
        val cityName: String,
        val stateCode: String,
        val stateName: String,
        val countryCode: String,
        val countryName: String,
        val latitude: String,
        val longitude: String,
        val timezone: String,
        val locationType: LocationType
) {
    constructor(split: List<String>) : this(
            iataCode = split[0],
            icaoCode = split[1],
            name = split[6],
            asciiName = split[7],
            cityCode = split[36],
            cityName = split[37],
            stateCode = split[20],
            stateName = split[21],
            countryCode = split[16],
            countryName = split[18],
            latitude = split[8],
            longitude = split[9],
            timezone = split[31],
            locationType = LocationType.parse(split[41])
    )
}