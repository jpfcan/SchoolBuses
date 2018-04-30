package com.juanpablofajardo.schoolbuses.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
abstract class BaseFragment: Fragment() {

    abstract fun getLayoutResource(): Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(getLayoutResource(), container, false)

    protected fun showLoadingView() {
        if (loading_view != null) {
            loading_view.visibility = View.VISIBLE
        }
    }

    protected fun hideLoadingView() {
        if (loading_view != null) {
            loading_view.visibility = View.GONE
        }
    }
}