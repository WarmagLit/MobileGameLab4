package com.tsu.mobilegamelab4.game.entity.enemy

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet

class MaskerSprite(private val spriteSheet: EnemySpriteSheet, private val rect: Rect) {

    var size = Point(300, 300)//Point(250,250)

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