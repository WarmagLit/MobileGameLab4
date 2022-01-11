package com.tsu.mobilegamelab4.game

import com.tsu.mobilegamelab4.game.Utils.getDistanceBetweenPoints

class Vector(X: Double, Y: Double) : Point(X, Y) {
    fun length(): Double {
        return getDistanceBetweenPoints(Point(0.0, 0.0), this)
    }
}