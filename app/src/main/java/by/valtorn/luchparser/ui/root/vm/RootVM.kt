package by.valtorn.luchparser.ui.root.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.valtorn.luchparser.network.NetworkService
import by.valtorn.luchparser.network.model.Modem
import kotlinx.coroutines.launch

class RootVM : ViewModel() {

    private val mModem = MutableLiveData<List<Modem>?>()
    val modem: LiveData<List<Modem>?> = mModem

    fun getModem(id: String) {
        viewModelScope.launch {
            mModem.value = NetworkService.getModemData(id)
        }
    }

    fun clear(){
        mModem.value = null
    }
}