package com.juanpablofajardo.schoolbuses.ui.activities

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.juanpablofajardo.schoolbuses.utils.DetailsTransition
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.app.AppManager
import com.juanpablofajardo.schoolbuses.presenters.BusDetailPresenter
import com.juanpablofajardo.schoolbuses.ui.view_interfaces.BusDetailView
import com.squareup.picasso.Picasso
import kotterknife.bindView
import javax.inject.Inject

class MapActivity : BaseActivity(), OnMapReadyCallback, BusDetailView {

    companion object {
        const val BUS_KEY = "bus"
        const val TRANSITION_SUFFIX_KEY = "transition_suffix"
    }

    val loadingView: View by bindView(R.id.loading_view)
    val ivBusIcon: ImageView by bindView(R.id.bus_icon)
    val tvBusName: TextView by bindView(R.id.bus_name)
    val tvBusDescription: TextView by bindView(R.id.bus_description)
    val tvBusStopsAmount: TextView by bindView(R.id.bus_stops_amount)
    val tvBusTimeBetweenStops: TextView by bindView(R.id.bus_time_between_stops)
    val tvBusRouteTime: TextView by bindView(R.id.bus_total_time)
    val mapView: MapView by bindView(R.id.bus_route_map_view)

    lateinit var googleMap: GoogleMap

    var isReLoadEnabled = false

    @Inject lateinit var presenter: BusDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.DAGGER_COMPONENT.inject(this)
        setToolbarWithCloseIcon()

        if (intent.hasExtra(TRANSITION_SUFFIX_KEY) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTransitionNames(intent.getIntExtra(TRANSITION_SUFFIX_KEY, -1))
            setupSharedElementTransitions()
        }

        if (intent.hasExtra(BUS_KEY)) {
            presenter.setView(this)
            presenter.setResources(resources)
            presenter.setupInitialView(intent.getParcelableExtra(BUS_KEY))
            presenter.fetchBusRouteInfo();
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setTransitionNames(transitionSuffix: Int) {
        ViewCompat.setTransitionName(ivBusIcon, ivBusIcon.transitionName + transitionSuffix)
        ViewCompat.setTransitionName(tvBusName, tvBusName.transitionName + transitionSuffix)
        ViewCompat.setTransitionName(tvBusDescription, tvBusDescription.transitionName + transitionSuffix)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupSharedElementTransitions() {
        window.sharedElementEnterTransition = DetailsTransition()
        window.sharedElementExitTransition = DetailsTransition()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bus_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_reload -> {
                if (isReLoadEnabled) presenter.reloadView() else presenter.prepareToastMessage()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setBusIcon(imageURL: String?) {
        //If Picasso fails to load the image from URL, for any reason, it'll use the default icon
        Picasso.with(this).load(imageURL).error(R.drawable.ic_default_bus).into(ivBusIcon)
    }

    override fun setBusName(busName: String?) {
        tvBusName.text = busName
    }

    override fun setBusDescription(busDescription: String?) {
        tvBusDescription.text = busDescription
    }

    override fun setupMapView() {
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun setBusStopsAmount(busStopsAmount: String?) {
        tvBusStopsAmount.text = getString(R.string.stops_amount_placeholder, busStopsAmount)
    }

    override fun setBusTimeBetweenStops(timeBetweenStops: String?) {
        tvBusTimeBetweenStops.text = getString(R.string.average_time_between_stops_placeholder, timeBetweenStops)
    }

    override fun setBusTotalRouteTime(totalRouteTime: String?) {
        tvBusRouteTime.text = getString(R.string.route_estimated_time_placeholder, totalRouteTime)
    }

    override fun addMarkerToMap(markerOptions: MarkerOptions) {
        googleMap.addMarker(markerOptions)
    }

    override fun addPolyLineToMap(polyLineOptions: PolylineOptions) {
        googleMap.addPolyline(polyLineOptions)
    }

    override fun setMapZoomBounds(bounds: LatLngBounds?, padding: Int) {
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.moveCamera(cameraUpdate)
    }

    override fun setReloadEnabledState(enabled: Boolean) {
        isReLoadEnabled = enabled
    }

    override fun showTimeLeftMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        loadingView.visibility = VISIBLE
    }

    override fun hideLoading() {
        loadingView.visibility = GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(this)
        this.googleMap = googleMap
    }

    override fun getLayoutResource() = R.layout.activity_bus_detail
}
