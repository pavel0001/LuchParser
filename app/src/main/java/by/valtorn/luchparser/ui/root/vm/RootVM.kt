package by.valtorn.luchparser.ui.root.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.valtorn.luchparser.repository.MessagesRepository
import kotlinx.coroutines.launch

class RootVM : ViewModel() {

    val messages = MessagesRepository.messages

    private val mProgress = MutableLiveData(false)
    val progress: LiveData<Boolean> = mProgress

    fun getModem(id: String) {
        viewModelScope.launch {
            mProgress.value = true
            MessagesRepository.getModem(id)
            mProgress.value = false
        }
    }

    fun clear() {
        MessagesRepository.clear()
    }
}