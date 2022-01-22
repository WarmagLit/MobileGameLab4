package com.tsu.mobilegamelab4.game.surfaceview.level

import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.graphics.KeySpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IDrawableUpdatable

abstract class Level(
    enemySpriteSheet: EnemySpriteSheet,
    spriteSheet: FirstLocationSpriteSheet,
    keySpriteSheet: KeySpriteSheet
) : IDrawableUpdatable {

    var gameObjects: MutableList<GameObject> = mutableListOf()
    var level: Int = 0

    abstract fun recycleBitmaps()
    abstract fun initializePlayer(heroSpriteSheet: HeroSpriteSheet): Player
}