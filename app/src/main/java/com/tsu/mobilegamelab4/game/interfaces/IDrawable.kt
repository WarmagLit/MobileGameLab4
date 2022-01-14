package com.tsu.mobilegamelab4.game.interfaces

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.GameDisplay

interface IDrawable {
    fun draw(canvas: Canvas, display: GameDisplay? = null)
}