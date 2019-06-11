package com.taboola.android.widget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.taboola.android.widget.network.Api
import com.taboola.android.widget.network.NetworkModule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("draxvel", "MainActivity")
    }
}
