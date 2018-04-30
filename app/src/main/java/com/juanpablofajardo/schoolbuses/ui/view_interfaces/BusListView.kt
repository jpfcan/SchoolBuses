package com.juanpablofajardo.schoolbuses.ui.view_interfaces

import android.support.v7.widget.SearchView.OnQueryTextListener
import android.view.View
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.ui.adapters.buses.BusesAdapter

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
interface BusListView: BaseView {

    fun setupRecyclerViewWithAdapter(adapter: BusesAdapter)

    fun setQueryTextListener(queryTextListener: OnQueryTextListener)

    fun goToBusItemDetail(selectedBus: Bus?, sharedElements: List<View>, suffix: Int)

}