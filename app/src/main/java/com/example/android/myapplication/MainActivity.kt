package com.example.android.myapplication
import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity(), SensorEventListener {
    lateinit var sensorManager: SensorManager
    var lightSensor: Sensor? = null
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        Log.d("MyApp", "onCreate called")
        imageView = findViewById(R.id.imageView)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Provjera postoji li senzor svjetlosti
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null) {
            // Ako ne postoji, prikaži poruku
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
                val lightValue = event.values[0]

                // Prag svjetlosti
                val threshold = 250f

                if (lightValue > threshold) {
                    // Ako je svjetlost iznad praga, postavi određenu sliku
                    val drawable: Drawable = resources.getDrawable(R.drawable.slikaa, null)
                    imageView.setImageDrawable(drawable)
                } else {
                    // Ako je svjetlost ispod praga, postavi drugu sliku
                    val drawable: Drawable = resources.getDrawable(R.drawable.sad, null)
                    imageView.setImageDrawable(drawable)
                }
            }

        }
        catch (e: Exception){
            e.printStackTrace()
            Log.e("SensorManager", "Exception handling sensor event: ${e.message}")
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}