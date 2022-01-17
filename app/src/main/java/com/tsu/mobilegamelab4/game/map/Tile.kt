package com.tsu.mobilegamelab4.game.map

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.Sprite
import com.tsu.mobilegamelab4.game.interfaces.IDrawable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationTileType
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationTileType.*
import com.tsu.mobilegamelab4.game.map.firstlocation.tiles.EmptyTile
import com.tsu.mobilegamelab4.game.map.firstlocation.tiles.*

abstract class Tile(private val mapLocationRect: Rect) : IDrawable {

    protected lateinit var sprite: Sprite
    var isObstacle = false

    companion object {
        fun getTile(iTileType: Int, spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect): Tile {
            if (iTileType != 0) {
                Log.d("a","sa")
            }
            return when(FirstLocationTileType.values()[iTileType]) {
                EMPTY -> EmptyTile(spriteSheet, mapLocationRect)
                FLOOR_1 -> Floor1Tile(spriteSheet, mapLocationRect)
                FLOOR_2 -> Floor2Tile(spriteSheet, mapLocationRect)
                FLOOR_3 -> Floor3Tile(spriteSheet, mapLocationRect)
                FLOOR_4 -> Floor4Tile(spriteSheet, mapLocationRect)
                FLOOR_5 -> Floor5Tile(spriteSheet, mapLocationRect)
                FLOOR_6 -> Floor6Tile(spriteSheet, mapLocationRect)
                FLOOR_7 -> Floor7Tile(spriteSheet, mapLocationRect)
                FLOOR_8 -> Floor8Tile(spriteSheet, mapLocationRect)
                WALL -> WallTile(spriteSheet, mapLocationRect)
                WALL_TOP -> WallTopTile(spriteSheet, mapLocationRect)
                WALL_SIDE_FRONT_LEFT -> WallSideFrontLeftTile(spriteSheet, mapLocationRect)
                LEFT_CORNER_WALL -> LeftCornerWallTile(spriteSheet, mapLocationRect)
                WALL_SIDE_LEFT -> WallSideLeftTile(spriteSheet, mapLocationRect)
                WALL_SIDE_RIGHT -> WallSideRightTile(spriteSheet, mapLocationRect)
                WALL_SIDE_LEFT_CORNER -> WallSideLeftCornerTile(spriteSheet, mapLocationRect)
                WALL_SIDE_RIGHT_CORNER -> WallSideRightCornerTile(spriteSheet, mapLocationRect)
                WALL_OUTSIDE_LEFT_CORNER -> WallOutsideLeftCornerTile(spriteSheet, mapLocationRect)
                WALL_FOUNTAIN_LAVA -> WallFountainLavaTile(spriteSheet, mapLocationRect)
                WALL_FOUNTAIN_TOP -> WallFountainTopTile(spriteSheet, mapLocationRect)
                FLOOR_FOUNTAIN_LAVA -> FloorFountainLavaTile(spriteSheet, mapLocationRect)

                else -> {EmptyTile(spriteSheet, mapLocationRect)}
            }
        }
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
    }
}