package com.juanpablofajardo.schoolbuses.presenters

import android.support.v7.widget.SearchView.OnQueryTextListener
import android.view.View
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.ui.adapters.buses.BusesAdapter
import com.juanpablofajardo.schoolbuses.ui.view_interfaces.BusListView
import javax.inject.Inject

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
class BusListPresenter @Inject constructor(): BasePresenter<BusListView>, BusesAdapter.BusAdapterListener {

    private var view: BusListView? = null
    private lateinit var  busesAdapter: BusesAdapter

    override fun setView(view: BusListView) {
        this.view = view
    }

    fun setupView(busList: List<Bus>) {
        if (view != null) {
            busesAdapter = BusesAdapter(busList, this)
            busesAdapter.showAll()

            view!!.setupRecyclerViewWithAdapter(busesAdapter)
        }
    }

    fun createSearchListener() {
        if (view != null) {
            val searchListener = object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    busesAdapter.showFiltered(newText)
                    return true
                }
            }

            view!!.setQueryTextListener(searchListener)
        }
    }

    override fun onBusItemClicked(clickedBusItem: Bus?, sharedElements: List<View>, position: Int) {
        if (view != null) {
            view!!.goToBusItemDetail(clickedBusItem, sharedElements, position)
        }
    }
}