package com.tsu.mobilegamelab4.game.surfaceview.interfaces

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay

interface IDrawable {
    fun draw(canvas: Canvas, display: GameDisplay? = null)
}