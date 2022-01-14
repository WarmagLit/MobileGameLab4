package com.tsu.mobilegamelab4.game

import android.graphics.Canvas

interface IDrawable {
    fun draw(canvas: Canvas, display: GameDisplay? = null)
}