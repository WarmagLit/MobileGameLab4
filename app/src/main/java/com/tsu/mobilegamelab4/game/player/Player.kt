package com.tsu.mobilegamelab4.game.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Utils.getDistanceBetweenPoints
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.map.MapLayout
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMapLayout
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationTilemap
import com.tsu.mobilegamelab4.game.player.guns.Gun

class Player(pos: Point, spriteSheet: HeroSpriteSheet, private val tilemap: FirstLocationTilemap) :
    GameObject(pos) {

    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    private var actX = 0.0
    private var actY = 0.0

    private val textPaint = Paint()

    private val animator = Animator(spriteSheet)

    private val gun: Gun

    init {
        textPaint.color = Color.CYAN
        textPaint.textSize = 50f

        gun = Gun(pos, spriteSheet.gunSprite)
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            val coordinates = it.gameToDisplayCoordinates(pos)
            gun.draw(canvas)
            animator.draw(
                canvas,
                coordinates.X.toInt() - animator.spriteStay.first().size.x / 2,
                coordinates.Y.toInt() - animator.spriteStay.first().size.y / 2
            )
        }
    }

    fun changeVelocity(actuator: Vector) {
        // Update velocity based on actuator of joystick
        velocity.X = actuator.X * MAX_SPEED
        velocity.Y = actuator.Y * MAX_SPEED

        actX = actuator.X
        actY = actuator.Y
    }

    fun attack(actuator: Vector) {
        Log.d("Attack", "At X:${actuator.X} Y: ${actuator.Y}")
        gun.fire(Vector(actuator.X * 50, actuator.Y * 50))
    }

    override fun update() {
        // Update position
        pos.X += velocity.X
        pos.Y += velocity.Y

        //obstacle check
        if (tilemap.mapLayout.layout[(pos.Y / FirstLocationMapLayout.TILE_HEIGHT_PIXELS).toInt()][(pos.X / FirstLocationMapLayout.TILE_WIDTH_PIXELS).toInt()] == 1) {
            pos.X -= velocity.X
            pos.Y -= velocity.Y
        }

        gun.update()

        // Animator update
        animator.changeDirection(velocity)
        animator.update()

        // Update direction
        if (velocity.X != 0.0 || velocity.Y != 0.0) {
            // Normalize velocity to get direction (unit vector of velocity)
            val distance: Double =
                getDistanceBetweenPoints(Point(0.0, 0.0), Point(velocity.X, velocity.Y))
            direction.X = velocity.X / distance
            direction.Y = velocity.Y / distance
        }
    }
}