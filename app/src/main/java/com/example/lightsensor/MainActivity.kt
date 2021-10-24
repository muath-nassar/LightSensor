package com.example.lightsensor

import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var tvLightValue: TextView
    lateinit var tvMaxRange : TextView
    lateinit var sm : SensorManager
    lateinit var background: ConstraintLayout
    var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        background = findViewById(R.id.background)
        tvLightValue = findViewById(R.id.tvLightValue)
        tvMaxRange = findViewById(R.id.tvMaxRange)
        setupSensorManager()
        sensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()
        sensor?.let {
            sm.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensor?.let {
            sm.unregisterListener(this,it)
        }


    }

    private fun setupSensorManager() {
        sm =getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_LIGHT){
            val light = event.values[0]
            tvLightValue.text = "The max = $light"
            tvMaxRange.text = "the max range = ${event.sensor.maximumRange}"
            if (light >= 100){
                background.background = getDrawable(R.drawable.morning)
            }else{

                background.background = getDrawable(R.drawable.night)
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}