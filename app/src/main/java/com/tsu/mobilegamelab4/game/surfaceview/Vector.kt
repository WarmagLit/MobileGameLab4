package com.tsu.mobilegamelab4.game.surfaceview

import com.tsu.mobilegamelab4.game.surfaceview.Utils.getDistanceBetweenPoints

class Vector(X: Double, Y: Double) : Point(X, Y) {

    fun length(): Double {
        return getDistanceBetweenPoints(Point(0.0, 0.0), this)
    }
}
fun Vector.clone(): Vector {
    val v = Vector(X, Y)
    return v
}
fun Vector.multiply(coef: Double): Vector {
    val v = Vector(X*coef, Y*coef)
    return v
}
fun Vector.rotateByAngle(angle: Double): Vector {

    val x_rotated =   (X * Utils.cosinus(angle)) - (Y * Utils.sinus(angle))
    val y_rotated =  Y * Utils.cosinus(angle) + (X * Utils.sinus(angle))
    return Vector(x_rotated, y_rotated)
}