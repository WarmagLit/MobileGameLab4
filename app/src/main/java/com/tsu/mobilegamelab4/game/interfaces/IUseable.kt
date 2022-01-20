package com.tsu.mobilegamelab4.game.interfaces

import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player

interface IUsable {
    fun use(player: Player)
    fun getCenter(): Point
}