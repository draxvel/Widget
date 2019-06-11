package com.taboola.android.widget

import android.util.Log
import com.taboola.android.widget.network.Api
import com.taboola.android.widget.network.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherPresenter {

    private val api: Api = NetworkModule.provideApi()

    fun getCurrentTemperature(iGetCurrentTemperatureCallBack: IGetCurrentTemperatureCallBack) {
        api.getCityByLocation("C0N7PgH6hHJxDwlCWIPN1jQaCsg95Zlc", "49.80, 24.02")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("draxvel", "get city from location: "+it.Key)

                api.get1DayOfDailyForecasts(it.Key, "C0N7PgH6hHJxDwlCWIPN1jQaCsg95Zlc")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        Log.d("draxvel", "get temp from city: "+result[0].Temperature.Metric.Value)
                        iGetCurrentTemperatureCallBack.onGet(result[0].Temperature.Metric.Value)
                    }
            }
    }

    interface IGetCurrentTemperatureCallBack {
        fun onGet(str: String)
        fun onError()
    }

}