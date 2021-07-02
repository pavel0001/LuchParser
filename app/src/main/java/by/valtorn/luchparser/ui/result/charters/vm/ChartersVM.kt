package by.valtorn.luchparser.ui.result.charters.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.valtorn.luchparser.repository.MessagesRepository

class ChartersVM : ViewModel() {
    val dataset = MessagesRepository.datasetCharts

    private val mSelectedType = MutableLiveData<CharterType>(CharterType.POWER)
    val selectedType: LiveData<CharterType> = mSelectedType

    fun selectType(type: CharterType) {
        mSelectedType.value = type
    }
}

enum class CharterType {
    POWER,
    DISTANCE,
    TEMPERATURE
}