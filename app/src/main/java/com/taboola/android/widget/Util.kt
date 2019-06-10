package com.taboola.android.widget

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

fun Context.disableWidget(componentName: ComponentName) {
    run {
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)
    }
}