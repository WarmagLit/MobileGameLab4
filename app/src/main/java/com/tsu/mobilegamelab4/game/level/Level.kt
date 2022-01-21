package com.tsu.mobilegamelab4.game.level

import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.KeySpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap

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