package com.pascal.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val queue= Volley.newRequestQueue(this)
        val url="http://api.weatherapi.com/v1/current.json?key=f269d6ac5ca5477896375924220208&q=London"
        val request= JsonObjectRequest(Request.Method.GET,url, null,
            {
            weatherJsonObject ->
                val locationObject= weatherJsonObject.getJSONObject("location")
                val currentObject= weatherJsonObject.getJSONObject("current")
                val temperature= currentObject.getString("temp_c")+"°C"
                val updatedAt=currentObject.get("last_updated")
                val updatedAtText="Updated at $updatedAt"


                val location=locationObject.get("name")
                val pressure= currentObject.getString("pressure_mb")
                val humidity= currentObject.getString("humidity")
                val wind= currentObject.getString("wind_kph")+"kph"
                val cloud= currentObject.getString("cloud")
                val precipitation= currentObject.getString("precip_mm")+"mm"
               
                val weatherDescription=currentObject.getJSONObject("condition").get("text")
                



                findViewById<TextView>(R.id.address).text=location.toString()
                findViewById<TextView>(R.id.status).text= weatherDescription.toString()
                findViewById<TextView>(R.id.temperature).text= temperature
                findViewById<TextView>(R.id.cloud).text= cloud
                findViewById<TextView>(R.id.precipitation).text= precipitation
                findViewById<TextView>(R.id.wind).text= wind
                findViewById<TextView>(R.id.pressure).text= pressure
                findViewById<TextView>(R.id.humidity).text= humidity
                findViewById<TextView>(R.id.updated_at).text=updatedAtText

                findViewById<ProgressBar>(R.id.loader).visibility=View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility=View.VISIBLE


            },
            {
                error ->
                findViewById<ProgressBar>(R.id.loader).visibility=View.VISIBLE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility=View.GONE
                findViewById<TextView>(R.id.errorText).visibility=View.VISIBLE
                error
            })
        queue.add(request)
    }
}