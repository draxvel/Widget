package com.taboola.android.widget

import android.location.Location
import android.util.Log
import com.taboola.android.widget.network.Api
import com.taboola.android.widget.network.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherPresenter {

    //old key C0N7PgH6hHJxDwlCWIPN1jQaCsg95Zlc
    //lviv 49.80, 24.02

    private val api: Api = NetworkModule.provideApi()

    fun getCurrentTemperature(iGetCurrentTemperatureCallBack: IGetCurrentTemperatureCallBack, location: Location) {
        api.getCityByLocation("wq40Q9nmGih01MNBPP9ta1wM2o06ETSz", "${location.latitude}, ${location.longitude}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("draxvel", "get city from location: "+it.Key)

                api.get1DayOfDailyForecasts(it.Key, "wq40Q9nmGih01MNBPP9ta1wM2o06ETSz")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( { result ->
                        Log.d("draxvel", "get temp from city: "+result[0].Temperature.Metric.Value)
                        iGetCurrentTemperatureCallBack.onGet(result[0].Temperature.Metric.Value)

                    }, {
                       iGetCurrentTemperatureCallBack.onError()
                    })
            }
    }

    interface IGetCurrentTemperatureCallBack {
        fun onGet(str: String)
        fun onError()
    }

}