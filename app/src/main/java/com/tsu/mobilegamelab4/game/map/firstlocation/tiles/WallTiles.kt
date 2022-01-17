package com.tsu.mobilegamelab4.game.map.firstlocation.tiles

import android.graphics.Rect
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.map.Tile

class WallTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(1,0)
        isObstacle = true
    }
}

class LeftCornerWallTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(1,5)
        isObstacle = true
    }
}

class WallSideFrontLeftTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,0)
        isObstacle = true
    }
}

class WallSideFrontRightTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,1)
        isObstacle = true
    }
}

class WallSideRightTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,3)
        isObstacle = true
    }
}

class WallSideLeftTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,2)
        isObstacle = true
    }
}

class WallSideLeftCornerTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,4)
        isObstacle = true
    }
}

class WallSideRightCornerTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,5)
        isObstacle = true
    }
}

class WallTopTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,6)
    }
}

class WallOutsideLeftCornerTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(2,7)
        isObstacle = true
    }
}

class WallFountainLavaTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(3,6)
        isObstacle = true
    }
}

class WallFountainTopTile(spriteSheet: FirstLocationSpriteSheet, mapLocationRect: Rect) :
    Tile(mapLocationRect) {
    init {
        sprite = spriteSheet.getSpriteByIndex(3,9)
        isObstacle = true
    }
}





