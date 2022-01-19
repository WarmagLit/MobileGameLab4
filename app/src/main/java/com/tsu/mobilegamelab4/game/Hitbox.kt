package com.tsu.mobilegamelab4.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Rect.intersects
import com.tsu.mobilegamelab4.game.gameobjects.GameObject
import com.tsu.mobilegamelab4.game.interfaces.IDrawable

class Hitbox(val gameObject: GameObject, val width: Int, val height: Int) : IDrawable {

    var offset = android.graphics.Point(0,0)

    var rect = Rect(
        gameObject.pos.X.toInt() + offset.x,
        gameObject.pos.Y.toInt() + offset.y,
        gameObject.pos.X.toInt() + width + offset.x,
        gameObject.pos.Y.toInt() + height+ offset.y
    )

    var rectPaint = Paint()

    init {
        rectPaint.color = Color.RED
        rectPaint.strokeWidth = 2f
    }

    override fun draw(canvas: Canvas, display: GameDisplay?) {
        canvas.drawRect(rect, rectPaint)
    }

    fun updateCoordinatesWithCentering(p:Point) {
        rect.left = p.X.toInt() + offset.x - width / 2
        rect.top = p.Y.toInt() + offset.y - height / 2
        rect.right = p.X.toInt() + offset.x + width / 2
        rect.bottom = p.Y.toInt() + offset.y + height / 2
    }

    fun updateCoordinates(p:Point) {
        rect.left = p.X.toInt()+ offset.x
        rect.top = p.Y.toInt() + offset.y
        rect.right = p.X.toInt() + offset.x + width
        rect.bottom = p.Y.toInt() + offset.y + height
    }

    fun isCollide(hitbox: Hitbox): Boolean {
        return intersects(rect, hitbox.rect)
    }

    fun isPointInside(p: Point): Boolean {
        return rect.contains(p.X.toInt(), p.Y.toInt())
    }
}