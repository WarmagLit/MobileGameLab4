package com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Landmine

import android.graphics.Canvas
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.HealthBar
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Enemy
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Masker.MaskerAnimator
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.Bullet
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationCollisionLayout
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap
import java.util.logging.Level

class Landmine (
    pos: Point,
    spriteSheet: EnemySpriteSheet,
    player: Player,
    private val collisionLayout: FirstLocationCollisionLayout,
    private val gameObjects: MutableList<GameObject>
) :
    Enemy(pos, spriteSheet, collisionLayout, player, gameObjects), ICollideable {

    private var act = Point(0.0, 0.0)

    private val healthBar: HealthBar = HealthBar(HP, this)

    private val animator = LandmineAnimator(spriteSheet)

    private var cooldownCounter = -1

    init {
        hitbox = Hitbox(this, 155, 180)
    }

    companion object {
        private const val SPEED_PIXELS_PER_SECOND = 0.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        private const val SEEK_DISTANCE = 400.0
        private const val ATTACK_DISTANCE = 400.0
        private const val ATTACK_COOLDOWN = 60
        private const val MAX_DAMAGE = 100.0
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            //hitbox.draw(canvas, display)
            animator.draw(
                canvas,
                displayCoordinates.X.toInt() - animator.currentSpriteList.first().size.x / 2,
                displayCoordinates.Y.toInt() - animator.currentSpriteList.first().size.y / 2
            )
            healthBar.draw(canvas, display)
        }
    }

    override fun changeVelocity(actuator: Vector) {
        // Update velocity based on actuator of joystick
        // It's static enemy
        /*
        velocity.X = actuator.X * MAX_SPEED
        velocity.Y = actuator.Y * MAX_SPEED

        act.X = actuator.X
        act.Y = actuator.Y
        */
    }

    fun attack(player: Player) {
        if (cooldownCounter == 0) {
            animator.explode()
            val distanceToPlayer = Utils.getDistanceBetweenPoints(player.pos, pos)
            val damag = (MAX_DAMAGE/(distanceToPlayer/FirstLocationMap.CELL_WIDTH_PIXELS)).toInt()
            Log.d("TAG", damag.toString())
            cooldownCounter = -1
            player.receiveDamage(damag)
        }
    }

    override fun update() {
        if (Utils.getDistanceBetweenPoints(player.pos, pos) <= ATTACK_DISTANCE && cooldownCounter == -1) {
            cooldownCounter = 40
        }

        if (cooldownCounter > 0) cooldownCounter--
        if (cooldownCounter == 0) {
            attack(player)
        }

        // Landmine exploded
        if (animator.isStopped) {
            toDestroy = true
        }

        if (pos.X >= 0 && pos.Y >= 0
            && collisionLayout.layout[(pos.Y / FirstLocationMap.CELL_HEIGHT_PIXELS).toInt()][(pos.X / FirstLocationMap.CELL_WIDTH_PIXELS).toInt()] == 1
        ) {
            pos.X -= velocity.X
            pos.Y -= velocity.Y
        }


        val objRect = collideCheck()
        objRect?.let {
            val pushVector = Utils.normalizeVector(
                Vector(
                    ((hitbox.rect.centerX() - it.centerX())).toDouble(),
                    ((hitbox.rect.centerY() - it.centerY())).toDouble()
                )
            )
            pos.X += pushVector.X - velocity.X * 2
            pos.Y += pushVector.Y - velocity.Y * 2
        }

        hitbox.updateCoordinatesWithCentering(displayCoordinates)

        // Animator update
        animator.update()
    }

    override fun hit(bullet: Bullet) {
        healthBar.getDamage(20)
    }

}