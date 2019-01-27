package no.tomlin.iata.data

enum class LocationType {
    METROPOLITAN_AREA,
    AIRPORT,
    OFF_LINE_POINT,
    BUS_STATION,
    RAILWAY_STATION,
    HELIPORT,
    FERRY_PORT,
    UNSUPPORTED;

    companion object {
        fun parse(locationType: String): LocationType = when (locationType) {
            "1", "O" -> OFF_LINE_POINT
            "2", "C" -> METROPOLITAN_AREA
            "3", "A", "CA" -> AIRPORT
            "4", "B", "CB" -> BUS_STATION
            "5", "R", "CR" -> RAILWAY_STATION
            "6", "H", "CH" -> HELIPORT
            "7", "P", "CP" -> FERRY_PORT
            else -> UNSUPPORTED
        }
    }
}