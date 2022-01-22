package com.tsu.mobilegamelab4.game

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.mobilegamelab4.chooselevel.ChooseLevelActivity
import com.tsu.mobilegamelab4.chooselevel.ChooseLevelViewModel
import com.tsu.mobilegamelab4.databinding.ActivityGameBinding
import com.tsu.mobilegamelab4.game.surfaceview.Game
import com.tsu.mobilegamelab4.game.surfaceview.GameViewModel
import com.tsu.mobilegamelab4.game.surfaceview.graphics.BossSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.KeySpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.level.FirstLevel

class GameActivity : AppCompatActivity(),
    SensorEventListener {

    private lateinit var binding: ActivityGameBinding
    private val viewModel by viewModels<GameViewModel>()

    // For sensors
    private lateinit var sensorManager: SensorManager

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        startLevel(intent.getIntExtra(ChooseLevelViewModel.LEVEL_KEY, 0))
        configureSensorForAccelerometer()
    }

    private fun setObservers() {
        viewModel.isJoystick.observe(this) {
            game.isJoystick = it
        }

        viewModel.showPerformance.observe(this) {
            game.showPerformance = it
        }

        viewModel.levelsCompleted.observe(this) {
            game.userCompletedLevels = it
        }

        game.levelsCompleted.observe(this) {
            viewModel.updateCompletedLevels(it)
        }
    }

    private fun startLevel(level: Int) {
        game = when (level) {
            1 -> Game(
                this,
                FirstLevel(
                    EnemySpriteSheet(this),
                    BossSpriteSheet(this),
                    FirstLocationSpriteSheet(this),
                    KeySpriteSheet(this)
                )
            )
            2 -> Game(
                this,
                FirstLevel(
                    EnemySpriteSheet(this),
                    BossSpriteSheet(this),
                    FirstLocationSpriteSheet(this),
                    KeySpriteSheet(this)
                )
            )
            3 -> Game(
                this,
                FirstLevel(
                    EnemySpriteSheet(this),
                    BossSpriteSheet(this),
                    FirstLocationSpriteSheet(this),
                    KeySpriteSheet(this)
                )
            )
            else -> throw Exception("Wrong level")
        }
        setObservers()
        setContentView(game)
    }

    fun restartLevel(level: Int) {

        startLevel(level)
    }

    private fun configureSensorForAccelerometer() {
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

    override fun onBackPressed() {
        val intent = Intent(this, ChooseLevelActivity::class.java)
        startActivity(intent)
    }
}