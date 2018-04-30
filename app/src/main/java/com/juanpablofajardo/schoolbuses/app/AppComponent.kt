package com.juanpablofajardo.schoolbuses.app

import com.juanpablofajardo.schoolbuses.ui.activities.MainActivity
import com.juanpablofajardo.schoolbuses.ui.activities.MapActivity
import com.juanpablofajardo.schoolbuses.ui.fragments.BusListFragment
import dagger.Component

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 *
 * Interface to be used by Dagger to inject dependencies
 */
@Component
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: BusListFragment)

    fun inject(activity: MapActivity)

}