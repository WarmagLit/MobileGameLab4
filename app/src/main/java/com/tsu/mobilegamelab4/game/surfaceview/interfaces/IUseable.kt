package com.tsu.mobilegamelab4.game.surfaceview.interfaces

import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player

interface IUsable {
    fun use(player: Player)
    fun getCenter(): Point
    fun isUsed(): Boolean
}