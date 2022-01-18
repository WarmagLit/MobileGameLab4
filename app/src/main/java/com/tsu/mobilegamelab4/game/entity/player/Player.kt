package com.tsu.mobilegamelab4.game.entity.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Utils.getDistanceBetweenPoints
import com.tsu.mobilegamelab4.game.entity.Entity
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.map.MapLayout
import com.tsu.mobilegamelab4.game.map.TileType
import com.tsu.mobilegamelab4.game.entity.player.guns.Bullet
import com.tsu.mobilegamelab4.game.entity.player.guns.Gun

class Player(
    pos: Point,
    spriteSheet: HeroSpriteSheet,
    private val mapLayout: MapLayout,
    private val gameObjects: MutableList<GameObject>
) :
    Entity(pos, mapLayout, gameObjects),
    ICollideable {


    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    private var actX = 0.0
    private var actY = 0.0

    private val textPaint = Paint()

    private val animator = HeroAnimator(spriteSheet)

    private val gun: Gun

    init {
        textPaint.color = Color.CYAN
        textPaint.textSize = 50f

        hitbox = Hitbox(this, 150, 200)

        gun = Gun(this, Utils.displayCenter, spriteSheet.gunSprite, gameObjects)
    }


    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            gun.draw(canvas)
            animator.draw(
                canvas,
                displayCoordinates.X.toInt() - animator.spriteStay.first().size.x / 2,
                displayCoordinates.Y.toInt() - animator.spriteStay.first().size.y / 2
            )
        }
    }


    override fun changeVelocity(actuator: Vector) {
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


        if (TileType.values()[mapLayout.layout[(pos.Y / MapLayout.TILE_HEIGHT_PIXELS).toInt()][(pos.X / MapLayout.TILE_WIDTH_PIXELS).toInt()]] == TileType.WATER_TILE) {
            pos.X -= velocity.X
            pos.Y -= velocity.Y
        }
        if (collideCheck()) {
            pos.X -= 0.2 * velocity.X
            pos.Y -= 0.2 *velocity.Y
        }

        gun.update()
        hitbox.updateCoord(displayCoordinates)

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

    override fun collideCheck(): Boolean {
        for (obj in gameObjects) {
            if (obj != this && obj.hitbox.isCollide(hitbox))
                return true
        }
        return false
    }

    override fun hit(bullet: Bullet) {
        // do nothing
    }
}