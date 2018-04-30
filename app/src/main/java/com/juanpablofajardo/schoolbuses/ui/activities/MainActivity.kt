package com.juanpablofajardo.schoolbuses.ui.activities

import android.content.DialogInterface
import android.content.DialogInterface.*
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.app.AppManager
import com.juanpablofajardo.schoolbuses.models.BusesModel
import com.juanpablofajardo.schoolbuses.objects.Bus
import com.juanpablofajardo.schoolbuses.objects.Responses.BusListResponse
import com.juanpablofajardo.schoolbuses.ui.activities.GeneralActivity.Companion.BUS_LIST_FRAGMENT
import com.juanpablofajardo.schoolbuses.ui.activities.GeneralActivity.Companion.FRAGMENT_CASE_KEY
import com.juanpablofajardo.schoolbuses.ui.fragments.BusListFragment.Companion.BUS_LIST_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : BaseActivity(), OnClickListener, BusesModel.BusListListener {

    companion object {
        val MINIMUM_SPLASH_TIME = 3000
    }

    @Inject lateinit var busesModel: BusesModel
    val initTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.DAGGER_COMPONENT.inject(this)
        fetchBusesInformation()
    }

    private fun fetchBusesInformation() {
        busesModel.fetchBusList(this)


        AppManager.RETROFIT_INSTANCE.getBuses().enqueue(object: Callback<BusListResponse> {
            override fun onResponse(call: Call<BusListResponse>?, response: Response<BusListResponse>?) {
                if (response != null && response.isSuccessful && response.body() != null) {

                }
            }

            override fun onFailure(call: Call<BusListResponse>?, t: Throwable?) {

            }
        })
    }

    override fun onBusListFetchSuccess(busListResponse: BusListResponse) {
        val endTime = System.currentTimeMillis()
        val elapsedTime = endTime - initTime
        if (elapsedTime < MINIMUM_SPLASH_TIME) {
            launchBusesListScreen(MINIMUM_SPLASH_TIME - elapsedTime, busListResponse.busList)
        } else {
            launchBusesListScreen(0, busListResponse.busList)
        }
    }

    override fun onBusListFetchError() {
        showConnectionAlert()
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

    private fun showConnectionAlert() {
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogTheme)
        val inflater = getLayoutInflater()

        val dialogView = inflater.inflate(R.layout.dialog_connection_error, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton(R.string.retry, this)
        dialogBuilder.setNegativeButton(R.string.close, this)

        val dialog = dialogBuilder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            BUTTON_POSITIVE -> fetchBusesInformation()
            BUTTON_NEGATIVE -> finish()
        }
    }

    override fun getLayoutResource() = R.layout.activity_main

}
