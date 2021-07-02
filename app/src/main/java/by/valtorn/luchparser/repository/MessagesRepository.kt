package by.valtorn.luchparser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.valtorn.luchparser.network.NetworkService
import by.valtorn.luchparser.network.model.Message
import by.valtorn.luchparser.ui.result.root.ui.toPayload
import com.github.mikephil.charting.data.Entry

object MessagesRepository {

    private val mMessages = MutableLiveData<List<Message>?>()
    val messages: LiveData<List<Message>?> = mMessages

    private val mDatasetCharts = MutableLiveData<DatasetForCharts>()
    val datasetCharts: LiveData<DatasetForCharts> = mDatasetCharts

    suspend fun getModem(id: String) {
        mMessages.value = NetworkService.getModemData(id)
        mDatasetCharts.value = messages.value?.toDataset()
    }

    fun getMessageWithId(id: String): Message? {
        return messages.value?.let {
            it.firstOrNull { message ->
                message.id == id
            }
        }
    }

    fun clear() {
        mMessages.value = null
    }

    data class DatasetForCharts(
        val power: List<Entry>,
        val distance: List<Entry>,
        val temperature: List<Entry>
    )

    fun List<Message>.toDataset(): DatasetForCharts {
        val pow = mutableListOf<Entry>()
        val dis = mutableListOf<Entry>()
        val tem = mutableListOf<Entry>()

        this.mapIndexed { index, message ->
            message.decodedPayload?.toPayload()?.let {
                pow.add(Entry(index.toFloat(), it.batteryPow.toFloat()))
                dis.add(Entry(index.toFloat(), it.distance.toFloat()))
                tem.add(Entry(index.toFloat(), it.temperature.toFloat()))
            }
        }
        return DatasetForCharts(power = pow, distance = dis, temperature = tem)
    }
}
