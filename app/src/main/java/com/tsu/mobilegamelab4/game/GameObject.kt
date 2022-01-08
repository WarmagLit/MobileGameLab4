package com.tsu.mobilegamelab4.game

import android.graphics.Canvas

abstract class GameObject(var positionX: Double, var positionY: Double) {

    protected var velocityX = 0.0
    protected var velocityY = 0.0
    var directionX = 1.0
    var directionY = 0.0

    abstract fun draw(canvas: Canvas?, gameDisplay: GameDisplay?)
    abstract fun update()
}
