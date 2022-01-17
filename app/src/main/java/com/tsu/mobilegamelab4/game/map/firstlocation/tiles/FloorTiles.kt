package com.tsu.mobilegamelab4.game.map.firstlocation.tiles

import android.graphics.Rect
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.map.Tile

class Floor1Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,0)
    }
}

class Floor2Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,1)
    }
}

class Floor3Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,2)
    }
}

class Floor4Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,3)
    }
}

class Floor5Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,4)
    }
}

class Floor6Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,5)
    }
}

class Floor7Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,6)
    }
}

class Floor8Tile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,7)
    }
}

class FloorLadderTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,8)
    }
}

class FloorSpikesTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(0,13)
    }
}

class FloorFountainLavaTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(4,6)
    }
}