package com.tsu.mobilegamelab4.game

open class Point(var X: Double, var Y: Double)

fun Point.clone(): Point {
    val p = Point(X, Y)
    return p
}
