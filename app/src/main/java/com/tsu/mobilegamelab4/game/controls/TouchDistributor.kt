package com.tsu.mobilegamelab4.game.controls

import android.util.Log
import android.view.MotionEvent
import com.tsu.mobilegamelab4.game.Point


class TouchDistributor(val joystick: Joystick, var swipeStick: SwipeStick) {

    fun handleTouch(event: MotionEvent): Boolean {
        // Handle user input touch event actions

        val pointerIndex = event.actionIndex
        val pointerID = event.getPointerId(pointerIndex)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {

                Log.d(
                    "CHECK",
                    "event coords: x = ${
                        event.getX(pointerIndex).toDouble()
                    } y = ${event.getY(pointerIndex).toDouble()}"
                )
                //  Log.d("CHECK", "event ${event.getPointerId(event.actionIndex)}")

                if (joystick.isPressed && joystick.pointerId == pointerID) {
                    // Joystick was pressed
                    // **Some operation**
                    Log.d("CHECK", "joystick is pressed")
                } else if (joystick.isPressed(
                        Point(
                            event.getX(pointerIndex).toDouble(),
                            event.getY(pointerIndex).toDouble()
                        )
                    )
                ) {
                    // Joystick is pressed in this event
                    joystick.pointerId = pointerID
                    joystick.isPressed = true
                    Log.d("CHECK", "joystick is pressed now with ID: ${joystick.pointerId}")
                } else {
                    Log.d("CHECK", "Joystick was not previously, and is not pressed in this event")
                    // Joystick was not previously, and is not pressed in this event
                    // **Some operation**
                }

                if (swipeStick.isPressed && swipeStick.pointerId == pointerID) {
                    // swipeStick was pressed
                    // **Some operation**
                    Log.d("CHECK", "swipestick is pressed")
                } else if (swipeStick.isPressed(
                        Point(
                            event.getX(pointerIndex).toDouble(),
                            event.getY(pointerIndex).toDouble()
                        )
                    )
                ) {
                    // swipeStick is pressed in this event
                    swipeStick.pointerId = pointerID
                    Log.d("CHECK", "swipestick is pressed now with ID: ${swipeStick.pointerId}")
                    swipeStick.isPressed = true
                } else {
                    Log.d(
                        "CHECK",
                        "swipeStick was not previously, and is not pressed in this event"
                    )
                    // swipeStick was not previously, and is not pressed in this event
                    // **Some operation**
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("CHECK", "POINTER ID $pointerID INDEX $pointerIndex")
                Log.d(
                    "CHECK",
                    "joystick POINTER ID ${joystick.pointerId} INDEX ${
                        event.findPointerIndex(joystick.pointerId)
                    }"
                )
                Log.d(
                    "CHECK",
                    "swipestick POINTER ID ${swipeStick.pointerId} INDEX ${
                        event.findPointerIndex(swipeStick.pointerId)
                    }"
                )

                if (joystick.isPressed) {
                    // Joystick was pressed previously and is now moved
//                    Log.d("CHECK", "Joystick move")
                    joystick.setActuator(
                        Point(
                            event.getX(event.findPointerIndex(joystick.pointerId)).toDouble(),
                            event.getY(event.findPointerIndex(joystick.pointerId)).toDouble()
                        )
                    )
                }

                if (swipeStick.isPressed) {
                    // Joystick was pressed previously and is now moved
                    Log.d("CHECK", "swipestick move")
                    swipeStick.setActuator(
                        Point(
                            event.getX(event.findPointerIndex(swipeStick.pointerId)).toDouble(),
                            event.getY(event.findPointerIndex(swipeStick.pointerId)).toDouble()
                        )
                    )
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                if (joystick.pointerId == pointerID && joystick.isPressed) {
                    // joystick pointer was let go off
                    Log.d("CHECK", "joystick UP with id: ${joystick.pointerId}")
                    joystick.isPressed = false
                    joystick.pointerId = -1
                    joystick.resetActuator()
                }

                if (swipeStick.pointerId == pointerID && swipeStick.isPressed) {
                    // swipeStick pointer was let go off
                    Log.d("CHECK", "swipestick UP with id: ${swipeStick.pointerId}")
                    swipeStick.isPressed = false
                    swipeStick.pointerId = -1
                    swipeStick.action()
                    swipeStick.resetActuator()
                }
                return true
            }
        }
        return false
    }
}