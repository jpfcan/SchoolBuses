package com.juanpablofajardo.schoolbuses.navigators

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.util.Pair
import android.view.View
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.ui.activities.MapActivity
import javax.inject.Inject
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import com.juanpablofajardo.schoolbuses.ui.activities.MapActivity.Companion.BUS_KEY
import com.juanpablofajardo.schoolbuses.ui.activities.MapActivity.Companion.TRANSITION_SUFFIX_KEY


/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 *
 * Navigator to ve used with the bus list fragment, to launch bus detail activity.
 */
class BusListNavigator @Inject constructor() {

    /**
     * This method is used to launch de detail activity using transitions, only used when the device has API 21 or higher
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun goToBusItemDetailWithTransition(origin: Activity, selectedBus: Bus?, sharedElements: List<View>, suffix: Int) {
        val intent = Intent(origin, MapActivity::class.java)
        intent.putExtra(BUS_KEY, selectedBus)
        intent.putExtra(TRANSITION_SUFFIX_KEY, suffix)

        val pairs = arrayOfNulls<Pair<View, String>>(sharedElements.size)
        for (i in 0 until sharedElements.size) {
            val viewAtIndex = sharedElements.get(i)
            pairs[i] = Pair(viewAtIndex, viewAtIndex.getTransitionName())
        }


        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(origin, *pairs)
        ActivityCompat.startActivity(origin, intent, optionsCompat.toBundle())
    }

    /**
     * This method is used to launch de detail activity without transitions. Used on devices with API lower than 21
     */
    fun goToBusItemDetailWithoutTransition(origin: Activity, selectedBus: Bus?) {
        val intent = Intent(origin, MapActivity::class.java)
        intent.putExtra(BUS_KEY, selectedBus)

        origin.startActivity(intent)
    }

}