package com.tsu.mobilegamelab4.game


import android.graphics.Rect

class GameDisplay(
    private val widthPixels: Int,
    private val heightPixels: Int,
    centerObject: GameObject
) {
    val DISPLAY_RECT: Rect
    private val centerObject: GameObject
    private val displayCenterX: Double
    private val displayCenterY: Double
    private var gameToDisplayCoordinatesOffsetX = 0.0
    private var gameToDisplayCoordinatesOffsetY = 0.0
    private var gameCenterX = 0.0
    private var gameCenterY = 0.0
    fun update() {
        gameCenterX = centerObject.positionX
        gameCenterY = centerObject.positionY
        gameToDisplayCoordinatesOffsetX = displayCenterX - gameCenterX
        gameToDisplayCoordinatesOffsetY = displayCenterY - gameCenterY
    }

    fun gameToDisplayCoordinatesX(x: Double): Double {
        return x + gameToDisplayCoordinatesOffsetX
    }

    fun gameToDisplayCoordinatesY(y: Double): Double {
        return y + gameToDisplayCoordinatesOffsetY
    }

    init {
        DISPLAY_RECT = Rect(0, 0, widthPixels, heightPixels)
        this.centerObject = centerObject
        displayCenterX = widthPixels / 2.0
        displayCenterY = heightPixels / 2.0
        update()
    }
}