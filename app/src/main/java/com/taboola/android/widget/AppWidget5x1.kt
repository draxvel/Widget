package com.taboola.android.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class AppWidget5x1 : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
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
    }

    companion object {

        private var wasFirstUpdate: Boolean = false

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


            val widgetText = context.getString(R.string.appwidget5x1_text)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.app_widget5x1)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

