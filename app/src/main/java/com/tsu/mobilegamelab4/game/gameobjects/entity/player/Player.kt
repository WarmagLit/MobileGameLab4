package com.tsu.mobilegamelab4.game.gameobjects.entity.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Utils.getDistanceBetweenPoints
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.Bullet
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.Gun
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.ICollideable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationCollisionLayout
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap

class Player(
    pos: Point,
    spriteSheet: HeroSpriteSheet,
    private val collisionLayout: FirstLocationCollisionLayout,
    private val gameObjects: MutableList<GameObject>
) :
    Entity(pos, collisionLayout, gameObjects),
    ICollideable {


    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    private var actX = 0.0
    private var actY = 0.0

    private val healthBar: PlayerHealthBar

    private val textPaint = Paint()

    private val animator = HeroAnimator(spriteSheet)

    private val gun: Gun

    init {
        // Init health bar
        healthBar = PlayerHealthBar(HP, this)

        textPaint.color = Color.CYAN
        textPaint.textSize = 50f

        hitbox = Hitbox(this, 150, 200)

        gun = Gun(this, Utils.displayCenter, spriteSheet.gunSprite, gameObjects)
    }


    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
           // hitbox.draw(canvas, display)
            displayCoordinates = it.gameToDisplayCoordinates(pos)
            gun.draw(canvas)
            healthBar.draw(canvas, display)
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

        //obstacle check
        if (pos.X >= 0 && pos.Y >= 0
            && collisionLayout.layout[(pos.Y / FirstLocationMap.CELL_HEIGHT_PIXELS).toInt()][(pos.X / FirstLocationMap.CELL_WIDTH_PIXELS).toInt()] == 1
        ) {
            pos.X -= velocity.X
            pos.Y -= velocity.Y
        }

        val objRect = collideCheck()
        objRect?.let {
            val pushVector = Utils.normalizeVector(Vector(
                ((hitbox.rect.centerX() - it.centerX())).toDouble(),
                ((hitbox.rect.centerY() - it.centerY())).toDouble()
            ))
            pos.X += pushVector.X - velocity.X
            pos.Y += pushVector.Y - velocity.Y
        }


        gun.update()
        hitbox.updateCoordinatesWithOffset(displayCoordinates)

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

    override fun hit(bullet: Bullet) {
        // do nothing
    }

    fun receiveStrike() {
        healthBar.getDamage(20)
    }
}