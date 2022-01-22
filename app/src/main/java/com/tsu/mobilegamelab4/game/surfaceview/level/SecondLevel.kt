package com.tsu.mobilegamelab4.game.surfaceview.level

import android.graphics.Canvas
import com.tsu.mobilegamelab4.game.surfaceview.GameDisplay
import com.tsu.mobilegamelab4.game.surfaceview.Point
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.*
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Landmine.Landmine
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Masker.Masker
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.enemy.Wizard.Wizard
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.*
import com.tsu.mobilegamelab4.game.surfaceview.items.Keys
import com.tsu.mobilegamelab4.game.surfaceview.map.firstlocation.FirstLocationMap
import com.tsu.mobilegamelab4.game.surfaceview.map.secondlocation.SecondLocationMap

class SecondLevel(
    private val enemySpriteSheet: EnemySpriteSheet,
    private val bossSpriteSheet: BossSpriteSheet,
    private val locationSpriteSheet: SecondLocationSpriteSheet,
    private val keySpriteSheet: KeySpriteSheet
) :
    Level(enemySpriteSheet, keySpriteSheet) {

    private val map = SecondLocationMap(locationSpriteSheet)
    private lateinit var player: Player

    init {
        level = 2

        gameObjects.addAll(
            arrayOf(
                Steps(
                    locationSpriteSheet,
                    Point(
                        26.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        36.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Door(
                    locationSpriteSheet,
                    Point(
                        24.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        40.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    listOf(
                        Keys.RED,
                        Keys.BLUE,
                        Keys.YELLOW
                    )
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        2.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.RED
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        43.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.YELLOW
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        22.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        24.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.EMPTY
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        7.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        50.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.BLUE
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        7.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        52.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.EMPTY
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        6.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        51.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.EMPTY
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        39.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.EMPTY
                ),
                Chest(
                    locationSpriteSheet,
                    Point(
                        31.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    Keys.EMPTY
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        40.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        14.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        23.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        25.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        23.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        23.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        26.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        24.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        30.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        39.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        2.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        51.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Crate(
                    locationSpriteSheet,
                    Point(
                        3.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        52.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        43.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        18.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        18.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        20.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        1.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        19.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        2.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        19.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        3.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        19.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        39.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        40.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        41.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        42.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        43.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        44.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        45.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        46.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        14.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        48.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        39.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        40.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        41.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        42.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        43.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        44.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        45.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        46.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        11.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        48.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        39.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        40.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        41.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        42.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        43.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        44.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        45.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        46.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        8.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        48.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        33.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        36.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        33.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        35.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        33.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        34.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        19.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        21.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        45.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        24.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        25.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        47.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Column(
                    locationSpriteSheet,
                    Point(
                        28.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        45.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
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
                23.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                5.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
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
        gameObjects.addAll(
            arrayOf(
                Masker(
                    Point(
                        16.3 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        20.4 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Wizard(
                    Point(
                        25.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        21.1 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Masker(
                    Point(
                        16.3 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        35.4 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Wizard(
                    Point(
                        25.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        32.1 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Landmine(
                    Point(
                        44.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        34.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Landmine(
                    Point(
                        43.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        38.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Landmine(
                    Point(
                        44.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        43.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Wizard(
                    Point(
                        10.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        36.6 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Wizard(
                    Point(
                        12.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        37.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Landmine(
                    Point(
                        10.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        51.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Wizard(
                    Point(
                        36.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        42.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Wizard(
                    Point(
                        2.6 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        21.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
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

}//DONE