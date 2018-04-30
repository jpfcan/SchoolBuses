package com.juanpablofajardo.schoolbuses.app

import com.juanpablofajardo.schoolbuses.ui.activities.MapActivity
import com.juanpablofajardo.schoolbuses.ui.fragments.BusListFragment
import dagger.Component

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
@Component
interface AppComponent {

    fun inject(fragment: BusListFragment)

    fun inject(activity: MapActivity)

}