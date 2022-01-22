package com.tsu.mobilegamelab4.game.surfaceview

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import java.lang.Exception
import java.lang.IllegalArgumentException

class GameLoop(private val game: Game, private val surfaceHolder: SurfaceHolder) : Thread() {
    private var isRunning = false

    // Declare time and cycle count variables
    private var updateCount = 0
    private var frameCount = 0
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var sleepTime: Long = 0

    fun startLoop() {
        isRunning = true
        start()
    }

    override fun run() {
        super.run()

        // Game loop
        var canvas: Canvas? = null
        startTime = System.currentTimeMillis()
        while (isRunning) {
            // Try to update and render game
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    game.update()
                    updateCount++
                    game.draw(canvas)
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            // Pause game loop to not exceed target UPS
            pauseToKeepTargetUPS()

            // Skip frames to keep up with target UPS
            skipFramesToKeepTargetUPS()

            // Calculate average UPS and FPS
            calculatePerformance()
        }
    }

    private fun pauseToKeepTargetUPS() {
        elapsedTime = System.currentTimeMillis() - startTime
        sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
        if (sleepTime > 0) {
            try {
                sleep(sleepTime)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun skipFramesToKeepTargetUPS() {
        while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
            game.update()
            updateCount++
            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
        }
    }

    private fun calculatePerformance() {
        elapsedTime = System.currentTimeMillis() - startTime
        if (elapsedTime >= 1000) {
            game.performance.setPerformance(
                updateCount / (1E-3 * elapsedTime),
                frameCount / (1E-3 * elapsedTime)
            )
            updateCount = 0
            frameCount = 0
            startTime = System.currentTimeMillis()
        }
    }

    fun stopLoop() {
        Log.d("GameLoop", "stopLoop()")
        isRunning = false
        try {
            join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val MAX_UPS = 60.0
        private const val UPS_PERIOD = 1E+3 / MAX_UPS
    }
}