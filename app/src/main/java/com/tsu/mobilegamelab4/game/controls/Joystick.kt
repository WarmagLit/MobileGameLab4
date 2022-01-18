package com.tsu.mobilegamelab4.game.controls

import android.graphics.Color
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.entity.player.Player

class Joystick(
    private val player: Player,
    outerCircleCenterPosition: Point,
    outerCircleRadius: Int,
    innerCircleRadius: Int
) : Stick(
    player,
    outerCircleCenterPosition,
    outerCircleRadius,
    innerCircleRadius
), IDrawableUpdatable {

    override fun update() {
        super.update()
        player.changeVelocity(actuator)
    }

    init {
        // Paint of circle
        innerCirclePaint.color = Color.BLUE
    }
}
