package com.tsu.mobilegamelab4.game.entity.enemy

import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.entity.Entity
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.map.MapLayout
import com.tsu.mobilegamelab4.game.entity.player.Player


abstract class Enemy(
    pos: Point,
    spriteSheet: EnemySpriteSheet,
    private val mapLayout: MapLayout,
    val player: Player,
    gameObjects: MutableList<GameObject>
) : Entity(pos, mapLayout,gameObjects) {

}