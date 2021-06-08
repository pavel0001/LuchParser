package by.valtorn.luchparser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.valtorn.luchparser.network.NetworkService
import by.valtorn.luchparser.network.model.Message

object MessagesRepository {

    private val mMessages = MutableLiveData<List<Message>?>()
    val messages: LiveData<List<Message>?> = mMessages

    suspend fun getModem(id: String) {
        mMessages.value = NetworkService.getModemData(id)
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
}