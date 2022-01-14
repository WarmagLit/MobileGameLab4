package com.tsu.mobilegamelab4.game.controls

import android.R.attr.action
import android.util.Log
import android.view.MotionEvent
import com.tsu.mobilegamelab4.game.Point


class TouchDistributor(val joystick: Joystick, var swipeStick: SwipeStick) {

    fun handleTouch(event: MotionEvent): Boolean {
        // Handle user input touch event actions

        val pointerIndex =
            action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {

                Log.d(
                    "CHECK",
                    "event coords: x = ${
                        event.getX(event.getPointerId(event.actionIndex)).toDouble()
                    } y = ${event.getY(event.getPointerId(event.actionIndex)).toDouble()}"
                )
                //  Log.d("CHECK", "event ${event.getPointerId(event.actionIndex)}")

                if (joystick.isPressed) {
                    // Joystick was pressed
                    // **Some operation**
                    Log.d("CHECK", "joystick is pressed")
                } else if (joystick.isPressed(
                        Point(
                            event.getX(event.getPointerId(pointerIndex)).toDouble(),
                            event.getY(event.getPointerId(pointerIndex)).toDouble()
                        )
                    )
                ) {
                    // Joystick is pressed in this event
                    joystick.pointerId = event.getPointerId(pointerIndex)
                    joystick.isPressed = true
                    Log.d("CHECK", "joystick is pressed now with ID: ${joystick.pointerId}")
                } else
//                {
//                    Log.d("CHECK", "Joystick was not previously, and is not pressed in this event")
//                    // Joystick was not previously, and is not pressed in this event
//                    // **Some operation**
//                }

                    if (swipeStick.isPressed) {
                        // swipeStick was pressed
                        // **Some operation**
                        Log.d("CHECK", "swipestick is pressed")
                    } else if (swipeStick.isPressed(
                            Point(
                                event.getX(event.getPointerId(pointerIndex)).toDouble(),
                                event.getY(event.getPointerId(pointerIndex)).toDouble()
                            )
                        )
                    ) {
                        // swipeStick is pressed in this event
                        swipeStick.pointerId = event.getPointerId(pointerIndex)
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
                // Log.d("CHECK", "POINTER ID ${event.getPointerId(event.actionIndex)}")
                if (joystick.isPressed && joystick.pointerId == event.getPointerId(pointerIndex)) {
                    // Joystick was pressed previously and is now moved
//                    Log.d("CHECK", "Joystick move")
                    joystick.setActuator(
                        Point(
                            event.getX(joystick.pointerId).toDouble(),
                            event.getY(joystick.pointerId).toDouble()
                        )
                    )
                } else if (swipeStick.isPressed && swipeStick.pointerId == event.getPointerId(
                        pointerIndex
                    )
                ) {
                    // Joystick was pressed previously and is now moved
                    Log.d("CHECK", "swipestick move")
                    swipeStick.setActuator(
                        Point(
                            event.getX(swipeStick.pointerId).toDouble(),
                            event.getY(swipeStick.pointerId).toDouble()
                        )
                    )
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                if (joystick.pointerId == event.getPointerId(pointerIndex) && joystick.isPressed) {
                    // joystick pointer was let go off
                    Log.d("CHECK", "joystick UP with id: ${joystick.pointerId}")
                    joystick.isPressed = false
                    joystick.resetActuator()
                } else if (swipeStick.pointerId == event.getPointerId(pointerIndex) && swipeStick.isPressed) {
                    // swipeStick pointer was let go off
                    Log.d("CHECK", "swipestick UP with id: ${swipeStick.pointerId}")
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