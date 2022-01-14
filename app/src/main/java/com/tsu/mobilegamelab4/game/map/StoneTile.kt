package com.tsu.mobilegamelab4.game.map

import android.graphics.Point
import android.graphics.Rect
import com.tsu.mobilegamelab4.game.graphics.MapSpriteSheet

class StoneTile(spriteSheet: MapSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.stoneSprite
        sprite.size = Point(MapLayout.TILE_WIDTH_PIXELS, MapLayout.TILE_HEIGHT_PIXELS)
    }
}
