package com.tsu.mobilegamelab4.game

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet
import com.tsu.mobilegamelab4.game.map.Tilemap
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
    private val touchDistributor: TouchDistributor
    private val spriteSheet: SpriteSheet
    val performance: Performance


    init {
        // Get surface holder and add callback
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)

        // Initialize game panels
        performance = Performance()

        // Set player
        Utils.setPlayerSkin(context)
        player = Player(Point(100.0, 100.0))
        spriteSheet = SpriteSheet(context)
        player.sprite = spriteSheet.playerSpriteArray

        // Joystick
        joystick = Joystick(player, Point(275.0, 700.0), 110, 50)

        // Touch Distributor
        touchDistributor = TouchDistributor(joystick)

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
        joystick.draw(canvas)
    }

    override fun update() {
        player.update()
        joystick.update()
        gameDisplay.update()
    }
}