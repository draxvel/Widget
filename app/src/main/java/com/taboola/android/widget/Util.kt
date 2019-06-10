package com.taboola.android.widget

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

const val BASE_URL: String = "http://dataservice.accuweather.com/"
const val TIMEOUT_FOR_LOADING: Long = 5

fun Context.disableWidget(componentName: ComponentName) {
    run {
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)
    }
}