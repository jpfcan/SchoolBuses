package com.juanpablofajardo.schoolbuses.presenters

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 *
 * Interface to be implemented by all presenters, defines the necessary method to set the view
 */
interface BasePresenter<T> {

    fun setView(view: T)
    
}