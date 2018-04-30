package com.juanpablofajardo.schoolbuses.presenters

/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
interface BasePresenter<T> {

    fun setView(view: T)
    
}