package com.tsu.mobilegamelab4.game.player

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Utils.cosinus
import com.tsu.mobilegamelab4.game.Utils.sinus
import com.tsu.mobilegamelab4.game.graphics.Sprite
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

enum class Direction {
    NORTH, SOUTH, WEST, EAST,
    NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST
}

class Animator(spriteSheet: SpriteSheet) {

    private var currentSpriteList: List<Sprite>
    private var currentSpriteIndex = 0
    private var updateCounter = 0
    private var newSpriteUpdateNum = 10

    private val spriteRight: List<Sprite>
    private val spriteUpRight: List<Sprite>
    private val spriteUp: List<Sprite>
    private val spriteUpLeft: List<Sprite>
    private val spriteLeft: List<Sprite>
    private val spriteDownLeft: List<Sprite>
    private val spriteDown: List<Sprite>
    private val spriteDownRight: List<Sprite>


    init {
        spriteRight = listOf(
            spriteSheet.getSpriteByIndex(0, 0),
            spriteSheet.getSpriteByIndex(0, 1),
            spriteSheet.getSpriteByIndex(0, 2),
            spriteSheet.getSpriteByIndex(0, 3)
        )
        spriteUpRight = listOf(
            spriteSheet.getSpriteByIndex(1, 0),
            spriteSheet.getSpriteByIndex(1, 1),
            spriteSheet.getSpriteByIndex(1, 2),
            spriteSheet.getSpriteByIndex(1, 3)
        )
        spriteUp = listOf(
            spriteSheet.getSpriteByIndex(2, 0),
            spriteSheet.getSpriteByIndex(2, 1),
            spriteSheet.getSpriteByIndex(2, 2),
            spriteSheet.getSpriteByIndex(2, 3)
        )
        spriteUpLeft = listOf(
            spriteSheet.getSpriteByIndex(3, 0),
            spriteSheet.getSpriteByIndex(3, 1),
            spriteSheet.getSpriteByIndex(3, 2),
            spriteSheet.getSpriteByIndex(3, 3)
        )
        spriteLeft = listOf(
            spriteSheet.getSpriteByIndex(4, 0),
            spriteSheet.getSpriteByIndex(4, 1),
            spriteSheet.getSpriteByIndex(4, 2),
            spriteSheet.getSpriteByIndex(4, 3)
        )
        spriteDownLeft = listOf(
            spriteSheet.getSpriteByIndex(5, 0),
            spriteSheet.getSpriteByIndex(5, 1),
            spriteSheet.getSpriteByIndex(5, 2),
            spriteSheet.getSpriteByIndex(5, 3)
        )
        spriteDown = listOf(
            spriteSheet.getSpriteByIndex(6, 0),
            spriteSheet.getSpriteByIndex(6, 1),
            spriteSheet.getSpriteByIndex(6, 2),
            spriteSheet.getSpriteByIndex(6, 3)
        )
        spriteDownRight = listOf(
            spriteSheet.getSpriteByIndex(7, 0),
            spriteSheet.getSpriteByIndex(7, 1),
            spriteSheet.getSpriteByIndex(7, 2),
            spriteSheet.getSpriteByIndex(7, 3)
        )

        currentSpriteList = spriteDown
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
            Direction.NORTH -> currentSpriteList = spriteUp
            Direction.SOUTH -> currentSpriteList = spriteDown
            Direction.EAST -> currentSpriteList = spriteRight
            Direction.WEST -> currentSpriteList = spriteLeft
            Direction.NORTH_EAST -> currentSpriteList = spriteUpRight
            Direction.SOUTH_WEST -> currentSpriteList = spriteDownLeft
            Direction.SOUTH_EAST -> currentSpriteList = spriteDownRight
            Direction.NORTH_WEST -> currentSpriteList = spriteUpLeft
        }
    }

    private fun getDirection(velocity: Vector): Direction {
        val directionVector = Vector(
            velocity.X / velocity.length(),
            velocity.Y / velocity.length()
        )

        val sqrtOfTwoByTwo = sqrt(2.0)/2

        if (directionVector.X > cosinus(22.5)) {
            return Direction.EAST
        }
        if (directionVector.X < -cosinus(22.5)) {
            return Direction.WEST
        }
        if (directionVector.Y > sinus(67.5)) {
            return Direction.SOUTH
        }
        if (directionVector.Y < -sinus(67.5)) {
            return Direction.NORTH
        }

        if (directionVector.X > cosinus(67.5) && directionVector.Y > -sinus(22.5)) {
            return Direction.SOUTH_EAST
        }
        if (directionVector.X < -cosinus(67.5) && directionVector.Y > -sinus(22.5)) {
            return Direction.SOUTH_WEST
        }
        if (directionVector.X > cosinus(67.5) && directionVector.Y < sinus(22.5)) {
            return Direction.NORTH_EAST
        }
        if (directionVector.X < -cosinus(67.5) && directionVector.Y < sinus(22.5)) {
            return Direction.NORTH_WEST
        }

        return Direction.SOUTH
    }


    fun attackAnimation() {

    }
}