package com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Boss

import android.graphics.Canvas
import android.graphics.Point
import com.tsu.mobilegamelab4.game.surfaceview.Vector
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Direction
import com.tsu.mobilegamelab4.game.surfaceview.graphics.BossSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.Sprite

class BossAnimator(spriteSheet: BossSpriteSheet) {


    private var currentDirection: Direction = Direction.STAY
    private var previousDirection: Direction = Direction.STAY

    private var currentSpriteList: List<Sprite>
    private var currentSpriteIndex = 0
    private var updateCounter = 0
    private var newSpriteUpdateNum = 10

    val spriteStay: List<Sprite>

    private val spriteRight: List<Sprite>
    private val spriteLeft: List<Sprite>
    private val spriteAttackRight: List<Sprite>
    private val spriteAttackLeft: List<Sprite>

    companion object {
        private val spriteSize = Point(2720, 880)
        private val leftOffset = -2050
    }

    init {

        spriteRight = listOf(
            spriteSheet.getSpriteByIndexWithSize(4, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(5, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(6, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(7, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(8, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(9, 0, spriteSize)
        )

        spriteLeft = listOf(
            spriteSheet.getSpriteByIndexWithSize(17, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(18, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(19, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(20, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(21, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(22, 0, spriteSize)
        )

        spriteStay = listOf(
            spriteSheet.getSpriteByIndexWithSize(0, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(1, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(2, 0, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(3, 0, spriteSize)
        )

        spriteAttackRight = listOf(
            spriteSheet.getSpriteByIndexWithSize(0, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(1, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(2, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(3, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(4, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(5, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(6, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(7, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(8, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(9, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(10, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(11, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(12, 3, spriteSize)
        )
        spriteAttackLeft = listOf(
            spriteSheet.getSpriteByIndexWithSize(13, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(14, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(15, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(16, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(17, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(18, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(19, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(20, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(21, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(22, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(23, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(24, 3, spriteSize),
            spriteSheet.getSpriteByIndexWithSize(25, 3, spriteSize)
        )

        currentSpriteList = spriteStay
    }

    fun draw(canvas: Canvas, X: Int, Y: Int) {
        var Xcoord = X
        if (currentSpriteList == spriteLeft || currentSpriteList == spriteAttackLeft) {
            Xcoord += leftOffset
        }
        currentSpriteList[currentSpriteIndex].draw(canvas, Xcoord, Y)
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

    }

    fun changeDirection(velocity: Vector) {
        currentDirection = getDirection(velocity)

        if (previousDirection == currentDirection) return
        previousDirection = currentDirection
        when (currentDirection) {
            Direction.STAY -> currentSpriteList = spriteStay
            Direction.EAST -> currentSpriteList = spriteRight
            Direction.WEST -> currentSpriteList = spriteLeft
            else -> {
                currentSpriteList = spriteStay
            }
        }
        if (currentSpriteIndex >= currentSpriteList.size) {
            currentSpriteIndex = currentSpriteList.size - 1
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
            currentSpriteList = spriteAttackRight
            currentSpriteIndex = 0
        } else {
            currentSpriteList = spriteAttackLeft
            currentSpriteIndex = 0
        }
    }
}