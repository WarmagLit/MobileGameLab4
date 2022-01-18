package com.tsu.mobilegamelab4.game.game

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class Game(context: Context) : SurfaceView(context),
    SurfaceHolder.Callback {
    private var joystickPointerId = 0
    //private val joystick: Joystick
    //private val player: Circle
    private var gameLoop: GameLoop
    private val performance: Performance
    //private val centeredGameDisplay: CenteredGameDisplay

    init {
        // Get surface holder and add callback
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)

        // Initialize game panels
        performance = Performance(gameLoop)
        //joystick = Joystick(275, 700, 70, 40)

        // Initialize game objects
        //player = Circle(context, R.color.purple_200, Point(100.0, 100.0), joystick)

        // Initialize display and center it around the player
        val displayMetrics = DisplayMetrics()
        (getContext() as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        //centeredGameDisplay = CenteredGameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player)

        isFocusable = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Handle user input touch event actions
        /*
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (joystick.isPressed) {
                    // Joystick was pressed
                    // **Some operation**
                } else if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    // Joystick is pressed in this event
                    joystickPointerId = event.getPointerId(event.actionIndex)
                    joystick.isPressed = true
                } else {
                    // Joystick was not previously, and is not pressed in this event
                    // **Some operation**
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (joystick.isPressed) {
                    // Joystick was pressed previously and is now moved
                    joystick.setActuator(event.x.toDouble(), event.y.toDouble())
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (joystickPointerId == event.getPointerId(event.actionIndex)) {
                    // joystick pointer was let go off
                    joystick.isPressed = false
                    joystick.resetActuator()
                }
                return true
            }
        }*/
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state.equals(Thread.State.TERMINATED)) {
            val surfaceHolder = getHolder()
            surfaceHolder.addCallback(this)
            gameLoop = GameLoop(this, surfaceHolder)
        }
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //Log
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //Log
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        // Draw game objects
        //player.draw(canvas, centeredGameDisplay)

        // Draw game panels
        //joystick.draw(canvas)
        performance.draw(canvas)
    }

    fun update() {
        // Update game state
        //joystick.update()
        //player.update()
        //performance.update(gameLoop.averageUPS, gameLoop.averageFPS)
    }

}