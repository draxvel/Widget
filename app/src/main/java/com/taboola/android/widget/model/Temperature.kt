package com.taboola.android.widget.model

class DailyForecasts {

    val temperature: Temperature = Temperature()

    class Temperature {

        val maximum: Temperature.Maximum = Maximum()

        class Maximum {

            val Unit: String = ""
        }
    }
}



