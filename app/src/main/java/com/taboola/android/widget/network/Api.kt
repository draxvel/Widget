package com.taboola.android.widget.network

import com.taboola.android.widget.model.Temperature
import com.taboola.android.widget.model.Location
import com.taboola.android.widget.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("currentconditions/v1/{locationKey}")
    fun get1DayOfDailyForecasts (@Path("locationKey") locationKey: Int, @Query("apikey") apiKey: String): Observable<List<Response>>

    @GET("locations/v1/cities/geoposition/search")
    fun getCityByLocation (@Query("apikey") apiKey: String, @Query("q") q: String): Observable<Location>

}