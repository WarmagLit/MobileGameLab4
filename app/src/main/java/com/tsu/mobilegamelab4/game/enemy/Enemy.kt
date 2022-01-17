package com.tsu.mobilegamelab4.game.enemy

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.graphics.SpriteSheet
import com.tsu.mobilegamelab4.game.map.MapLayout
import com.tsu.mobilegamelab4.game.player.HeroAnimator
import com.tsu.mobilegamelab4.game.player.Player
import com.tsu.mobilegamelab4.game.player.guns.Gun


abstract class Enemy(pos: Point, spriteSheet: EnemySpriteSheet, private val mapLayout: MapLayout, val player: Player) : GameObject(pos) {
    abstract fun changeVelocity(actuator: Vector)
}