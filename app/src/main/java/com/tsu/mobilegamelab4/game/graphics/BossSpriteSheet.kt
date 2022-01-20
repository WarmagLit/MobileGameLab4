package com.tsu.mobilegamelab4.game.graphics

import android.content.Context
import android.graphics.BitmapFactory
import com.tsu.mobilegamelab4.R

class BossSpriteSheet(context: Context) : EnemySpriteSheet(context) {
    init {
        spriteWidthPixels = 136
        spriteHeightPixels = 44

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.boss_spritesheet, bitmapOptions)
    }
}