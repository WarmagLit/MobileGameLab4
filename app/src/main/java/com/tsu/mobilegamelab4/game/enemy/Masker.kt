package com.tsu.mobilegamelab4.game.enemy

import android.graphics.Canvas
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.map.MapLayout
import com.tsu.mobilegamelab4.game.map.TileType
import com.tsu.mobilegamelab4.game.player.Player
import com.tsu.mobilegamelab4.game.player.guns.Bullet

class Masker(
    pos: Point,
    spriteSheet: EnemySpriteSheet,
    player: Player,
    private val mapLayout: MapLayout,
    private val gameObjects: MutableList<GameObject>
) :
    Enemy(pos, spriteSheet, mapLayout, player), ICollideable {

    private var actX = 0.0
    private var actY = 0.0

    private var HP = 100

    private val animator = EnemyAnimator(spriteSheet)

    var displayCoordinates = Point(0.0, 0.0)

    init {
        hitbox = Hitbox(this, 155, 180)
    }

    companion object {
        private const val SPEED_PIXELS_PER_SECOND = 200.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        private const val SEEK_DISTANCE = 400.0
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            //hitbox.draw(canvas, display)
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
    }


    override fun update() {
        if (Utils.getDistanceBetweenPoints(player.pos, pos) <= Companion.SEEK_DISTANCE) {
            changeVelocity(Utils.getDirectionalVector(pos, player.pos))
        } else {
            changeVelocity(Vector(0.0, 0.0))
        }

        // Update position
        pos.X += velocity.X
        pos.Y += velocity.Y

        if (TileType.values()[mapLayout.layout[(pos.Y / MapLayout.TILE_HEIGHT_PIXELS).toInt()][(pos.X / MapLayout.TILE_WIDTH_PIXELS).toInt()]] == TileType.WATER_TILE) {
            pos.X -= velocity.X
            pos.Y -= velocity.Y
        }
        if (collideCheck()) {
            pos.X -= 3 * velocity.X
            pos.Y -= 3 * velocity.Y
        }

        hitbox.updateCoord(displayCoordinates)

        // Animator update
        animator.changeDirection(velocity)
        animator.update()

        // Update direction
        if (velocity.X != 0.0 || velocity.Y != 0.0) {
            // Normalize velocity to get direction (unit vector of velocity)
            val distance: Double =
                Utils.getDistanceBetweenPoints(Point(0.0, 0.0), Point(velocity.X, velocity.Y))
            direction.X = velocity.X / distance
            direction.Y = velocity.Y / distance
        }
    }

    private fun collideCheck(): Boolean {
        for (obj in gameObjects) {
            if (obj != this && obj.hitbox.isCollide(hitbox))
                return true
        }
        return false
    }

    override fun hit(bullet: Bullet) {
        HP -= 10
        if (HP <= 0) {
            die()
        }
    }

    fun die() {
        toDestroy = true
    }
}