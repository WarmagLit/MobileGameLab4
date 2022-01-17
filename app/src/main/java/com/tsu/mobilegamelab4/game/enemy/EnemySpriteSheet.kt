package com.tsu.mobilegamelab4.game.enemy

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.game.graphics.Sprite

class EnemySpriteSheet(context: Context) {
    val bitmap: Bitmap

    fun getSpriteByIndex(idxRow: Int, idxCol: Int): EnemySprite {
        return EnemySprite(
            this, Rect(
                idxCol * WIDTH,
                idxRow * HEIGHT,
                (idxCol + 1) * WIDTH,
                (idxRow + 1) * HEIGHT
            )
        )
    }

    companion object {
        private const val WIDTH = 16
        private const val HEIGHT = 19
    }

    init {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.sprite_sheet, bitmapOptions)
    }
}