package com.tsu.mobilegamelab4.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.game.graphics.Sprite

open class SpriteSheet(context: Context) {
    open val bitmap: Bitmap
    val playerSpriteArray: List<Sprite>
        get() {
            val spriteArray: MutableList<Sprite> = mutableListOf()

            for (i in 0..7) {
                for (j in 0..3) {
                    spriteArray.add(
                        Sprite(
                            this,
                            Rect(
                                j * HERO_WIDTH,
                                i * HERO_WIDTH,
                                (j + 1) * SPRITE_WIDTH_PIXELS,
                                (i + 1) * SPRITE_WIDTH_PIXELS
                            )
                        )
                    )
                }
            }
            return spriteArray
        }


    val lavaSprite: Sprite
        get() = getSpriteByIndex(1, 1)
    val groundSprite: Sprite
        get() = getSpriteByIndex(1, 2)

    val treeSprite: Sprite
        get() = getSpriteByIndex(1, 4)
    val heroSprite: Sprite
        get() = getSpriteByIndex(0, 0)

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

    companion object {
        private const val SPRITE_WIDTH_PIXELS = 17
        private const val SPRITE_HEIGHT_PIXELS = 17
        private const val HERO_WIDTH = 17
        private const val HERO_HEIGHT = 17
    }

    init {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.sprite_sheet, bitmapOptions)
    }
}