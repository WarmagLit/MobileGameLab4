package com.tsu.mobilegamelab4.game.player.guns

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.GameObject
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.clone
import com.tsu.mobilegamelab4.game.graphics.Sprite
import java.util.*

class Gun(
    position: Point,
    private val sprite: Sprite
) : GameObject(position) {

    private val bullets: MutableList<Bullet> = mutableListOf()

    override fun draw(canvas: Canvas) {
        sprite.draw(canvas, pos.X.toInt(), pos.Y.toInt())

        for(bullet in bullets) {
            bullet.draw(canvas)
        }
    }

    override fun update() {
        for(bullet in bullets) {
            bullet.update()
            if (bullet.toDestroy) {
                bullets.remove(bullet)
                break
            }
        }

    }

    fun fire(velocity: Vector) {
        val bullet = Bullet(Point(pos.X + 100, pos.Y + 50), velocity)
        bullets.add(bullet)
    }
}