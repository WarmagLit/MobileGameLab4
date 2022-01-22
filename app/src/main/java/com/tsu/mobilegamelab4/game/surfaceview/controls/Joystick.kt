package com.tsu.mobilegamelab4.game.surfaceview.controls

import android.graphics.Color
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player

class Joystick(
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

    override fun update() {
        super.update()
        player.changeVelocity(actuator)
    }

    init {
        // Paint of circle
        innerCirclePaint.color = Color.BLUE
    }
}
