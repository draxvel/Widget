package com.taboola.android.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.taboola.android.widget.network.Api
import com.taboola.android.widget.network.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Implementation of App Widget functionality.
 */
class AppWidget4x1 : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        api = NetworkModule.provideApi()
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        private var wasFirstUpdate: Boolean = false

        lateinit var api: Api


        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            if(!wasFirstUpdate) {
                //disable items in user widget list
                context.disableWidget(ComponentName(context, AppWidget4x1::class.java))
                context.disableWidget(ComponentName(context, AppWidget5x1::class.java))
                wasFirstUpdate = true
            }

            val widgetText = context.getString(R.string.appwidget4x1_text)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.app_widget4x1)
//            views.setTextViewText(R.id.appwidget_text, widgetText)


            Log.d("draxvel", "in updateAppWidget")

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
                            views.setTextViewText(R.id.appwidget_text, result[0].Temperature.Metric.Value)
                            appWidgetManager.updateAppWidget(appWidgetId, views)
                        }
                }

            // Instruct the widget manager to update the widget
        }
    }
}

