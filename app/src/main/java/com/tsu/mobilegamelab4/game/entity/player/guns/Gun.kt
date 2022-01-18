package com.tsu.mobilegamelab4.game.entity.player.guns

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.Vector
import com.tsu.mobilegamelab4.game.entity.Entity
import com.tsu.mobilegamelab4.game.entity.enemy.Enemy
import com.tsu.mobilegamelab4.game.graphics.Sprite
import com.tsu.mobilegamelab4.game.entity.player.Player

class Gun(
    private val owner: Player,
    position: Point,
    private val sprite: Sprite,
    private val gameObjects: MutableList<GameObject>
) : GameObject(position) {

    private val bullets: MutableList<Bullet> = mutableListOf()

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        sprite.draw(canvas, pos.X.toInt(), pos.Y.toInt())

        for(bullet in bullets) {
            bullet.draw(canvas)
        }
    }

    override fun update() {
        for(bullet in bullets) {
            bullet.update()
            for (obj in gameObjects) {
                if (obj is Entity && obj != owner && obj.hitbox.isPointInside(bullet.pos)) {
                    obj.hit(bullet)
                    bullet.toDestroy = true
                }
            }
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