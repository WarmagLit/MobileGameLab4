package com.tsu.mobilegamelab4.game.map.thirdlocation

import android.graphics.Bitmap
import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.graphics.SecondLocationSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.ThirdLocationSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IDrawable
import com.tsu.mobilegamelab4.game.map.secondlocation.SecondLocationCollisionLayout

class ThirdLocationMap(spriteSheet: ThirdLocationSpriteSheet) : IDrawable {

    companion object {
        const val CELL_WIDTH_PIXELS = 200
        const val CELL_HEIGHT_PIXELS = 200
        const val NUMBER_OF_ROW_CELLS = 46
        const val NUMBER_OF_COLUMN_CELLS = 67
    }

    val collisionLayout = ThirdLocationCollisionLayout()
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