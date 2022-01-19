package com.tsu.mobilegamelab4.game.gameobjects.entity.enemy

import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationCollisionLayout


abstract class Enemy(
    pos: Point,
    spriteSheet: EnemySpriteSheet,
    private val collisionLayout: FirstLocationCollisionLayout,
    val player: Player,
    gameObjects: MutableList<GameObject>
) : Entity(pos, collisionLayout,gameObjects) {

}