package com.tsu.mobilegamelab4.game.surfaceview.graphics

import android.content.Context
import android.graphics.BitmapFactory
import com.tsu.mobilegamelab4.R

open class EnemySpriteSheet(context: Context) : SpriteSheet(context) {


    init {
        spriteWidthPixels = 16
        spriteHeightPixels = 19

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.enemy_spritesheet, bitmapOptions)
    }
}