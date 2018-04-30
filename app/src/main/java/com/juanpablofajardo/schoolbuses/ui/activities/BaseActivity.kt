package com.juanpablofajardo.schoolbuses.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.R.id.content


/**
 * Created by Juan Pablo Fajardo Cano on 4/24/18.
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
    }

    protected fun setToolbarWithCloseIcon() {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun executeFragment(fragmentToExecute: Fragment, addToBackstack: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.content, fragmentToExecute)
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragmentToExecute.tag)
        }
        fragmentTransaction.commit()
    }

    abstract fun getLayoutResource(): Int

}