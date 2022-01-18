package com.tsu.mobilegamelab4.game.gameobjects.entity.enemy

import android.graphics.Canvas
import android.graphics.Point
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.graphics.Sprite
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Direction

class MaskerAnimator(spriteSheet: EnemySpriteSheet) {

    private var currentDirection: Direction = Direction.STAY

    private var currentSpriteList: List<Sprite>
    private var currentSpriteIndex = 0
    private var updateCounter = 0
    private var newSpriteUpdateNum = 10

    private var additionalSpriteList: List<Sprite>? = null
    private var additionalSpriteIndex = 0
    private var additionalUpdateCounter = 0
    private var additionalSpriteUpdateNum = 5
    private var currentAdditionalSpriteOffset =
        Point(additionalSpriteOffset.x, additionalSpriteOffset.y)

    val spriteStay: List<Sprite>
    private val spriteRight: List<Sprite>
    private val spriteLeft: List<Sprite>

    private val spriteAttackRight: List<Sprite>
    private val spriteAttackLeft: List<Sprite>

    companion object {
        private val additionalSpriteOffset = Point(100, 0)
    }

    init {
        spriteRight = listOf(
            spriteSheet.getSpriteByIndex(0, 4),
            spriteSheet.getSpriteByIndex(0, 5),
            spriteSheet.getSpriteByIndex(0, 6),
            spriteSheet.getSpriteByIndex(0, 7)
        )
        spriteLeft = listOf(
            spriteSheet.getSpriteByIndex(1, 4),
            spriteSheet.getSpriteByIndex(1, 5),
            spriteSheet.getSpriteByIndex(1, 6),
            spriteSheet.getSpriteByIndex(1, 7)
        )

        spriteStay = listOf(
            spriteSheet.getSpriteByIndex(0, 0),
            spriteSheet.getSpriteByIndex(0, 1),
            spriteSheet.getSpriteByIndex(0, 2),
            spriteSheet.getSpriteByIndex(0, 3)
        )
        spriteAttackRight = listOf(
            spriteSheet.getSpriteByIndex(2, 0),
            spriteSheet.getSpriteByIndex(2, 1),
            spriteSheet.getSpriteByIndex(2, 2),
            spriteSheet.getSpriteByIndex(2, 3)
        )
        spriteAttackLeft = listOf(
            spriteSheet.getSpriteByIndex(3, 3),
            spriteSheet.getSpriteByIndex(3, 2),
            spriteSheet.getSpriteByIndex(3, 1),
            spriteSheet.getSpriteByIndex(3, 0)
        )
        currentSpriteList = spriteStay
    }

    fun draw(canvas: Canvas, X: Int, Y: Int) {
        currentSpriteList[currentSpriteIndex].draw(canvas, X, Y)
        if (additionalSpriteList != null) {
            additionalSpriteList!![additionalSpriteIndex].draw(
                canvas,
                X + currentAdditionalSpriteOffset.x,
                Y + currentAdditionalSpriteOffset.y
            )
        }
    }

    fun update() {
        updateCounter++

        if (updateCounter >= newSpriteUpdateNum) {
            currentSpriteIndex++
            updateCounter = 0
            if (currentSpriteIndex >= currentSpriteList.size) {
                currentSpriteIndex = 0
            }
        }
        // Additional sprite (attack)
        if (additionalSpriteList != null) {
            additionalUpdateCounter++
        }
        if (additionalSpriteList != null && additionalUpdateCounter >= additionalSpriteUpdateNum) {
            additionalSpriteIndex++
            additionalUpdateCounter = 0
            if (additionalSpriteIndex >= additionalSpriteList!!.size) {
                additionalSpriteIndex = 0
                additionalSpriteList = null
            }
        }
    }

    fun changeDirection(velocity: Vector) {
        currentDirection = getDirection(velocity)

        when (currentDirection) {
            Direction.STAY -> currentSpriteList = spriteStay
            Direction.EAST -> currentSpriteList = spriteRight
            Direction.WEST -> currentSpriteList = spriteLeft
            else -> {
                currentSpriteList = spriteStay
            }
        }
    }

    private fun getDirection(velocity: Vector): Direction {
        val directionVector = Vector(
            velocity.X / velocity.length(),
            velocity.Y / velocity.length()
        )

        if (velocity.X == 0.0 && velocity.Y == 0.0) {
            return Direction.STAY
        }
        if (directionVector.X > 0) {
            return Direction.EAST
        }
        if (directionVector.X < 0) {
            return Direction.WEST
        }

        return Direction.SOUTH
    }


    fun attackAnimation() {
        if (currentDirection == Direction.EAST) {
            additionalSpriteList = spriteAttackRight
            currentAdditionalSpriteOffset.x = Companion.additionalSpriteOffset.x
        } else {
            additionalSpriteList = spriteAttackLeft
            currentAdditionalSpriteOffset.x = -Companion.additionalSpriteOffset.x
        }
    }
}