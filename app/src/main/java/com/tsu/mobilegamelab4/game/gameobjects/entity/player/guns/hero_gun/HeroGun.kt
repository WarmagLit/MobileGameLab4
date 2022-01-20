package com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.hero_gun

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.*
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.Entity
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.Bullet
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.guns.Gun
import com.tsu.mobilegamelab4.game.graphics.Sprite

class HeroGun(
    owner: Player,
    sprite: Sprite,
    gameObjects: MutableList<GameObject>
) : Gun(owner, sprite, gameObjects) {

    init {
        bulletSpeed = 40.0
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        //sprite.draw(canvas, owner.displayCoordinates.X.toInt(), owner.displayCoordinates.Y.toInt())

        for (bullet in bullets) {
            bullet.draw(canvas)
        }
    }

    override fun update() {
        for (bullet in bullets) {
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

    override fun fire(velocity: Vector) {
        val bullet1 = HeroBullet(
            Point(owner.displayCoordinates.X, owner.displayCoordinates.Y + 50),
            velocity.multiply(50.0)
        )
        val bullet2 = HeroBullet(
            Point(owner.displayCoordinates.X, owner.displayCoordinates.Y + 50),
            velocity.multiply(50.0).rotateByAngle(12.0)
        )
        val bullet3 = HeroBullet(
            Point(owner.displayCoordinates.X, owner.displayCoordinates.Y + 50),
            velocity.multiply(50.0).rotateByAngle(-12.0)
        )
        bullets.add(bullet1)
        bullets.add(bullet2)
        bullets.add(bullet3)
    }
}