package com.tsu.mobilegamelab4.game.controls

import android.graphics.Color
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Utils
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player

class SwipeStick(
    player: Player,
    outerCircleCenterPosition: Point,
    outerCircleRadius: Int,
    innerCircleRadius: Int
) : Stick(
    player,
    outerCircleCenterPosition,
    outerCircleRadius,
    innerCircleRadius
), IDrawableUpdatable {

    fun action() {
        val direction = Vector(1.0, 0.0)
        // Update direction
        if (actuator.X != 0.0 || actuator.Y != 0.0) {
            // Normalize velocity to get direction (unit vector of velocity)
            val distance: Double =
                Utils.getDistanceBetweenPoints(Point(0.0, 0.0), Point(actuator.X, actuator.Y))
            direction.X = actuator.X / distance
            direction.Y = actuator.Y / distance
        }
        player.attack(direction)
    }

    init {
        // Paint of circles
        innerCirclePaint.color = Color.parseColor("#e20cc2")
    }
}
