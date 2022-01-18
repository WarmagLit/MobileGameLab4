package com.tsu.mobilegamelab4.game.graphics

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap

class Sprite(private val spriteSheet: SpriteSheet, private val rect: Rect) {

    var size = Point(FirstLocationMap.CELL_WIDTH_PIXELS, FirstLocationMap.CELL_HEIGHT_PIXELS)//Point(250,250)

    fun draw(canvas: Canvas, x: Int, y: Int) {
        canvas.drawBitmap(
            spriteSheet.bitmap,
            rect,
            Rect(x, y, x + size.x, y + size.y),
            null
        )
    }
}
