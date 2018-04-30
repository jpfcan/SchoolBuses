package com.juanpablofajardo.schoolbuses.ui.view_interfaces

import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 */
interface BusDetailView: BaseView {

    fun setBusIcon(imageURL: String?)

    fun setBusName(busName: String?)

    fun setBusDescription(busDescription: String?)

    fun setupMapView()

    fun setBusStopsAmount(busStopsAmount: String?)

    fun setBusTimeBetweenStops(timeBetweenStops: String?)

    fun setBusTotalRouteTime(totalRouteTime: String?)

    fun addMarkerToMap(markerOptions: MarkerOptions)

    fun addPolyLineToMap(polyLineOptions: PolylineOptions)

    fun setMapZoomBounds(bounds: LatLngBounds?, padding: Int)

    fun setReloadEnabledState(enabled: Boolean)

    fun showTimeLeftMessage(message: String)

}