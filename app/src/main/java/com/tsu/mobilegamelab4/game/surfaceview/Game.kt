package com.tsu.mobilegamelab4.game.surfaceview

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.chooselevel.ChooseLevelActivity
import com.tsu.mobilegamelab4.game.GameActivity
import com.tsu.mobilegamelab4.game.surfaceview.controls.Joystick
import com.tsu.mobilegamelab4.game.surfaceview.controls.SwipeStick
import com.tsu.mobilegamelab4.game.surfaceview.controls.TouchDistributor
import com.tsu.mobilegamelab4.game.surfaceview.controls.UseButton
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.Chest
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.Steps
import com.tsu.mobilegamelab4.game.surfaceview.gameobjects.entity.player.Player
import com.tsu.mobilegamelab4.game.surfaceview.graphics.HeroSpriteSheet
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IUpdatable
import com.tsu.mobilegamelab4.game.surfaceview.interfaces.IUsable
import com.tsu.mobilegamelab4.game.surfaceview.items.Keys
import com.tsu.mobilegamelab4.game.surfaceview.level.Level
import com.tsu.mobilegamelab4.menu.MenuActivity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("ViewConstructor")
class Game(private val activity: GameActivity, private val currentLevel: Level) :
    SurfaceView(activity),
    SurfaceHolder.Callback,
    IUpdatable {

    private val viewModel by activity.viewModels<GameViewModel>()

    private val gameDisplay: GameDisplay
    private var gameLoop: GameLoop
    private val joystick: Joystick
    private val useButton: UseButton
    private val swipeStick: SwipeStick
    private val touchDistributor: TouchDistributor

    private val player: Player

    var userCompletedLevels = 0

    val performance: Performance

    // For sensors
    var sensorUpDown = 0.0
    var sensorSides = 0.0

    private val textPaint = Paint()

    var isJoystick = true
    var showPerformance = false

    var isDeathDialogShowed = false

    init {
        setObservers()

        // Metrics for SwipeStick and CenteredGameDisplay
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        Utils.setDisplayMetrics(context)

        // For Accelerometer values
        textPaint.color = Color.CYAN
        textPaint.textSize = 50f

        // Get surface holder and add callback
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)

        // Initialize game panels
        performance = Performance()

        // Set player
        Utils.setPlayerSkin(context)
        player = currentLevel.initializePlayer(HeroSpriteSheet(context))

        //create exit from level
        val steps: Steps = currentLevel.gameObjects.find { it is Steps } as Steps
        steps.levelCompleted = {
            activity.lifecycleScope.launch {
                showLevelCompletedDialog {
                    val intent = Intent(activity, ChooseLevelActivity::class.java)
                    currentLevel.recycleBitmaps()
                    sendCollectedKeysAndLevel()
                    activity.startActivity(intent)
                    activity.finish()
                }
            }
        }

        //create dialogs from open chest
        currentLevel.gameObjects.filterIsInstance<Chest>().forEach { chest ->
            chest.showDialog = {
                activity.lifecycleScope.launch {
                    showOpenChestDialog(it)
                }
            }
        }

        // UseButton
        useButton = UseButton(
            player,
            currentLevel.gameObjects.filter { it is IUsable }.map { it as IUsable },
            Point(displayMetrics.widthPixels.toDouble() - 600.0, 900.0),
            100
        )

        // Joystick
        joystick = Joystick(player, Point(275.0, 700.0), 180, 80)

        // SwipeStick
        swipeStick =
            SwipeStick(player, Point(displayMetrics.widthPixels.toDouble() - 275.0, 700.0), 180, 80)

        // Touch Distributor
        touchDistributor = TouchDistributor(joystick, swipeStick, useButton)

        // Initialize display
        gameDisplay = GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player)

        isFocusable = true

    }

    private fun setObservers() {
        viewModel.showPerformance.observe(activity) {
            showPerformance = it
        }

        viewModel.isJoystick.observe(activity) {
            isJoystick = it
        }

        viewModel.levelsCompleted.observe(activity) {
            userCompletedLevels = it
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state.equals(Thread.State.TERMINATED)) {
            holder.addCallback(this)
            gameLoop = GameLoop(this, holder)
        }
        gameLoop.startLoop()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (touchDistributor.handleTouch(event)) {
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        currentLevel.draw(canvas, gameDisplay)

        swipeStick.draw(canvas)

        useButton.draw(canvas)

        if (isJoystick) {
            joystick.draw(canvas)
        } else {
            extraDraw(canvas)
        }

        if (showPerformance) performance.draw(canvas)

        // Draw HealthBar ont hte top of all drawables
        player.healthBar.draw(canvas, gameDisplay)
    }

    private fun extraDraw(canvas: Canvas) {
        canvas.drawText(
            "${resources.getString(R.string.accelerometer)} X:${sensorSides.toInt()} \n Y:${sensorUpDown.toInt()}",
            100f,
            400f,
            textPaint
        )
    }

    override fun update() {
        if (player.toDestroy && !isDeathDialogShowed) {
            showDialog()
            isDeathDialogShowed = true
        }

        currentLevel.update()

        gameDisplay.update()

        useButton.update()

        swipeStick.update()

        if (isJoystick) {
            joystick.update()
        } else {
            joystick.player.changeVelocity(Vector(sensorUpDown / 100, sensorSides / 100))
        }
        //joystick.update()
    }

    fun pause() {
        gameLoop.stopLoop()
    }

    private fun sendCollectedKeysAndLevel() {
        if (userCompletedLevels < currentLevel.level) {
            viewModel.updateCompletedLevels(userCompletedLevels + 1)
        }
        viewModel.sendKeys(player.keys)
    }

    private suspend fun showOpenChestDialog(loot: Keys) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_open_chest_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.findViewById<ConstraintLayout>(R.id.rootLayout).setOnClickListener {
            dialog.dismiss()
        }

        val keyImageView = dialog.findViewById<ImageView>(R.id.dialogChestKeyImageView)
        val title = dialog.findViewById<TextView>(R.id.dialogChestTitleTextView)

        when (loot) {
            Keys.BLUE -> keyImageView.setImageResource(R.drawable.blue_key)
            Keys.RED -> keyImageView.setImageResource(R.drawable.red_key)
            Keys.YELLOW -> keyImageView.setImageResource(R.drawable.yellow_key)
            Keys.GREEN -> keyImageView.setImageResource(R.drawable.green_key)
            Keys.EMPTY -> {
                keyImageView.visibility = View.GONE
                title.setText(R.string.chest_was_empty)
            }
        }

        dialog.show()
        coroutineScope {
            delay(1500)
            dialog.dismiss()
        }
    }

    private suspend fun showLevelCompletedDialog(onDismiss: () -> Unit) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        dialog.setContentView(R.layout.dialog_open_chest_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val keyImageView = dialog.findViewById<ImageView>(R.id.dialogChestKeyImageView)
        val title = dialog.findViewById<TextView>(R.id.dialogChestTitleTextView)

        keyImageView.visibility = View.GONE
        title.setText(R.string.level_completed)

        dialog.show()
        coroutineScope {
            delay(1500)
            onDismiss.invoke()
            dialog.dismiss()
        }
    }

    private fun showDialog() {
        activity.runOnUiThread {
            showDeathDialog()
        }
    }

    private fun showDeathDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_death_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnRestart = dialog.findViewById<MaterialButton>(R.id.dialogRestartButton)
        btnRestart.setOnClickListener {
            currentLevel.recycleBitmaps()
            activity.restartLevel(currentLevel.level)
            dialog.dismiss()
        }
        val btnBackToMenu = dialog.findViewById<MaterialButton>(R.id.dialogBackToMenuButton)
        btnBackToMenu.setOnClickListener {
            currentLevel.recycleBitmaps()
            val intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }

        dialog.create()
        dialog.show()
        pause()
    }
}