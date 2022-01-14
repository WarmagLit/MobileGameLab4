package com.tsu.mobilegamelab4.game.map

import android.graphics.Canvas
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.Sprite
import com.tsu.mobilegamelab4.game.interfaces.IDrawable

abstract class Tile(private val mapLocationRect: Rect) : IDrawable {

    protected lateinit var sprite: Sprite

    companion object {
        fun getTile(iTileType: Int, spriteSheet: MapSpriteSheet, mapLocationRect: Rect): Tile {
            return when(TileType.values()[iTileType]) {
                TileType.STONE_TILE -> StoneTile(spriteSheet, mapLocationRect)
                //TileType.BLUE_TILE -> BlueTile(spriteSheet, mapLocationRect)
                TileType.GRASS_TILE -> GrassTile(spriteSheet, mapLocationRect)
                TileType.WATER_TILE -> WaterTile(spriteSheet, mapLocationRect)
                else -> {WaterTile(spriteSheet, mapLocationRect)}
            }
        }
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
    }
}