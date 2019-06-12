package com.taboola.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import java.util.*

class LocationManager {

    // location updates interval - 10sec
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private  var mCurrentLocation: Location? = null
    private lateinit var mLastUpdateTime: Date

    private val locationRequest = LocationRequest().apply {
        this.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        this.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            Log.d("draxvel", "in onLocationResult")

            super.onLocationResult(p0)
            mCurrentLocation = p0.lastLocation
            mLastUpdateTime = Date()
        }
    }

    @SuppressLint("MissingPermission")
    fun requestUpdates(context: Context) {
        Log.d("draxvel", "in requestUpdates")

        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mFusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback, Looper.myLooper());
    }

    fun removeUpdates() {
        Log.d("draxvel", "in removeUpdates")

        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(): Location? {
        Log.d("draxvel", "in getLastKnownLocation")
        return mCurrentLocation
    }
}