package com.juanpablofajardo.schoolbuses.objects

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 *
 * @property id the identifier for a given bus
 * @property name the displayable name for a given bus
 * @property description the description of a given bus route
 * @property stopsUrl the url for a given bus stops
 * @property imageUrl the url for a given bus image
 */
data class Bus(val id: Int?, val name: String?, val description: String?,
               @SerializedName("stops_url") val stopsUrl: String?,
               @SerializedName("img_url") val imageUrl: String?): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(stopsUrl)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bus> {
        override fun createFromParcel(parcel: Parcel): Bus {
            return Bus(parcel)
        }

        override fun newArray(size: Int): Array<Bus?> {
            return arrayOfNulls(size)
        }
    }
}