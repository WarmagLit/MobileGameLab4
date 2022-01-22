package com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Wizard

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.surfaceview.*
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.HealthBar
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Enemy
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns.Bullet
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns.Gun
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns.magic_gun.MagicGun
import com.tsu.mobilegamelab4.game.surfaceview.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.surfaceview.map.CollisionLayout
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationCollisionLayout
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap

class Wizard (
    pos: Point,
    spriteSheet: EnemySpriteSheet,
    player: Player,
    private val collisionLayout: CollisionLayout,
    private val gameObjects: MutableList<GameObject>
) :
    Enemy(pos, spriteSheet, collisionLayout, player, gameObjects), ICollideable {

    private var act = Point(0.0, 0.0)

    private val healthBar: HealthBar = HealthBar(HP, this)

    private val animator = WizardAnimator(spriteSheet)

    private var cooldownCounter = 0
    private val gun: Gun

    init {
        hitbox = Hitbox(this, 140, 140)
        gun = MagicGun(this, spriteSheet.getSpriteByIndex(3, 0), gameObjects)
    }

    companion object {
        private const val SPEED_PIXELS_PER_SECOND = 200.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        private const val SEEK_DISTANCE = 700.0
        private const val ATTACK_DISTANCE = 600.0
        private const val ATTACK_COOLDOWN = 60
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            //hitbox.draw(canvas, display)
            gun.draw(canvas, display)
            animator.draw(
                canvas,
                displayCoordinates.X.toInt() - animator.spriteStay.first().size.x / 2,
                displayCoordinates.Y.toInt() - animator.spriteStay.first().size.y / 2
            )
            healthBar.draw(canvas, display)
        }
    }

    override fun changeVelocity(actuator: Vector) {
        // Update velocity based on actuator of joystick
        velocity.X = actuator.X * MAX_SPEED
        velocity.Y = actuator.Y * MAX_SPEED

        act.X = actuator.X
        act.Y = actuator.Y
    }

    fun attack(player: Player) {
        if (cooldownCounter == 0) {
            cooldownCounter = ATTACK_COOLDOWN
            //player.changeVelocity(Vector(direction.X * 2, direction.Y * 2))
            //animator.attackAnimation()
            //player.receiveStrike()
            val playerDirection = Utils.getDirectionalVector(pos, player.pos)
            gun.fire(Vector(playerDirection.X, playerDirection.Y))
        }
    }

    override fun update() {
        if (Utils.getDistanceBetweenPoints(player.pos, pos) <= SEEK_DISTANCE) {
            changeVelocity(Utils.getDirectionalVector(pos, player.pos))
        } else {
            changeVelocity(Vector(0.0, 0.0))
        }

        if (Utils.getDistanceBetweenPoints(player.pos, pos) <= ATTACK_DISTANCE) {
            attack(player)
        }

        if (cooldownCounter > 0) cooldownCounter--

        // Update position
        pos.X += velocity.X
        pos.Y += velocity.Y

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
        gun.update()

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

    override fun hit(bullet: Bullet) {
        healthBar.getDamage(20)
    }

}