package com.tsu.mobilegamelab4.game

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.Window
import com.google.android.material.button.MaterialButton
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.SharedPreference
import com.tsu.mobilegamelab4.game.controls.Joystick
import com.tsu.mobilegamelab4.game.controls.SwipeStick
import com.tsu.mobilegamelab4.game.controls.TouchDistributor
import com.tsu.mobilegamelab4.game.controls.UseButton
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IUpdatable
import com.tsu.mobilegamelab4.game.interfaces.IUsable
import com.tsu.mobilegamelab4.game.level.Level
import com.tsu.mobilegamelab4.menu.MenuActivity


@SuppressLint("ViewConstructor")
class Game(private val activity: GameActivity, private val currentLevel: Level) :
    SurfaceView(activity),
    SurfaceHolder.Callback,
    IUpdatable {

    private val gameDisplay: GameDisplay
    private var gameLoop: GameLoop
    private val joystick: Joystick
    private val useButton: UseButton
    private val swipeStick: SwipeStick
    private val touchDistributor: TouchDistributor

    private val player: Player

    val performance: Performance

    // For sensors
    var sensorUpDown = 0.0
    var sensorSides = 0.0

    private val textPaint = Paint()

    var isJoystick = true
    var showPerformance = false

    init {
        // Metrics for SwipeStick and CenteredGameDisplay
        val displayMetrics = DisplayMetrics()
        (getContext() as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        Utils.setDisplayMetrics(context)

        // Check joystick or gyroscope from settings
        isJoystick = SharedPreference(context).getValueBoolean("control", true)
        showPerformance = SharedPreference(context).getValueBoolean("performance", false)

        // For Accelerometer values
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
        // player = Player(Point(1000.0, 1000.0), HeroSpriteSheet(context), tilemap.mapLayout, gameObjects)

        // Set enemy
//        enemy = Masker(
//            Point(400.0, 300.0),
//            EnemySpriteSheet(context),
//            player,
//            tilemap.mapLayout,
//            gameObjects
//        )

        //player.sprite = spriteSheet.playerSpriteArray
        player = currentLevel.initializePlayer(HeroSpriteSheet(context))

        // UseButton

        useButton = UseButton(
            player,
            currentLevel.gameObjects.filter { it is IUsable }.map { it as IUsable },
            Point(displayMetrics.widthPixels.toDouble() - 600.0, 900.0),
            100
        )

        // Joystick
        joystick = Joystick(player, Point(275.0, 700.0), 180, 80)

        // SwipeStick
        swipeStick =
            SwipeStick(player, Point(displayMetrics.widthPixels.toDouble() - 275.0, 700.0), 180, 80)

        // Touch Distributor
        touchDistributor = TouchDistributor(joystick, swipeStick, useButton)

        // Set up sensor stuff
        //sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Initialize display
        gameDisplay = GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player)

        isFocusable = true

        // Init all game objects
        //gameObjects.add(player)
        //gameObjects.add(enemy)
    }

    fun attachControlsToPlayer(player: Player) {
        joystick.player = player
        swipeStick.player = player
        gameDisplay.centerObject = player
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

        currentLevel.draw(canvas, gameDisplay)

        swipeStick.draw(canvas)

        useButton.draw(canvas)

        if (showPerformance) performance.draw(canvas)

        if (isJoystick) {
            joystick.draw(canvas)
        } else {
            extraDraw(canvas)
        }

        if (showPerformance) performance.draw(canvas)

        // Draw HealthBar ont hte top of all drawables
        player.healthBar.draw(canvas, gameDisplay)
    }

    private fun extraDraw(canvas: Canvas) {
        canvas.drawText(
            "Accelerometer: X:${sensorSides.toInt()} \n Y:${sensorUpDown.toInt()}",
            100f,
            400f,
            textPaint
        )
    }

    override fun update() {
//        for (obj in gameObjects) {
//            obj.update()
//            if (obj.toDestroy) {
//                gameObjects.remove(obj)
//                break
//            }
//        }
        if (player.toDestroy) showDialog()

        currentLevel.update()

        gameDisplay.update()

        useButton.update()

        swipeStick.update()

        if (isJoystick) {
            joystick.update()
        } else {
            joystick.player.changeVelocity(Vector(sensorUpDown / 100, sensorSides / 100))
        }
        //joystick.update()
    }

    fun pause() {
        gameLoop.stopLoop()
    }

    private fun showDialog() {
        activity.runOnUiThread {
            showDeathDialog()
        }
    }

    private fun showDeathDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_death_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnRestart = dialog.findViewById<MaterialButton>(R.id.dialogRestartButton)
        btnRestart.setOnClickListener {
            activity.restartLevel()
            dialog.dismiss()
        }
        val btnBackToMenu = dialog.findViewById<MaterialButton>(R.id.dialogBackToMenuButton)
        btnBackToMenu.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
        }

        dialog.show()
        pause()
    }
}