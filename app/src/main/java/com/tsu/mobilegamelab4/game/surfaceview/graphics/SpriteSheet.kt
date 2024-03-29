package com.tsu.mobilegamelab4.game.surfaceview.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect

abstract class SpriteSheet(context: Context) {
    lateinit var bitmap: Bitmap
    protected var spriteWidthPixels: Int = 0
    protected var spriteHeightPixels: Int = 0


    fun getSpriteByIndex(idxRow: Int, idxCol: Int): Sprite {
        return Sprite(
            this, Rect(
                idxCol * spriteWidthPixels,
                idxRow * spriteHeightPixels,
                (idxCol + 1) * spriteWidthPixels,
                (idxRow + 1) * spriteHeightPixels
            )
        )
    }

    fun getSpriteByIndexWithSize(idxRow: Int, idxCol: Int, spriteSize: Point): Sprite {
        val sprite = Sprite(
            this, Rect(
                idxCol * spriteWidthPixels,
                idxRow * spriteHeightPixels,
                (idxCol + 1) * spriteWidthPixels,
                (idxRow + 1) * spriteHeightPixels
            )
        )
        sprite.size = spriteSize
        return sprite
    }

    fun getSpriteByIndex(rect: Rect): Sprite {
        return Sprite(
            this, Rect(
                rect.left * spriteWidthPixels,
                rect.top * spriteHeightPixels,
                rect.right * spriteWidthPixels,
                rect.bottom * spriteHeightPixels
            )
        )
    }

    fun getSpriteByRect(rect: Rect): Sprite {
        return Sprite(
            this, Rect(
                rect.left,
                rect.top,
                rect.right,
                rect.bottom
            )
        )
    }
}