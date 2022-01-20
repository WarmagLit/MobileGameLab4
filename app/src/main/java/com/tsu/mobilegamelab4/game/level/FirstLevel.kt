package com.tsu.mobilegamelab4.game.level

import android.graphics.Canvas
import android.util.DisplayMetrics
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.gameobjects.*
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Masker.Masker
import com.tsu.mobilegamelab4.game.gameobjects.entity.enemy.Wizard.Wizard
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.graphics.EnemySpriteSheet
import com.tsu.mobilegamelab4.game.graphics.FirstLocationSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.graphics.KeySpriteSheet
import com.tsu.mobilegamelab4.game.items.Keys
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap

class FirstLevel(
    private val enemySpriteSheet: EnemySpriteSheet,
    locationSpriteSheet: FirstLocationSpriteSheet,
    private val keySpriteSheet: KeySpriteSheet
) :
    Level(enemySpriteSheet, locationSpriteSheet, keySpriteSheet) {

    private val map = FirstLocationMap(locationSpriteSheet)
    private lateinit var player: Player

    init {
        gameObjects.addAll(
            arrayOf(
                Crate(
                    locationSpriteSheet,
                    Point(
                        7.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        18.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        5.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        21.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        3.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        21.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.BLUE
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        5.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        21.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.RED
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        7.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        21.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.EMPTY
                ),
                Door(
                    locationSpriteSheet,
                    Point(
                        25.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        9.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    listOf(
                        Keys.RED,
                        Keys.BLUE
                    )
                )
            )
        )
    }

    override fun initializePlayer(heroSpriteSheet: HeroSpriteSheet): Player {
        player = Player(
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
                ),
                Wizard(
                    Point(
                        16.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        19.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
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
            Comparator { go1: GameObject, go2: GameObject ->
                if (go1.pos.Y < go2.pos.Y) {
                    return@Comparator -1
                }
                if (go1.pos.Y > go2.pos.Y) {
                    return@Comparator 1
                }
                return@Comparator 0
            }
        )
        gameObjects.forEach {
            it.draw(canvas, display)
        }
        map.draw(canvas, display)

        //draw keys
        display?.let {
            var xOffset = 200
            for (key in player.keys) {
                val keySprite = keySpriteSheet.getSpriteByRect(key.keyRect)
                keySprite.size = android.graphics.Point(key.keyRect.width()*4, key.keyRect.height()*4)
                keySprite.draw(canvas, display.widthPixels - xOffset, 50)
                xOffset += 150
            }
        }
    }

    override fun update() {
        gameObjects = gameObjects.filter {
            !it.toDestroy
        }.toMutableList()

        gameObjects.forEach {
            it.update()
        }
    }

}