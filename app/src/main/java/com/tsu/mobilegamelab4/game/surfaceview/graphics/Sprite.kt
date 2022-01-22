package com.tsu.mobilegamelab4.game.surfaceview.graphics

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap
import android.graphics.drawable.BitmapDrawable

import android.util.DisplayMetrics

import android.graphics.Bitmap




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

    fun drawMirrored(canvas: Canvas, x: Int, y: Int) {
        canvas.drawBitmap(
            spriteSheet.bitmap,
            rect,
            Rect(x, y, x + size.x, y + size.y),
            null
        )
    }

    fun flip(d: BitmapDrawable): BitmapDrawable? {
        val m = Matrix()
        m.preScale(-1f, 1f)
        val src = d.bitmap
        val dst = Bitmap.createBitmap(src, 0, 0, src.width, src.height, m, false)
        dst.density = DisplayMetrics.DENSITY_DEFAULT
        return BitmapDrawable(dst)
    }
}
