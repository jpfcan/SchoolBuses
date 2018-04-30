package com.juanpablofajardo.schoolbuses.api

import com.juanpablofajardo.schoolbuses.objects.Responses.BusListResponse
import com.juanpablofajardo.schoolbuses.objects.Responses.BusStopsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 *
 * Interface used to define the methods to get info from server wiht retrofit.
 */
interface ApiRetrofit {

    companion object {
       const val GET_BUSES_URL = "https://api.myjson.com/bins/10yg1t"
    }

    @GET(GET_BUSES_URL)
    fun getBuses(): Call<BusListResponse>

    @GET
    fun getBusStops(@Url busStopsUrl: String): Call<BusStopsResponse>

}