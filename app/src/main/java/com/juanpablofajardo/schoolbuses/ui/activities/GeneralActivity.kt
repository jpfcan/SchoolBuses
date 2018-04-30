package com.juanpablofajardo.schoolbuses.ui.activities

import android.os.Bundle
import android.support.annotation.IntDef
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.ui.fragments.BusListFragment
import com.juanpablofajardo.schoolbuses.ui.fragments.BusListFragment.Companion.BUS_LIST_KEY

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
class GeneralActivity: BaseActivity() {

    companion object {
        const val FRAGMENT_CASE_KEY = "fragment"
        const val BUS_LIST_FRAGMENT = 0x0001
    }

    @IntDef(BUS_LIST_FRAGMENT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class FragmentCases

    override fun getLayoutResource() = R.layout.activity_general

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent?.extras
        if (extras != null && extras.containsKey(FRAGMENT_CASE_KEY)) {
            @FragmentCases val fragmentCase = extras.getInt(FRAGMENT_CASE_KEY)
            when (fragmentCase) {
                BUS_LIST_FRAGMENT -> {
                    if (extras.containsKey(BUS_LIST_KEY)) {
                        executeFragment(BusListFragment.newInstance(extras.getParcelableArrayList(BUS_LIST_KEY)), false)
                    }
                }
            }
        }
    }

}