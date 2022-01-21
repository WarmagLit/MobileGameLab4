package com.tsu.mobilegamelab4.game.map.firstlocation

import android.graphics.Bitmap
import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IDrawable

class FirstLocationMap(spriteSheet: FirstLocationSpriteSheet) : IDrawable {

    companion object {
        const val CELL_WIDTH_PIXELS = 200
        const val CELL_HEIGHT_PIXELS = 200
        const val NUMBER_OF_ROW_CELLS = 42
        const val NUMBER_OF_COLUMN_CELLS = 49
    }

    val collisionLayout = FirstLocationCollisionLayout()
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