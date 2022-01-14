package com.tsu.mobilegamelab4.game.controls

import android.view.MotionEvent
import com.tsu.mobilegamelab4.game.Point

class TouchDistributor(val joystick: Joystick, var swipeStick: SwipeStick) {

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

                if (swipeStick.isPressed) {
                    // swipeStick was pressed
                    // **Some operation**
                } else if (swipeStick.isPressed(Point(event.x.toDouble(), event.y.toDouble()))) {
                    // swipeStick is pressed in this event
                    swipeStick.pointerId = event.getPointerId(event.actionIndex)
                    swipeStick.isPressed = true
                } else {
                    // swipeStick was not previously, and is not pressed in this event
                    // **Some operation**
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (joystick.isPressed) {
                    // Joystick was pressed previously and is now moved
                    joystick.setActuator(Point(event.x.toDouble(), event.y.toDouble()))
                }
                if (swipeStick.isPressed) {
                    // Joystick was pressed previously and is now moved
                    swipeStick.setActuator(Point(event.x.toDouble(), event.y.toDouble()))
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (joystick.pointerId == event.getPointerId(event.actionIndex) && joystick.isPressed) {
                    // joystick pointer was let go off
                    joystick.isPressed = false
                    joystick.resetActuator()
                }
                if (swipeStick.pointerId == event.getPointerId(event.actionIndex) && swipeStick.isPressed) {
                    // swipeStick pointer was let go off
                    swipeStick.isPressed = false
                    swipeStick.action()
                    swipeStick.resetActuator()
                }
                return true
            }
        }
        return false
    }
}