package com.tsu.mobilegamelab4.game

import android.util.Log
import android.view.MotionEvent

class TouchDistributor(val joystick: Joystick) {

    fun handleTouch(event: MotionEvent): Boolean {
        // Handle user input touch event actions
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (joystick.isPressed) {
                    // Joystick was pressed
                    // **Some operation**
                } else if (joystick.isPressed(Point(event.x.toDouble(), event.y.toDouble()))) {
                    // Joystick is pressed in this event
                    joystick.pointerId = event.getPointerId(event.actionIndex)
                    joystick.isPressed = true
                } else {
                    // Joystick was not previously, and is not pressed in this event
                    // **Some operation**
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (joystick.isPressed) {
                    // Joystick was pressed previously and is now moved
                    joystick.setActuator(Point(event.x.toDouble(), event.y.toDouble()))
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (joystick.pointerId == event.getPointerId(event.actionIndex)) {
                    // joystick pointer was let go off
                    joystick.isPressed = false
                    joystick.resetActuator()
                }
                return true
            }
        }
        return false
    }
}