package com.juanpablofajardo.schoolbuses.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.app.AppManager
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.objects.Responses.BusListResponse
import com.juanpablofajardo.schoolbuses.ui.activities.GeneralActivity.Companion.BUS_LIST_FRAGMENT
import com.juanpablofajardo.schoolbuses.ui.activities.GeneralActivity.Companion.FRAGMENT_CASE_KEY
import com.juanpablofajardo.schoolbuses.ui.fragments.BusListFragment.Companion.BUS_LIST_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    companion object {
        val MINIMUM_SPLASH_TIME = 3000
    }

    override fun getLayoutResource() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchBusesInformation()
    }

    private fun fetchBusesInformation() {
        val initTime = System.currentTimeMillis()

        AppManager.RETROFIT_INSTANCE.getBuses().enqueue(object: Callback<BusListResponse> {
            override fun onResponse(call: Call<BusListResponse>?, response: Response<BusListResponse>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    val endTime = System.currentTimeMillis()
                    val elapsedTime = endTime - initTime
                    if (elapsedTime < MINIMUM_SPLASH_TIME) {
                        launchBusesListScreen(MINIMUM_SPLASH_TIME - elapsedTime, response.body()?.busList)
                    } else {
                        launchBusesListScreen(0, response.body()?.busList)
                    }
                }
            }

            override fun onFailure(call: Call<BusListResponse>?, t: Throwable?) {
                //TODO show message for retry
            }
        })
    }

    private fun launchBusesListScreen(delay: Long, busesList: List<Bus>?) {
        Handler().postDelayed({
            val intent = Intent(this, GeneralActivity::class.java)
            intent.putExtra(FRAGMENT_CASE_KEY, BUS_LIST_FRAGMENT)
            intent.putParcelableArrayListExtra(BUS_LIST_KEY, ArrayList(busesList))
            startActivity(intent)
            finish()
        }, delay)
    }

}
