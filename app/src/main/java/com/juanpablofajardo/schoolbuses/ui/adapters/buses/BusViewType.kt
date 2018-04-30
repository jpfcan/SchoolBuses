package com.juanpablofajardo.schoolbuses.ui.adapters.buses

import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewType
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewTypeConstants
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewTypeConstants.BUS_ITEM_VIEW_TYPE

/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 */
class BusViewType (val id: Int?, val name: String?, val description: String?, val imageUrl: String?): ViewType {
    override fun getViewType(): Int = BUS_ITEM_VIEW_TYPE
}