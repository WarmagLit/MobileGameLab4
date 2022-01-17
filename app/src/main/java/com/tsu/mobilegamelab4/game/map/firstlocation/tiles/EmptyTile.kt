package com.tsu.mobilegamelab4.game.map.firstlocation.tiles

import android.graphics.Rect
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.map.Tile

class EmptyTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(5, 0)
    }
}