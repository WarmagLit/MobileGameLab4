package com.tsu.mobilegamelab4.game.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.SpriteSheet

class Sprite(private val spriteSheet: SpriteSheet, private val rect: Rect) {
    fun draw(canvas: Canvas, x: Int, y: Int) {
        canvas.drawBitmap(
            spriteSheet.bitmap,
            rect,
            Rect(x, y, x + width, y + height),
            null
        )
    }


    private val width: Int = 300
    private val height: Int = 300
}
