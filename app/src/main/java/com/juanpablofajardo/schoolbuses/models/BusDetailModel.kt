package com.juanpablofajardo.schoolbuses.models

import com.juanpablofajardo.schoolbuses.app.AppManager
import com.juanpablofajardo.schoolbuses.objects.Responses.BusStopsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 */
class BusDetailModel @Inject constructor() {

    interface BusRouteInfoListener {
        fun onBusRouteInfoFetchSuccess(busRouteInfo: BusStopsResponse)
        fun onBusRouteInfoFetchError()
    }

    fun fetchBusRouteInfo(routeInfoUrl: String, listener: BusRouteInfoListener) {
        AppManager.RETROFIT_INSTANCE.getBusStops(routeInfoUrl).enqueue(object: Callback<BusStopsResponse> {
            override fun onResponse(call: Call<BusStopsResponse>?, response: Response<BusStopsResponse>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    listener.onBusRouteInfoFetchSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<BusStopsResponse>?, t: Throwable?) {

            }
        })
    }

}