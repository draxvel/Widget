package com.taboola.android.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class AppWidget4x1 : AppWidgetProvider() {

    private var wasFirstUpdate: Boolean = false

    private val locationManager: LocationManager = LocationManager()

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        Log.d("draxvel", "in onUpdate")

        if(!wasFirstUpdate) {
            //disable items in user widget list
//            context.disableWidget(ComponentName(context, AppWidget4x1::class.java))
            context.disableWidget(ComponentName(context, AppWidget5x1::class.java))
            Log.d("draxvel", "in disableWidget")

            wasFirstUpdate = true
            locationManager.requestUpdates(context)
        }

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        locationManager.removeUpdates()
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        return Intent(action)
            .let { intent -> PendingIntent.getBroadcast(context, 0, intent, 0) }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        Log.d("draxvel", "in updateAppWidget")

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        val views: RemoteViews = RemoteViews(context.packageName, R.layout.app_widget4x1)
            .apply { setOnClickPendingIntent(R.id.relative_layout, getPendingSelfIntent(context, AppWidgetManager.ACTION_APPWIDGET_UPDATE))
        }

        locationManager.getLastKnownLocation()?.let {

            Log.d("draxvel", "latitude: "+it.latitude)
            Log.d("draxvel", "latitude: "+it.longitude)

//            WeatherPresenter().getCurrentTemperature(iGetCurrentTemperatureCallBack = object : WeatherPresenter.IGetCurrentTemperatureCallBack{
//
//                override fun onGet(str: String) {
//                    Log.d("draxvel", str)
//                    views.setTextViewText(R.id.appwidget_text, str)
//                    appWidgetManager.updateAppWidget(appWidgetId, views)
//                }
//
//                override fun onError() {
//                    Log.d("draxvel", "in onError")
//                }
//            }, location = it)
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun callOnUpdate(context: Context) {

        Log.d("draxvel", "in callOnUpdate")

        val appWidgetManager = AppWidgetManager.getInstance(context)

        val thisAppWidgetComponentName = ComponentName(
            context.packageName, javaClass.name
        )
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            thisAppWidgetComponentName
        )
        onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        Log.d("draxvel", "in onReceive action "+intent.action)

        if(intent.action == "android.appwidget.action.APPWIDGET_UPDATE") {
            callOnUpdate(context)
        }
    }
}

