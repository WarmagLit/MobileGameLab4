package com.tsu.mobilegamelab4.game.gameobjects.entity.player

import android.graphics.Color
import android.graphics.Point
import com.tsu.mobilegamelab4.game.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.gameobjects.entity.HealthBar

class PlayerHealthBar(maxHealth: Int, owner: Entity): HealthBar(maxHealth, owner) {
    init {
        healthPaint.color = Color.RED
        healthPaint.strokeWidth = 65f

        healthLineLength = 500
        offset = Point(700, 450)
    }
}