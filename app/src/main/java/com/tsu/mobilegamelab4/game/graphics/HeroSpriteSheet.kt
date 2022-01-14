package com.tsu.mobilegamelab4.game.graphics

import android.content.Context
import android.graphics.BitmapFactory
import com.tsu.mobilegamelab4.R

class HeroSpriteSheet(context: Context) : SpriteSheet(context) {

    init {
        spriteWidthPixels = 17
        spriteHeightPixels = 17

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.hero_sprite_sheet, bitmapOptions)
    }

    val gunSprite: Sprite
        get() = getSpriteByIndex(9,0)
}