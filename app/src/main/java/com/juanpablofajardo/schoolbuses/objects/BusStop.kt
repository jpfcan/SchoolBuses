package com.juanpablofajardo.schoolbuses.objects

import com.google.gson.annotations.SerializedName

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 *
 * @property latitude the stop latitude coordinate
 * @property longitude the stop longitude coordinate
 */
data class BusStop(@SerializedName("lat") val latitude: Double?,
                   @SerializedName("lng") val longitude: Double?)