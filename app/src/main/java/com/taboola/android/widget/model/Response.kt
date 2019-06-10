package com.taboola.android.widget.model

class Response {

    val Temperature: Temperature = Temperature()

}

class Temperature {

    val Metric: Metric = Metric()
}

class Metric {

    val Value: String = "test"
}



