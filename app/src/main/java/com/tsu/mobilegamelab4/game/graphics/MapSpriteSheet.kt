package com.tsu.mobilegamelab4.game.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.game.SpriteSheet

class MapSpriteSheet(context: Context) : SpriteSheet(context) {
    override val bitmap: Bitmap

    companion object {
        private const val SPRITE_WIDTH_PIXELS = 32
        private const val SPRITE_HEIGHT_PIXELS = 32
        const val TILE_WIDTH = 64
        const val TILE_HEIGHT = 64
    }

    val stoneSprite: Sprite
        get() = getSpriteByIndex(0, 1)
    val waterSprite: Sprite
        get() = getSpriteByIndex(0, 4)
    val grassSprite: Sprite
        get() = getSpriteByIndex(0, 3)

    private fun getSpriteByIndex(idxRow: Int, idxCol: Int): Sprite {
        return Sprite(
            this, Rect(
                idxCol * SPRITE_WIDTH_PIXELS,
                idxRow * SPRITE_HEIGHT_PIXELS,
                (idxCol + 1) * SPRITE_WIDTH_PIXELS,
                (idxRow + 1) * SPRITE_HEIGHT_PIXELS
            )
        )
    }

    init {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.maptiles, bitmapOptions)
    }
}