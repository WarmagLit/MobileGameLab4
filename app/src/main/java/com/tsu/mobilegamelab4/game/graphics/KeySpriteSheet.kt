package com.tsu.mobilegamelab4.game.graphics

import android.content.Context
import android.graphics.BitmapFactory
import com.tsu.mobilegamelab4.R

class KeySpriteSheet(context: Context) : SpriteSheet(context) {

    init {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.keys_spritesheet,
                bitmapOptions
            )
    }
}