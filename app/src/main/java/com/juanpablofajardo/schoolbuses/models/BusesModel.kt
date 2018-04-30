package com.juanpablofajardo.schoolbuses.models

import com.juanpablofajardo.schoolbuses.app.AppManager
import com.juanpablofajardo.schoolbuses.objects.Responses.BusListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Juan Pablo Fajardo Cano on 4/30/18.
 */
class BusesModel @Inject constructor() {

    interface BusListListener {
        fun onBusListFetchSuccess(busListResponse: BusListResponse)
        fun onBusListFetchError()
    }

    fun fetchBusList(busListListener: BusListListener) {
        AppManager.RETROFIT_INSTANCE.getBuses().enqueue(object: Callback<BusListResponse> {
            override fun onResponse(call: Call<BusListResponse>?, response: Response<BusListResponse>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    busListListener.onBusListFetchSuccess(response.body()!!)
                } else {
                    busListListener.onBusListFetchError()
                }
            }

            override fun onFailure(call: Call<BusListResponse>?, t: Throwable?) {
                busListListener.onBusListFetchError()
            }
        })
    }

}