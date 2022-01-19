package com.tsu.mobilegamelab4.game

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.databinding.ActivityGameBinding
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.level.FirstLevel
import java.lang.Exception
import java.lang.reflect.Executable

class GameActivity : AppCompatActivity(),
    SensorEventListener {

    private lateinit var binding: ActivityGameBinding

    // For sensors
    private lateinit var sensorManager: SensorManager

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        val level = intent.getIntExtra("level", 0)

        when(level) {
            1 -> game = Game(this, FirstLevel(EnemySpriteSheet(this), FirstLocationSpriteSheet(this)))
            2 -> game = Game(this, FirstLevel(EnemySpriteSheet(this), FirstLocationSpriteSheet(this)))
            3 -> game = Game(this, FirstLevel(EnemySpriteSheet(this), FirstLocationSpriteSheet(this)))
            else -> throw Exception("Wrong level")
        }

        setContentView(game)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onPause() {
        super.onPause()
        game.pause()
        Log.d("PAUS", "We are paused")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val sides = event.values[0]
            val upDown = event.values[1]

            game.sensorSides = sides * 10.0
            game.sensorUpDown = upDown * 10.0
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}