package by.valtorn.luchparser.network.model

data class ApiData(
    val rssi: Long,
    val snr: Long,
    val decodedPayload: String,
    val id: Long,
    val isDuplicate: Boolean,
    val iterator: Long,
    val modemID: String,
    val payload: String,
    val skip: Boolean,
    val stationID: String,
    val timeDetected: String,
    val timePublished: String,
    val timeSaved: String
) {
    fun toMessage(): Message {
        return Message(this.id.toString(), this.decodedPayload, this.timePublished)
    }
}