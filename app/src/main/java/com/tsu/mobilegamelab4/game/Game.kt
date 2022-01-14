package com.tsu.mobilegamelab4.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet
import com.tsu.mobilegamelab4.game.map.Tilemap
import com.tsu.mobilegamelab4.SharedPreference
import com.tsu.mobilegamelab4.game.controls.Joystick
import com.tsu.mobilegamelab4.game.controls.SwipeStick
import com.tsu.mobilegamelab4.game.controls.TouchDistributor
import com.tsu.mobilegamelab4.game.interfaces.IUpdatable
import com.tsu.mobilegamelab4.game.player.Animator
import com.tsu.mobilegamelab4.game.player.Player
import android.app.Activity

import android.util.DisplayMetrics




class Game(context: Context) : SurfaceView(context),
    SurfaceHolder.Callback,
    IUpdatable {

    private val tilemap = Tilemap(MapSpriteSheet(context))
    private val gameDisplay: GameDisplay

    private var gameLoop: GameLoop
    private val player: Player
    private val joystick: Joystick
    private var joystickPointerId = 0
    private val swipeStick: SwipeStick
    private var swipeStickPointerId = 1
    private val touchDistributor: TouchDistributor
    private val animator: Animator
    private val spriteSheet: SpriteSheet
    val performance: Performance

    // For sensors
    var sensorUpDown = 0.0
    var sensorSides = 0.0
    private val textPaint = Paint()

    var isJoystick = true

    init {
        // Metrics for SwipeStick and CenteredGameDisplay
        val displayMetrics = DisplayMetrics()
        (getContext() as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        spriteSheet = SpriteSheet(context)

        // Check joystick or gyroscope from settings
        isJoystick = SharedPreference(context).getValueBoolean("control", true)

        textPaint.color = Color.CYAN
        textPaint.textSize = 50f

        // Get surface holder and add callback
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)

        // Initialize game panels
        performance = Performance()

        // Set player
        Utils.setPlayerSkin(context)
        animator = Animator(spriteSheet)
        player = Player(Point(100.0, 100.0), animator, spriteSheet)
        //player.sprite = spriteSheet.playerSpriteArray

        // Joystick
        joystick = Joystick(player, Point(275.0, 700.0), 110, 50)

        // SwipeStick
        swipeStick = SwipeStick(player, Point(displayMetrics.widthPixels.toDouble() - 275.0, 700.0),  110, 50)

        // Touch Distributor
        touchDistributor = TouchDistributor(joystick, swipeStick)

        // Set up sensor stuff
        //sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Initialize display
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        gameDisplay = GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player)

        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state.equals(Thread.State.TERMINATED)) {
            holder.addCallback(this)
            gameLoop = GameLoop(this, holder)
        }
        gameLoop.startLoop()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (touchDistributor.handleTouch(event)) {
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        tilemap.draw(canvas, gameDisplay)

        performance.draw(canvas)
        player.draw(canvas, gameDisplay)

        swipeStick.draw(canvas)

        if (isJoystick) {
            joystick.draw(canvas)
        }
        else {
            extraDraw(canvas)
        }
    }

    private fun extraDraw(canvas: Canvas) {
        canvas.drawText("Gyro: X:${sensorSides.toInt()} \n Y:${sensorUpDown.toInt()}", 100f, 400f, textPaint)
    }

    override fun update() {
        player.update()
        gameDisplay.update()
        swipeStick.update()

        if (isJoystick) {
            joystick.update()
        }
        else {
            player.changeVelocity(Vector(sensorUpDown/100, sensorSides/100))
        }
        //joystick.update()
        //player.changeVelocity(Vector(sensorUpDown/100, sensorSides/100))
    }

    fun pause() {
        gameLoop.stopLoop()
    }

}