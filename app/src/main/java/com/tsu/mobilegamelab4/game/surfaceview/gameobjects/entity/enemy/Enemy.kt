package com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy

import com.tsu.mobilegamelab4.game.surfaceview.*
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.surfaceview.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.map.CollisionLayout
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationCollisionLayout


abstract class Enemy(
    pos: Point,
    spriteSheet: EnemySpriteSheet,
    private val collisionLayout: CollisionLayout,
    val player: Player,
    gameObjects: MutableList<GameObject>
) : Entity(pos, collisionLayout,gameObjects) {

}