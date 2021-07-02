package by.valtorn.luchparser.ui.result.root.ui

import android.content.Context
import android.view.View
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.ItemModemBinding
import by.valtorn.luchparser.network.model.Message
import by.valtorn.luchparser.utils.BaseListItem
import by.valtorn.luchparser.utils.backTimeToNormalTime

class MessageItem(private val context: Context, val message: Message) : BaseListItem {

    override fun getViewId() = R.layout.item_modem

    override fun renderView(view: View, positionInAdapter: Int) {
        with(ItemModemBinding.bind(view)) {
            imId.text = context.getString(R.string.modem_id, message.id)
            imTime.text = context.getString(R.string.modem_time, message.timePublished.orEmpty().backTimeToNormalTime())
            message.decodedPayload?.toPayload()?.let {
                imPayloadPower.text = context.getString(R.string.modem_payload_power, it.batteryPow)
                imPayloadDistance.text = context.getString(R.string.modem_payload_distance, it.distance)
                imPayloadTemperature.text = context.getString(R.string.modem_payload_temperature, it.temperature)
            }
        }
    }
}

data class Payload(
    val batteryPow: String,
    val distance: String,
    val temperature: String
)

fun String.toPayload(): Payload {
    val result = mutableListOf<String>()
    for (i in this.indices step 4) {
        result.add("${this[i]}${this[i + 1]}${this[i + 2]}${this[i + 3]}")
    }
    return Payload(
        batteryPow = result[1].toLong(16).toFloat().div(1000).toString(),
        distance = result[2].toLong(16).toString(),
        temperature = result[3].toLong(16).toFloat().div(100).minus(150).toString()
    )
}