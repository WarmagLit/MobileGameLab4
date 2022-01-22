package com.tsu.mobilegamelab4.game.surfaceview.map.secondlocation

import android.graphics.Bitmap
import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.surfaceview.graphics.SecondLocationSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IDrawable

class SecondLocationMap(spriteSheet: SecondLocationSpriteSheet) : IDrawable {

    companion object {
        const val CELL_WIDTH_PIXELS = 200
        const val CELL_HEIGHT_PIXELS = 200
        const val NUMBER_OF_ROW_CELLS = 54
        const val NUMBER_OF_COLUMN_CELLS = 46
    }

    val collisionLayout = SecondLocationCollisionLayout()
    private val lowerLevel: Bitmap = Bitmap.createScaledBitmap(
        spriteSheet.lowerLevelBitmap,
        NUMBER_OF_COLUMN_CELLS * CELL_WIDTH_PIXELS,
        NUMBER_OF_ROW_CELLS * CELL_HEIGHT_PIXELS,
        false
    )

    private val upperLevel: Bitmap = Bitmap.createScaledBitmap(
        spriteSheet.upperLevelBitmap,
        NUMBER_OF_COLUMN_CELLS * CELL_WIDTH_PIXELS,
        NUMBER_OF_ROW_CELLS * CELL_HEIGHT_PIXELS,
        false
    )

    var drawLowerLevel = true

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            if (drawLowerLevel) {
                canvas.drawBitmap(
                    lowerLevel,
                    it.gameRect,
                    it.displayRect,
                    null
                )
            } else {
                canvas.drawBitmap(
                    upperLevel,
                    it.gameRect,
                    it.displayRect,
                    null
                )
            }
            drawLowerLevel = !drawLowerLevel
        }
    }
}