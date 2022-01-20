package com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.magic_gun

import android.graphics.Canvas
import android.util.Log
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.Gun
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.hero_gun.HeroBullet
import com.tsu.mobilegamelab4.game.graphics.Sprite

class MagicGun(
    owner: Entity,
    sprite: Sprite,
    gameObjects: MutableList<GameObject>
) : Gun(owner, sprite, gameObjects) {

    val position = owner.displayCoordinates

    init {
        bulletSpeed = 15.0
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        //sprite.draw(canvas, owner.displayCoordinates.X.toInt(), owner.displayCoordinates.Y.toInt())

        for (bullet in bullets) {
            bullet.draw(canvas, display)
        }
    }

    override fun update() {
        for (bullet in bullets) {
            bullet.update()
            for (obj in gameObjects) {
                if (obj is Entity && obj != owner && obj.hitbox.isPointInside(bullet.displayCoordinates)) {
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

    override fun fire(velocity: Vector) {
        val fireVector = Vector(velocity.X, velocity.Y)
        val bullet = MagicBall(owner.pos.clone(), fireVector.multiply(bulletSpeed))
        bullets.add(bullet)
    }
}