package com.tsu.mobilegamelab4.game.enemy

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.SpriteSheet

class EnemySprite(private val spriteSheet: EnemySpriteSheet, private val rect: Rect) {
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