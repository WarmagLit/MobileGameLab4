package com.tsu.mobilegamelab4.game.map.firstlocation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IDrawable
import com.tsu.mobilegamelab4.game.map.Tile

class FirstLocationTilemap(spriteSheet: FirstLocationSpriteSheet) : IDrawable {
    
    private val mapLayout = FirstLocationMapLayout()
    private var mapBitmap: Bitmap
    private val lowerLevel: Bitmap
    private val upperLevel: Bitmap
    val tilemap = Array(FirstLocationMapLayout.NUMBER_OF_ROW_TILES) {
        Array(FirstLocationMapLayout.NUMBER_OF_COLUMN_TILES) {
            Tile.getTile(
                0,
                spriteSheet,
                Rect(0, 0, 0, 0)
            )
        }
    }

    init {
        val layout = mapLayout.layout
        for (iRow in 0 until FirstLocationMapLayout.NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until FirstLocationMapLayout.NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol] = Tile.getTile(
                    layout[iRow][iCol].toString().toFloat().toInt(),
                    spriteSheet,
                    rectByIndex(iRow, iCol)
                )
            }
        }

        val config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
            FirstLocationMapLayout.NUMBER_OF_COLUMN_TILES * FirstLocationMapLayout.TILE_WIDTH_PIXELS,
            FirstLocationMapLayout.NUMBER_OF_ROW_TILES * FirstLocationMapLayout.TILE_HEIGHT_PIXELS,
            config
        )

        mapBitmap = Bitmap.createScaledBitmap(
            spriteSheet.lowerLevelBitmap,
            FirstLocationMapLayout.NUMBER_OF_COLUMN_TILES * FirstLocationMapLayout.TILE_WIDTH_PIXELS,
            FirstLocationMapLayout.NUMBER_OF_ROW_TILES * FirstLocationMapLayout.TILE_HEIGHT_PIXELS,
        false
        )

        lowerLevel = Bitmap.createScaledBitmap(
            spriteSheet.lowerLevelBitmap,
            FirstLocationMapLayout.NUMBER_OF_COLUMN_TILES * FirstLocationMapLayout.TILE_WIDTH_PIXELS,
            FirstLocationMapLayout.NUMBER_OF_ROW_TILES * FirstLocationMapLayout.TILE_HEIGHT_PIXELS,
            false
        )

        upperLevel = Bitmap.createScaledBitmap(
            spriteSheet.upperLevelBitmap,
            FirstLocationMapLayout.NUMBER_OF_COLUMN_TILES * FirstLocationMapLayout.TILE_WIDTH_PIXELS,
            FirstLocationMapLayout.NUMBER_OF_ROW_TILES * FirstLocationMapLayout.TILE_HEIGHT_PIXELS,
            false
        )

//        val mapCanvas = Canvas(mapBitmap)
//
//        for (iRow in 0 until FirstLocationMapLayout.NUMBER_OF_ROW_TILES) {
//            for (iCol in 0 until FirstLocationMapLayout.NUMBER_OF_COLUMN_TILES) {
//                tilemap[iRow][iCol].draw(mapCanvas)
//            }
//        }

        Log.d("t","T")
    }

    private fun rectByIndex(iRow: Int, iCol: Int): Rect {
        return Rect(
            iCol * FirstLocationMapLayout.TILE_WIDTH_PIXELS,
            iRow * FirstLocationMapLayout.TILE_HEIGHT_PIXELS,
            (iCol + 1) * FirstLocationMapLayout.TILE_WIDTH_PIXELS,
            (iRow + 1) * FirstLocationMapLayout.TILE_HEIGHT_PIXELS
        )
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            canvas.drawBitmap(
                mapBitmap,
                it.gameRect,
                it.displayRect,
                null
            )
        }
    }

    fun drawLower(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            canvas.drawBitmap(
                lowerLevel,
                it.gameRect,
                it.displayRect,
                null
            )
        }
    }

    fun drawUpper(canvas: Canvas, display: GameDisplay?) {
        display?.let {
            canvas.drawBitmap(
                upperLevel,
                it.gameRect,
                it.displayRect,
                null
            )
        }
    }
}