package com.tsu.mobilegamelab4.game.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IDrawable

class Tilemap(spriteSheet: MapSpriteSheet) : IDrawable {
    val mapLayout = MapLayout()
    private var mapBitmap: Bitmap
    private val tilemap = Array(MapLayout.NUMBER_OF_ROW_TILES) {
        Array(MapLayout.NUMBER_OF_COLUMN_TILES) {
            Tile.getTile(
                0,
                spriteSheet,
                Rect(0, 0, 0, 0)
            )
        }
    }

    init {
        val layout = mapLayout.layout
        for (iRow in 0 until MapLayout.NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until MapLayout.NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol] = Tile.getTile(
                    layout[iRow][iCol],
                    spriteSheet,
                    rectByIndex(iRow, iCol)
                )
            }
        }

        val config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
            MapLayout.NUMBER_OF_COLUMN_TILES * MapLayout.TILE_WIDTH_PIXELS,
            MapLayout.NUMBER_OF_ROW_TILES * MapLayout.TILE_HEIGHT_PIXELS,
            config
        )

        val mapCanvas = Canvas(mapBitmap)

        for (iRow in 0 until MapLayout.NUMBER_OF_ROW_TILES) {
            for (iCol in 0 until MapLayout.NUMBER_OF_COLUMN_TILES) {
                tilemap[iRow][iCol].draw(mapCanvas)
            }
        }

        Log.d("t","T")
    }

    private fun rectByIndex(iRow: Int, iCol: Int): Rect {
        return Rect(
            iCol * MapLayout.TILE_WIDTH_PIXELS,
            iRow * MapLayout.TILE_HEIGHT_PIXELS,
            (iCol + 1) * MapLayout.TILE_WIDTH_PIXELS,
            (iRow + 1) * MapLayout.TILE_HEIGHT_PIXELS
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



}