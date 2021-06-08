package by.valtorn.luchparser.ui.details.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.valtorn.luchparser.network.model.Message
import by.valtorn.luchparser.repository.MessagesRepository

class DetailsVM : ViewModel() {

    private val mSelectMessage = MutableLiveData<Message?>(null)
    val selectMessage: LiveData<Message?> = mSelectMessage

    fun selectMessage(id: String) {
        mSelectMessage.value = MessagesRepository.getMessageWithId(id)
    }

    fun clear() {
        mSelectMessage.value = null
    }
}