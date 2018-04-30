package com.juanpablofajardo.schoolbuses.objects

import com.google.gson.annotations.SerializedName

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
object Responses {

    /**
     * @property response true if the response is successfull
     * @property busList the requested list of school buses
     */
    data class BusListResponse(val response: Boolean?, @SerializedName("school_buses") val busList: List<Bus>?)


    /**
     * @property response true if the response is successfull
     * @property stops the requested list of stops
     * @property estimatedTime the estimated time between stops (in milliseconds). ASSUMPTION
     * @property retryTime the minimum time for the service to be called again (in milliseconds). ASSUMPTION
     */
    data class BusStopsResponse(val response: Boolean?, val stops: List<BusStop>?,
                                @SerializedName("estimated_time_milliseconds") val estimatedTime: Long?,
                                @SerializedName("retry_time_milliseconds") val retryTime: Long?)
}