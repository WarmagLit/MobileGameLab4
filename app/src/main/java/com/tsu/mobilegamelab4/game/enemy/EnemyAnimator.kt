package com.tsu.mobilegamelab4.game.enemy

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.SpriteSheet
import com.tsu.mobilegamelab4.game.Utils
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.graphics.Sprite
import com.tsu.mobilegamelab4.game.player.Direction

class EnemyAnimator(spriteSheet: EnemySpriteSheet) {

    private var currentSpriteList: List<EnemySprite>
    private var currentSpriteIndex = 0
    private var updateCounter = 0
    private var newSpriteUpdateNum = 10

    private val spriteStay: List<EnemySprite>
    private val spriteRight: List<EnemySprite>
    private val spriteLeft: List<EnemySprite>



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

        when (getDirection(velocity)) {
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

        if (velocity.X == 0.0 && velocity.Y==0.0) {
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

    }
}