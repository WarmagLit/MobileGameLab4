package com.tsu.mobilegamelab4.game.level

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.gameobjects.Crate
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Masker
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap

class FirstLevel(
    private val enemySpriteSheet: EnemySpriteSheet,
    spriteSheet: FirstLocationSpriteSheet
) :
    IDrawableUpdatable {

    private val map = FirstLocationMap(spriteSheet)
    private var gameObjects: MutableList<GameObject> = mutableListOf()

    init {
        gameObjects.addAll(
            arrayOf(
                Crate(
                    spriteSheet,
                    Point(
                        7.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        18.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                )
            )
        )
    }

    fun initializePlayer(heroSpriteSheet: HeroSpriteSheet): Player {
        val player = Player(
            Point(
                4.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                18.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
            ),
            heroSpriteSheet,
            map.collisionLayout,
            gameObjects
        )
        gameObjects.add(player)
        addEnemies(player)
        return player
    }

    private fun addEnemies(player: Player) {
        gameObjects.addAll(
            arrayOf(
                Masker(
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        18.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                )
            )
        )
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        map.draw(canvas, display)
        gameObjects.sortWith(
            Comparator{ go1: GameObject, go2: GameObject ->
                if (go1.pos.X < go2.pos.X) {
                    return@Comparator 1
                }
                if (go1.pos.X > go2.pos.X) {
                    return@Comparator -1
                }
                return@Comparator 0
            }
        )
        gameObjects.map {
            it.draw(canvas, display)
        }
        map.draw(canvas, display)
    }

    override fun update() {
        gameObjects = gameObjects.filter {
            !it.toDestroy
        }.toMutableList()

        gameObjects.map {
            it.update()
        }
    }

}