package com.tsu.mobilegamelab4.game.map

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.SpriteSheet
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.Sprite

class StoneTile(spriteSheet: MapSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.stoneSprite
        sprite.size = Point(MapLayout.TILE_WIDTH_PIXELS, MapLayout.TILE_HEIGHT_PIXELS)
    }
}
