package com.juanpablofajardo.schoolbuses.presenters

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.models.BusDetailModel
import com.juanpablofajardo.schoolbuses.models.BusDetailModel.BusRouteInfoListener
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.objects.Responses.BusStopsResponse
import com.juanpablofajardo.schoolbuses.ui.view_interfaces.BusDetailView
import com.juanpablofajardo.schoolbuses.utils.miliSecondGetSeconds
import com.juanpablofajardo.schoolbuses.utils.milisecondsGetHours
import com.juanpablofajardo.schoolbuses.utils.milisecondsGetMinutes
import javax.inject.Inject
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED
import com.google.maps.android.PolyUtil
import com.juanpablofajardo.schoolbuses.utils.milisecondsToSeconds


/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 *
 * Presenter in charge of handling the logic of the Bus detail view.
 */
class BusDetailPresenter @Inject constructor(): BasePresenter<BusDetailView>, BusRouteInfoListener {

    companion object {
        const val MARKER_COLOR = 25F
        const val ZOOM_DP = 50F
        const val MINIMUM_RELOAD_TIME = 3000L
        const val COUNTDOWN_TICK = 1000L
        const val ROUTE_DISTANCE_TOLERANCE = 25000.0
    }


    private lateinit var view: BusDetailView
    private lateinit var bus:Bus
    private lateinit var busRouteInfo: BusStopsResponse
    private lateinit var resources: Resources

    private var retryTime: Long = MINIMUM_RELOAD_TIME
    private var timeLeft: Long = MINIMUM_RELOAD_TIME

    @Inject lateinit var model: BusDetailModel


    override fun setView(view: BusDetailView) {
        this.view = view
    }

    fun setResources(resources: Resources) {
        this.resources = resources
    }

    fun setupInitialView(bus: Bus) {
        this.bus = bus

        view.setBusIcon(bus.imageUrl)
        view.setBusName(bus.name)
        view.setBusDescription(bus.description)
        view.showLoading()
        view.setupMapView()
    }

    fun fetchBusRouteInfo() {
        view.showLoading()
        if (bus.stopsUrl != null) {
            model.fetchBusRouteInfo(bus.stopsUrl!!, this)
        }
    }

    fun reloadView() {
        fetchBusRouteInfo()
        view.setReloadEnabledState(false)
    }

    override fun onBusRouteInfoFetchSuccess(busRouteInfo: BusStopsResponse) {
        this.busRouteInfo = busRouteInfo
        showInfo()
    }

    private fun showInfo() {
        if (busRouteInfoDataIsValid()) {
            retryTime = busRouteInfo.retryTime!!
            timeLeft = retryTime
            initializeCountDown()

            view.setBusStopsAmount((busRouteInfo.stops!!.size - 1).toString())
            view.setBusTimeBetweenStops(getTimeFormatted(busRouteInfo.estimatedTime!!))
            view.setBusTotalRouteTime(getTimeFormatted(busRouteInfo.estimatedTime!! * (busRouteInfo.stops!!.size - 1)))

            setupRoutePolyLine()
        } else {
            view.showConnectionAlert()
        }
    }

    private fun initializeCountDown() {
        object : CountDownTimer(retryTime, COUNTDOWN_TICK) {
            override fun onFinish() {
                view.setReloadEnabledState(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }
        }.start()
    }

    /**
     * This is the main method for the map
     *
     * First it transforms stops to LatLng objects, used by the Google Maps library
     * It then validates the points, in case any of them is not within the route's defined tolerance
     * After that, it iterates over each valid point, adds a marker for it to the map, and adds it to the polyline and to the bounds builder
     * Lastly it sets the bounds to the map and the polyline
     */
    private fun setupRoutePolyLine() {
        val stopPoints = mutableListOf<LatLng?>()
        busRouteInfo.stops!!.mapTo(stopPoints, {busStop -> if (busStop.latitude != null && busStop.longitude != null) LatLng(busStop.latitude, busStop.longitude) else null })

        val stopPointsFiltered = validatePoints(stopPoints.toList())

        val bounds = LatLngBounds.builder()
        val polyLineOptions = PolylineOptions()

        stopPointsFiltered.forEachIndexed({index, point ->
            if (point != null) {
                bounds.include(point)
                polyLineOptions.add(point)

                val markerOptions = MarkerOptions()
                markerOptions.position(point)

                when (index) {
                    0 -> {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN))
                        markerOptions.title(resources.getString(R.string.starting_point))
                    }
                    stopPointsFiltered.lastIndex -> {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(HUE_RED))
                        markerOptions.title(resources.getString(R.string.last_stop_title))
                        markerOptions.snippet(resources.getString(R.string.time_from_start_placeholder, getTimeFormatted(index * busRouteInfo.estimatedTime!!)))
                    }
                    else -> {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(MARKER_COLOR))
                        markerOptions.title(resources.getString(R.string.stop_title_placeholder, index))
                        markerOptions.snippet(resources.getString(R.string.time_from_start_placeholder, getTimeFormatted(index * busRouteInfo.estimatedTime!!)))
                    }
                }

                view.addMarkerToMap(markerOptions)
            }
        })

        view.setMapZoomBounds(bounds.build(), applyDimension(COMPLEX_UNIT_DIP, ZOOM_DP, resources.displayMetrics).toInt())
        view.addPolyLineToMap(polyLineOptions)
        view.hideLoading()
    }

    private fun validatePoints(initialPoints: List<LatLng?>): List<LatLng?> {
        val validPoints = mutableListOf<LatLng?>()

        initialPoints.forEachIndexed({ position, _ ->
            val tempList = initialPoints.toMutableList()
            val removed = tempList.removeAt(position)
            if (PolyUtil.isLocationOnPath(removed, tempList, false, ROUTE_DISTANCE_TOLERANCE)) {
                validPoints.add(removed)
            }
        })

        return validPoints
    }

    /**
     * Format time from miliseconds to the custom format, including hours (if minutes are more than 60), minutes and seconds.
     */
    private fun getTimeFormatted(timeToFormat: Long): String {
        val hours = timeToFormat.milisecondsGetHours().toInt()
        var minutes = timeToFormat.milisecondsGetMinutes().toInt()
        val seconds = timeToFormat.miliSecondGetSeconds().toInt()

        if (hours >= 1) {
            minutes -= (hours * 60)
        }

        if (hours == 0) {
            return resources.getString(R.string.time_no_hours_placeholder, minutes, seconds)
        } else {
            return resources.getString(R.string.time_with_hours_placeholder, hours, minutes, seconds)
        }
    }

    private fun busRouteInfoDataIsValid(): Boolean {
        return busRouteInfo.response == true && busRouteInfo.stops != null && busRouteInfo.estimatedTime != null && busRouteInfo.retryTime != null
    }


    override fun onBusRouteInfoFetchError() {
        view.showConnectionAlert();
    }

    fun prepareToastMessage() {
        val message = resources.getString(R.string.time_left_reload_placeholder, timeLeft.milisecondsToSeconds())
        view.showTimeLeftMessage(message)
    }

}