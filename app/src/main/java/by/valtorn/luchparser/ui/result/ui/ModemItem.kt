package by.valtorn.luchparser.ui.result.ui

import android.content.Context
import android.view.View
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.ItemModemBinding
import by.valtorn.luchparser.network.model.Modem
import by.valtorn.luchparser.utils.BaseListItem

class ModemItem(private val context: Context, private val modem: Modem) : BaseListItem {

    override fun getViewId() = R.layout.item_modem

    override fun renderView(view: View, positionInAdapter: Int) {
        with(ItemModemBinding.bind(view)) {
            imId.text = context.getString(R.string.modem_id, modem.modemID)
            imPayload.text = context.getString(R.string.modem_payload, modem.payload)
            imTime.text = context.getString(R.string.modem_time, modem.timePublished)
        }
    }
}