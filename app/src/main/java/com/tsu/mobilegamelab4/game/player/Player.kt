package com.tsu.mobilegamelab4.game.player

import android.graphics.Bitmap
import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Utils.getDistanceBetweenPoints
import com.tsu.mobilegamelab4.game.graphics.Sprite

class Player(pos: Point) : GameObject(pos) {

    var skin: Bitmap
    lateinit var sprite: List<Sprite>
    private var spriteUpdateCount = 0
    private var spriteIndex = 0
    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    init{
        skin = Utils.playerSkin!!
    }

    companion object {
        private const val CHANGE_SPRITE_UPDATES = 10
        private const val SPRITE_AMOUNT = 32
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            val coordinates = it.gameToDisplayCoordinates(pos)
            sprite[spriteIndex].draw(canvas, coordinates.X.toInt() - sprite[spriteIndex].size.x/2, coordinates.Y.toInt() - sprite[spriteIndex].size.y/2)
        }
 //       sprite[spriteIndex].draw(canvas, pos.X.toInt(), pos.Y.toInt())
    }

    fun changeVelocity(actuator: Vector) {
        // Update velocity based on actuator of joystick
        velocity.X = actuator.X * MAX_SPEED
        velocity.Y = actuator.Y * MAX_SPEED
    }

    override fun update() {
        // Update position
        pos.X += velocity.X
        pos.Y += velocity.Y

        // Sprite update
        spriteUpdateCount++
        if (spriteUpdateCount >= CHANGE_SPRITE_UPDATES) {
            spriteIndex++
            spriteUpdateCount = 0
            if (spriteIndex >= SPRITE_AMOUNT) {
                spriteIndex = 0
            }
        }

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