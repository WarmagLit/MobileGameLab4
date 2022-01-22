package com.tsu.mobilegamelab4.game.surfaceview.items

import android.graphics.Rect

enum class Keys(val keyRect: Rect) {
    RED(Rect(22,0,36,38)),
    BLUE(Rect(0,0,22,38)),
    GREEN(Rect(37,0,55,32)),
    YELLOW(Rect(56,0,71,35)),
    EMPTY(Rect(0,0,0,0))
}