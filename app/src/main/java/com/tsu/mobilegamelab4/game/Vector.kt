package com.tsu.mobilegamelab4.game

class Vector(X: Double, Y: Double): Point(X, Y) {
    fun length(): Double {
        return Utils.getDistanceBetweenPoints(Point(0.0, 0.0), this)
    }
}