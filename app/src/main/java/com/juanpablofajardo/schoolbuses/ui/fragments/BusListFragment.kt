package com.juanpablofajardo.schoolbuses.ui.fragments

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.app.AppManager
import com.juanpablofajardo.schoolbuses.navigators.BusListNavigator
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.presenters.BusListPresenter
import com.juanpablofajardo.schoolbuses.ui.adapters.buses.BusesAdapter
import com.juanpablofajardo.schoolbuses.ui.view_interfaces.BusListView
import kotterknife.bindView
import javax.inject.Inject

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
class BusListFragment: BaseFragment(), BusListView {

    companion object {
        const val BUS_LIST_KEY = "bus_list"

        fun newInstance(busList: ArrayList<Bus>): BusListFragment {
            val busListFragment = BusListFragment()
            val args = Bundle()
            args.putParcelableArrayList(BUS_LIST_KEY, busList)
            busListFragment.arguments = args

            return busListFragment
        }
    }

    val busListRecyclerView: RecyclerView by bindView(R.id.bus_list_recycler_view)
    val busListSearchView: SearchView by bindView(R.id.bus_list_search)


    @Inject lateinit var presenter: BusListPresenter
    @Inject lateinit var navigator: BusListNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.DAGGER_COMPONENT.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null && arguments?.containsKey(BUS_LIST_KEY) == true){
            presenter.setView(this)
            presenter.setupView(arguments?.getParcelableArrayList(BUS_LIST_KEY)!!)
            presenter.createSearchListener()
        }
    }

    override fun setupRecyclerViewWithAdapter(adapter: BusesAdapter) {
        if (isAdded) {
            busListRecyclerView.layoutManager = LinearLayoutManager(context);
            busListRecyclerView.adapter = adapter
        }
    }

    override fun setQueryTextListener(queryTextListener: SearchView.OnQueryTextListener) {
        if (isAdded) {
            busListSearchView.setOnQueryTextListener(queryTextListener)
        }
    }

    override fun goToBusItemDetail(selectedBus: Bus?, sharedElements: List<View>, suffix: Int) {
        if (isAdded) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                navigator.goToBusItemDetailWithTransition(activity!!, selectedBus, sharedElements, suffix)
            } else {
                navigator.goToBusItemDetailWithoutTransition(activity!!, selectedBus)
            }
        }
    }

    override fun showLoading() {
        //NO-OP
    }

    override fun hideLoading() {
        //NO-OP
    }

    override fun getLayoutResource(): Int = R.layout.fragment_bus_list
}