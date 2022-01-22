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

class FirstLevel(
    private val enemySpriteSheet: EnemySpriteSheet,
    private val bossSpriteSheet: BossSpriteSheet,
    private val locationSpriteSheet: FirstLocationSpriteSheet,
    private val keySpriteSheet: KeySpriteSheet
) :
    Level(enemySpriteSheet, keySpriteSheet) {

    private val map = FirstLocationMap(locationSpriteSheet)
    private lateinit var player: Player

    init {
        level = 1

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
                        11.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    listOf(
                        Keys.RED,
                        Keys.BLUE
                    )
                ),
                Steps(
                    locationSpriteSheet,
                    Point(
                        28.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        2.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                ),
                Spikes(
                    locationSpriteSheet,
                    Point(
                        5.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        18.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    )
                )
            )
        )
    }

    override fun recycleBitmaps() {
        if (!enemySpriteSheet.bitmap.isRecycled) {
            enemySpriteSheet.bitmap.recycle()
        }
        if (!bossSpriteSheet.bitmap.isRecycled) {
            bossSpriteSheet.bitmap.recycle()
        }
        if (!keySpriteSheet.bitmap.isRecycled) {
            keySpriteSheet.bitmap.recycle()
        }
        if (!locationSpriteSheet.bitmap.isRecycled) {
            locationSpriteSheet.bitmap.recycle()
        }
        if (!locationSpriteSheet.lowerLevelBitmap.isRecycled) {
            locationSpriteSheet.lowerLevelBitmap.recycle()
        }
        if (!locationSpriteSheet.upperLevelBitmap.isRecycled) {
            locationSpriteSheet.upperLevelBitmap.recycle()
        }
//        enemySpriteSheet.bitmap.recycle()
//        bossSpriteSheet.bitmap.recycle()
//        locationSpriteSheet.bitmap.recycle()
//        locationSpriteSheet.lowerLevelBitmap.recycle()
//        locationSpriteSheet.upperLevelBitmap.recycle()
//        keySpriteSheet.bitmap.recycle()
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
        gameObjects.filterIsInstance<Spikes>().forEach { it.player = player }
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
                ),
                Landmine(
                    Point(
                        18.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        23.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    enemySpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                ),
                Boss(
                    Point(
                        18.0 * FirstLocationMap.CELL_WIDTH_PIXELS,
                        23.0 * FirstLocationMap.CELL_HEIGHT_PIXELS
                    ),
                    bossSpriteSheet,
                    player,
                    map.collisionLayout,
                    gameObjects
                )
            ).toList()
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

}