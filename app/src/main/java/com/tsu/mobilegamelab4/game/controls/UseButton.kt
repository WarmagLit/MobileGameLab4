package com.tsu.mobilegamelab4.game.controls

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.tsu.mobilegamelab4.game.GameDisplay
import com.tsu.mobilegamelab4.game.Point
import com.tsu.mobilegamelab4.game.Utils
import com.tsu.mobilegamelab4.game.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.interfaces.IDrawableUpdatable
import com.tsu.mobilegamelab4.game.interfaces.IUsable
import com.tsu.mobilegamelab4.game.map.firstlocation.FirstLocationMap
import kotlin.math.pow
import kotlin.math.sqrt

class UseButton(
    var player: Player,
    private val usableGameObjects: List<IUsable>,
    private val outerCircleCenterPosition: Point,
    private val outerCircleRadius: Int
) : IDrawableUpdatable {

    private val outerCirclePaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private var usableObject: IUsable? = null
    var isVisible = false

    init {
        outerCirclePaint.color = Color.parseColor("#ffffff")
        outerCirclePaint.style = Paint.Style.STROKE
        outerCirclePaint.strokeWidth = 10f

        textPaint.color = Color.parseColor("#ffffff")
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 60f
    }

    fun onClick() {
        usableObject?.use(player)
    }

    fun isPressed(touchPosition: Point): Boolean {
        val centerToTouchDistance = sqrt(
            (outerCircleCenterPosition.X - touchPosition.X).pow(2.0) +
                    (outerCircleCenterPosition.Y - touchPosition.Y).pow(2.0)
        )
        return centerToTouchDistance < outerCircleRadius
    }


    override fun draw(canvas: Canvas, display: GameDisplay?) {
        if (isVisible) {
            canvas.drawArc(
                RectF(
                    (outerCircleCenterPosition.X - outerCircleRadius).toFloat(),
                    (outerCircleCenterPosition.Y - outerCircleRadius).toFloat(),
                    (outerCircleCenterPosition.X + outerCircleRadius).toFloat(),
                    (outerCircleCenterPosition.Y + outerCircleRadius).toFloat()
                ),
                0f,
                360f,
                false,
                outerCirclePaint
            )
            canvas.drawText(
                "USE",
                (outerCircleCenterPosition.X).toFloat(),
                (outerCircleCenterPosition.Y + 25).toFloat(),
                textPaint
            )
        }
    }

    override fun update() {
        for (usable in usableGameObjects) {
            if (Utils.getDistanceBetweenPoints(
                    player.pos,
                    usable.getCenter()
                ) <= FirstLocationMap.CELL_WIDTH_PIXELS * 1.2
            ) {
                isVisible = true
                usableObject = usable
                return
            }
        }
        isVisible = false
    }
}