package com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Landmine

import android.graphics.Canvas
import android.graphics.Point
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Direction
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.graphics.Sprite

class LandmineAnimator (spriteSheet: EnemySpriteSheet) {

    var currentSpriteList: List<Sprite>
    private var currentSpriteIndex = 0
    private var updateCounter = 0
    private var newSpriteUpdateNum = 10

    val spriteStay: List<Sprite>
    val spriteExplode: List<Sprite>

    var isStopped = false

    init {

        spriteStay = listOf(
            spriteSheet.getSpriteByIndex(1, 8),
            spriteSheet.getSpriteByIndex(1, 9),
            spriteSheet.getSpriteByIndex(1, 10),
            spriteSheet.getSpriteByIndex(1, 11)
        )

        spriteExplode = listOf(
            spriteSheet.getSpriteByIndexWithSize(2, 4, Point(300,300)),
            spriteSheet.getSpriteByIndexWithSize(2, 5, Point(300,300)),
            spriteSheet.getSpriteByIndexWithSize(2, 6, Point(300,300)),
            spriteSheet.getSpriteByIndexWithSize(3, 4, Point(300,300)),
            spriteSheet.getSpriteByIndexWithSize(3, 5, Point(300,300)),
            spriteSheet.getSpriteByIndexWithSize(3, 6, Point(300,300)),
            spriteSheet.getSpriteByIndexWithSize(99, 99, Point(300,300))
        )

        currentSpriteList = spriteStay
    }

    fun draw(canvas: Canvas, X: Int, Y: Int) {
        currentSpriteList[currentSpriteIndex].draw(canvas, X, Y)
    }

    fun update() {
        updateCounter++

        if (updateCounter >= newSpriteUpdateNum && !isStopped) {
            currentSpriteIndex++
            updateCounter = 0
            if (currentSpriteList == spriteExplode && currentSpriteIndex == currentSpriteList.size - 1) {
                isStopped = true
            }
            if (currentSpriteIndex >= currentSpriteList.size) {
                currentSpriteIndex = 0
            }
        }
    }

    fun explode() {
        currentSpriteList = spriteExplode
    }
}