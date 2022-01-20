package com.tsu.mobilegamelab4.game.controls

import android.view.MotionEvent
import com.tsu.mobilegamelab4.game.Point


class TouchDistributor(
    private val joystick: Joystick,
    private var swipeStick: SwipeStick,
    private val useButton: UseButton
) {

    fun handleTouch(event: MotionEvent): Boolean {
        // Handle user input touch event actions

        val pointerIndex = event.actionIndex
        val pointerID = event.getPointerId(pointerIndex)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {

                if (joystick.isPressed && joystick.pointerId == pointerID) {
                    // Joystick was pressed
                    // **Some operation**
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
                } else {
                    // Joystick was not previously, and is not pressed in this event
                    // **Some operation**
                }

                if (swipeStick.isPressed && swipeStick.pointerId == pointerID) {
                    // swipeStick was pressed
                    // **Some operation**
                } else if (swipeStick.isPressed(
                        Point(
                            event.getX(pointerIndex).toDouble(),
                            event.getY(pointerIndex).toDouble()
                        )
                    )
                ) {
                    // swipeStick is pressed in this event
                    swipeStick.pointerId = pointerID
                    swipeStick.isPressed = true
                } else {
                    // swipeStick was not previously, and is not pressed in this event
                    // **Some operation**
                }

                if (useButton.isVisible && useButton.isPressed(
                        Point(
                            event.getX(pointerIndex).toDouble(),
                            event.getY(pointerIndex).toDouble()
                        )
                    ) && pointerID != joystick.pointerId && pointerID != swipeStick.pointerId
                ) {
                    useButton.onClick()
                }

                return true
            }
            MotionEvent.ACTION_MOVE -> {
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
                    joystick.isPressed = false
                    joystick.pointerId = -1
                    joystick.resetActuator()
                }

                if (swipeStick.pointerId == pointerID && swipeStick.isPressed) {
                    // swipeStick pointer was let go off
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