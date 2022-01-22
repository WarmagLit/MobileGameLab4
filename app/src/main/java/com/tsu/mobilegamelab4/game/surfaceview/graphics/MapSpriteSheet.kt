package com.tsu.mobilegamelab4.game.surfaceview.graphics

import android.content.Context
import android.graphics.BitmapFactory
import com.tsu.mobilegamelab4.R

class MapSpriteSheet(context: Context) : SpriteSheet(context) {

    init {
        spriteWidthPixels = 32
        spriteHeightPixels = 32

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.map_sprite_sheet, bitmapOptions)
    }

    val stoneSprite: Sprite
        get() = getSpriteByIndex(0, 1)
    val waterSprite: Sprite
        get() = getSpriteByIndex(0, 4)
    val grassSprite: Sprite
        get() = getSpriteByIndex(0, 3)
}