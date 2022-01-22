package com.tsu.mobilegamelab4.game.surfaceview.interfaces

import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.guns.Bullet

interface ICollideable {
    fun hit(bullet: Bullet)
}