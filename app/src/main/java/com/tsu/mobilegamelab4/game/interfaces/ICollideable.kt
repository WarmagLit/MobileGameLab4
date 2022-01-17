package com.tsu.mobilegamelab4.game.interfaces

import com.tsu.mobilegamelab4.game.player.guns.Bullet

interface ICollideable {
    fun hit(bullet: Bullet)
}