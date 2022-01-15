package com.tsu.mobilegamelab4.game

import android.graphics.Rect

class GameDisplay(
    private val widthPixels: Int,
    private val heightPixels: Int,
    private val centerObject: GameObject
) {

    val displayRect = Rect(0, 0, widthPixels, heightPixels)
    private val displayCenter = Point(widthPixels / 2.0, heightPixels / 2.0)
    private var gameToDisplayCoordinatesOffset = Point(0.0, 0.0)
    private var gameCenter = Point(0.0, 0.0)
    val gameRect: Rect
        get() = Rect(
            (gameCenter.X - widthPixels / 2).toInt(),
            (gameCenter.Y - heightPixels / 2).toInt(),
            (gameCenter.X + widthPixels / 2).toInt(),
            (gameCenter.Y + heightPixels / 2).toInt()
        )

    fun update() {
        gameCenter = centerObject.pos
        gameToDisplayCoordinatesOffset.X = displayCenter.X - gameCenter.X
        gameToDisplayCoordinatesOffset.Y = displayCenter.Y - gameCenter.Y
    }

    fun gameToDisplayCoordinates(coordinates: Point): Point {
        return Point(
            coordinates.X + gameToDisplayCoordinatesOffset.X,
            coordinates.Y + gameToDisplayCoordinatesOffset.Y
        )
    }

    init {
        update()
    }
}