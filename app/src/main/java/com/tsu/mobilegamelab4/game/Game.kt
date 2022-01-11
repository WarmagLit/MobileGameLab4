package com.tsu.mobilegamelab4.game

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.tsu.mobilegamelab4.game.player.Animator
import com.tsu.mobilegamelab4.game.player.Player

class Game(context: Context) : SurfaceView(context),
    SurfaceHolder.Callback,
    IUpdatable {

    private var gameLoop: GameLoop
    private val player: Player
    private val joystick: Joystick
    private var joystickPointerId = 0
    private val touchDistributor: TouchDistributor
    private val animator: Animator
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
        animator = Animator(SpriteSheet(context))
        player = Player(Point(100.0, 100.0), animator)
        //player.sprite = spriteSheet.playerSpriteArray

        // Joystick
        joystick = Joystick(player,Point(275.0, 700.0),  110, 50)

        // Touch Distributor
        touchDistributor = TouchDistributor(joystick)

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

        performance.draw(canvas)
        player.draw(canvas)
        joystick.draw(canvas)
    }

    override fun update() {
        player.update()
        joystick.update()
    }
}