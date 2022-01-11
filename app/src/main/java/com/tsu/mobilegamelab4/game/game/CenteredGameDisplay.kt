package com.tsu.mobilegamelab4.game.game

import android.graphics.Rect
import com.tsu.mobilegamelab4.game.Point

class CenteredGameDisplay(
    private val widthPixels: Int,
    private val heightPixels: Int,
    centerObject: GameObject
) {
    val DISPLAY_RECT: Rect
    private val centerObject: GameObject
    private val displayCenter: Point
    private var gameToDisplayCoordinatesOffset = Point(0.0, 0.0)
    private var gameCenter = Point(0.0, 0.0)

    fun update() {
        gameCenter = centerObject.pos
        gameToDisplayCoordinatesOffset.X = displayCenter.X - gameCenter.X
        gameToDisplayCoordinatesOffset.Y = displayCenter.Y - gameCenter.Y
    }

    fun gameToDisplayCoordinatesX(x: Double): Double {
        return x + gameToDisplayCoordinatesOffset.X
    }

    fun gameToDisplayCoordinatesY(y: Double): Double {
        return y + gameToDisplayCoordinatesOffset.Y
    }

    init {
        DISPLAY_RECT = Rect(0, 0, widthPixels, heightPixels)
        this.centerObject = centerObject
        displayCenter = Point(widthPixels / 2.0, heightPixels / 2.0)
        update()
    }
}