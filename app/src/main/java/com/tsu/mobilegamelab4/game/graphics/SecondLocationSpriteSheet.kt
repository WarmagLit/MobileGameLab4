package com.tsu.mobilegamelab4.game.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tsu.mobilegamelab4.R

class SecondLocationSpriteSheet(context: Context) : SpriteSheet(context) {

    var lowerLevelBitmap: Bitmap
    var upperLevelBitmap: Bitmap

    init {
        spriteWidthPixels = 16
        spriteHeightPixels = 16

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.first_location_spritesheet,
                bitmapOptions
            )
        lowerLevelBitmap =
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.second_level_lower,
                bitmapOptions
            )
        upperLevelBitmap =
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.secod_level_upper,
                bitmapOptions
            )
    }
}