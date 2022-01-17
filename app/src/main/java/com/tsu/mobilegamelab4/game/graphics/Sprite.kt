package com.tsu.mobilegamelab4.game.graphics

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.map.MapLayout
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMapLayout

class Sprite(private val spriteSheet: SpriteSheet, private val rect: Rect) {

    var size = Point(FirstLocationMapLayout.TILE_WIDTH_PIXELS, FirstLocationMapLayout.TILE_HEIGHT_PIXELS)//Point(250,250)

    fun draw(canvas: Canvas, x: Int, y: Int) {
        canvas.drawBitmap(
            spriteSheet.bitmap,
            rect,
            Rect(x, y, x + size.x, y + size.y),
            null
        )
    }

    val width: Int = 100
    val height: Int = 100
}
