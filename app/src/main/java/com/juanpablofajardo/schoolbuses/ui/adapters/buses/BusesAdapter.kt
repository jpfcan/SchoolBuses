package com.juanpablofajardo.schoolbuses.ui.adapters.buses

import android.support.v4.util.SparseArrayCompat
import android.view.View
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewTypeConstants.BUS_ITEM_VIEW_TYPE
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.base.BaseDelegateAdapter
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 */
class BusesAdapter(val originalList: List<Bus>, val listener: BusAdapterListener) :
        BaseDelegateAdapter(SparseArrayCompat(), emptyList()), BusItemDelegate.BusItemListener {

    interface BusAdapterListener {
        fun onBusItemClicked(clickedBusItem: Bus?, sharedElements: List<View>, position: Int)
    }

    init {
        delegates.put(BUS_ITEM_VIEW_TYPE, BusItemDelegate(this))
    }


    fun showAll() {
        val viewTypesList = mutableListOf<BusViewType>()
        originalList.mapTo(viewTypesList, {bus ->
            BusViewType(bus.id, bus.name, bus.description, bus.imageUrl)
        })

        addViewTypeRangeAndClear(viewTypesList)
    }


    fun showFiltered(filter: String) {
        val viewTypesList = mutableListOf<BusViewType?>()
        originalList.mapTo(viewTypesList, {bus ->
            if (bus.name?.toLowerCase()?.contains(filter.toLowerCase()) == true ||
                    bus.description?.toLowerCase()?.contains(filter.toLowerCase()) == true)
                BusViewType(bus.id, bus.name, bus.description, bus.imageUrl)
            else null
        })

        addViewTypeRangeAndClear(viewTypesList.filterNotNull())
    }

    override fun onBusViewTypeClicked(clickedViewType: BusViewType, sharedElements: List<View>, position: Int) {
        val clickedBus = originalList.find { it.id == clickedViewType.id }
        listener.onBusItemClicked(clickedBus, sharedElements, position)
    }
}