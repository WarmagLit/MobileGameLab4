package com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Wizard

import android.graphics.Canvas
import android.graphics.Point
import com.tsu.mobilegamelab4.game.surfaceview.Vector
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Direction
import com.tsu.mobilegamelab4.game.surfaceview.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.Sprite

class WizardAnimator(spriteSheet: EnemySpriteSheet) {

    private var currentDirection: Direction = Direction.STAY

    private var currentSpriteList: List<Sprite>
    private var currentSpriteIndex = 0
    private var updateCounter = 0
    private var newSpriteUpdateNum = 10

    val spriteStay: List<Sprite>
    private val spriteRight: List<Sprite>
    private val spriteLeft: List<Sprite>

    companion object {
        val size = Point(150, 150)
    }

    init {
        spriteRight = listOf(
            spriteSheet.getSpriteByIndexWithSize(0, 8, size),
            spriteSheet.getSpriteByIndexWithSize(0, 9, size),
            spriteSheet.getSpriteByIndexWithSize(0, 10, size),
            spriteSheet.getSpriteByIndexWithSize(0, 11, size)
        )
        spriteLeft = listOf(
            spriteSheet.getSpriteByIndexWithSize(0, 12, size),
            spriteSheet.getSpriteByIndexWithSize(0, 13, size),
            spriteSheet.getSpriteByIndexWithSize(0, 14, size),
            spriteSheet.getSpriteByIndexWithSize(0, 15, size)
        )

        spriteStay = listOf(
            spriteSheet.getSpriteByIndexWithSize(0, 9, size),
            spriteSheet.getSpriteByIndexWithSize(0, 10, size),
            spriteSheet.getSpriteByIndexWithSize(0, 9, size),
            spriteSheet.getSpriteByIndexWithSize(0, 10, size)
        )

        currentSpriteList = spriteStay
    }

    fun draw(canvas: Canvas, X: Int, Y: Int) {
        currentSpriteList[currentSpriteIndex].draw(canvas, X, Y)
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
}