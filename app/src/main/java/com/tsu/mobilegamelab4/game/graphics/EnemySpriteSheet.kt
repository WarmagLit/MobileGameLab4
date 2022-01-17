package com.tsu.mobilegamelab4.game.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.game.enemy.EnemySprite

class EnemySpriteSheet(context: Context) : SpriteSheet(context) {


    init {
        spriteWidthPixels = 16
        spriteHeightPixels = 19

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.enemy_mask, bitmapOptions)
    }
}