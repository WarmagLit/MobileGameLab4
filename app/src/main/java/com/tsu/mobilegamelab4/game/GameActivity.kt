package com.tsu.mobilegamelab4.game

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.chooselevel.ChooseLevelActivity
import com.tsu.mobilegamelab4.databinding.ActivityGameBinding
import com.tsu.mobilegamelab4.game.graphics.*
import com.tsu.mobilegamelab4.game.level.BonusLevel
import com.tsu.mobilegamelab4.game.level.FirstLevel
import com.tsu.mobilegamelab4.game.level.SecondLevel
import com.tsu.mobilegamelab4.game.level.ThirdLevel
import java.lang.Exception
import java.lang.reflect.Executable

class GameActivity : AppCompatActivity(),
    SensorEventListener {

    private lateinit var binding: ActivityGameBinding

    // For sensors
    private lateinit var sensorManager: SensorManager

    private lateinit var game: Game

    var level: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        level = intent.getIntExtra("level", 0)

        when(level) {
            3 -> game = Game(this, BonusLevel(EnemySpriteSheet(this), BossSpriteSheet(this), BonusLocationSpriteSheet(this), KeySpriteSheet(this)))
            1 -> game = Game(this, FirstLevel(EnemySpriteSheet(this), BossSpriteSheet(this), FirstLocationSpriteSheet(this), KeySpriteSheet(this)))
            2 -> game = Game(this, SecondLevel(EnemySpriteSheet(this), BossSpriteSheet(this), SecondLocationSpriteSheet(this), KeySpriteSheet(this)))
            4 -> game = Game(this, ThirdLevel(EnemySpriteSheet(this), BossSpriteSheet(this), ThirdLocationSpriteSheet(this), KeySpriteSheet(this)))
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

    fun restartLevel() {
        when(level) {
           3 -> game = Game(this, BonusLevel(EnemySpriteSheet(this), BossSpriteSheet(this), BonusLocationSpriteSheet(this), KeySpriteSheet(this)))
            1 -> game = Game(this, FirstLevel(EnemySpriteSheet(this), BossSpriteSheet(this), FirstLocationSpriteSheet(this), KeySpriteSheet(this)))
            2 -> game = Game(this, SecondLevel(EnemySpriteSheet(this), BossSpriteSheet(this), SecondLocationSpriteSheet(this), KeySpriteSheet(this)))
            4 -> game = Game(this, ThirdLevel(EnemySpriteSheet(this), BossSpriteSheet(this), ThirdLocationSpriteSheet(this), KeySpriteSheet(this)))
            else -> throw Exception("Wrong level")
        }

        setContentView(game)

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

    override fun onBackPressed() {
        val intent = Intent(this, ChooseLevelActivity::class.java)
        startActivity(intent)
    }
}