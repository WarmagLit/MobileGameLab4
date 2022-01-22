package com.tsu.mobilegamelab4.game.surfaceview.level

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.*
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Boss.Boss
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Landmine.Landmine
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Masker.Masker
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Wizard.Wizard
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.*
import com.tsu.mobilegamelab4.game.surfaceview.items.Keys
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap
import com.tsu.mobilegamelab4.game.surfaceview.map.thirdlocation.ThirdLocationMap

class ThirdLevel(
    private val enemySpriteSheet: EnemySpriteSheet,
    private val bossSpriteSheet: BossSpriteSheet,
    private val locationSpriteSheet: ThirdLocationSpriteSheet,
    private val keySpriteSheet: KeySpriteSheet
) :
    Level(enemySpriteSheet, keySpriteSheet) {

    private val map = ThirdLocationMap(locationSpriteSheet)
    private lateinit var player: Player

    init {
        level = 3

        gameObjects.addAll(
            arrayOf(
                Steps(
                    locationSpriteSheet,
                    Point(
                        29.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        3.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Door(
                    locationSpriteSheet,
                    Point(
                        29.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        9.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    listOf(
                        Keys.RED,
                        Keys.BLUE,
                        Keys.GREEN,
                        Keys.YELLOW
                    )
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        2.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.YELLOW
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        55.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.RED
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        39.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        43.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.GREEN
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        20.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        43.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.BLUE
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        10.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        15.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        13.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        17.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        18.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        20.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        22.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        38.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        39.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        43.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        46.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        46.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        46.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        48.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        49.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        49.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        49.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        39.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        41.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        20.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        41.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        37.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        41.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        22.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        41.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                )
            )
        )
    }

    override fun recycleBitmaps() {
        enemySpriteSheet.bitmap.recycle()
        bossSpriteSheet.bitmap.recycle()
        locationSpriteSheet.bitmap.recycle()
        locationSpriteSheet.lowerLevelBitmap.recycle()
        locationSpriteSheet.upperLevelBitmap.recycle()
        keySpriteSheet.bitmap.recycle()
    }

    override fun initializePlayer(heroSpriteSheet: HeroSpriteSheet): Player {
        player = Player(
            Point(
                33.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
            ),
            heroSpriteSheet,
            map.collisionLayout,
            gameObjects
        )
        gameObjects.add(player)
        addEnemies(player)
        gameObjects.filterIsInstance<Spikes>().forEach { it.player = player }
        return player
    }

    private fun addEnemies(player: Player) {
        gameObjects.addAll(arrayOf(
            Masker(
                Point(
                    44.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Masker(
                Point(
                    44.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Masker(
                Point(
                    36.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Wizard(
                Point(
                    38.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Wizard(
                Point(
                    41.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Wizard(
                Point(
                    43.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    16.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Landmine(
                Point(
                    9.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Landmine(
                Point(
                    14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Landmine(
                Point(
                    18.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    15.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                enemySpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Boss(
                Point(
                    24.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    36.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                bossSpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            ),
            Boss(
                Point(
                    37.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                    36.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                ),
                bossSpriteSheet,
                player,
                map.collisionLayout,
                gameObjects
            )
        ))
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        map.draw(canvas, display)
        gameObjects.sortWith(
            Comparator { go1: GameObject, go2: GameObject ->
                if (go1 is Spikes || go1.pos.Y < go2.pos.Y) {
                    return@Comparator -1
                }
                if (go2 is Spikes || go1.pos.Y > go2.pos.Y) {
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
                keySprite.size =
                    android.graphics.Point(key.keyRect.width() * 4, key.keyRect.height() * 4)
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