package by.valtorn.luchparser.utils

import android.view.View
import androidx.annotation.LayoutRes

interface BaseListItem {

    @LayoutRes
    fun getViewId(): Int

    fun renderView(view: View, positionInAdapter: Int = 0)

    fun isItemEquals(another: BaseListItem): Boolean {
        return false
    }
}