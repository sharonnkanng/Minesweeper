package hu.ait

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import hu.ait.MineSweeperModel.getFieldContent
import hu.ait.MineSweeperModel.setFieldContent
import hu.ait.minesweeper.R

class MineSweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val tileUnknown = R.drawable.tileunknown
    val tile1 = R.drawable.tile1
    val tile2 = R.drawable.tile2
    val tile3 = R.drawable.tile3
    val tile4 = R.drawable.tile4
    val tile5 = R.drawable.tile5
    val tile6 = R.drawable.tile6
    val tile7 = R.drawable.tile7
    val tile8 = R.drawable.tile8
    val tileBomb = R.drawable.tileexploded
    val tileFlag = R.drawable.tileflag
    val tileempty = R.drawable.tileempty
    private val flagedCells = Array(5) { BooleanArray(5) { false } }
    private var tileBitmap = BitmapFactory.decodeResource(resources, tileUnknown)

    var tX: Int = -1
    var tY:Int = -1
    val resetTextView = findViewById<TextView>(R.id.resetTextView)
    val scoreTextView = findViewById<TextView>(R.id.scoreTextView)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            tX = event.x.toInt() / (width / 5)
            tY = event.y.toInt() / (height / 5)

            if (getFieldContent(tX, tY) != MineSweeperModel.BOMB) {
                setFieldContent(tX, tY)
                invalidate()
            } else {
                MineSweeperModel.isExplode = true
            }

        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawGameArea(canvas)
        if (MineSweeperModel.isExplode) {
            gameOver(canvas, tX, tY)
            resetTextView.text = context?.getString(R.string.Restart)
        } else {
            scoreTextView.text = "You have ${MineSweeperModel.mineNumber} left"
        }
    }

    fun gameOver(canvas: Canvas?, tX: Int, tY: Int) {
        val left = tX * (width / 5).toFloat()
        val top = tY * (height / 5).toFloat()
        val right = ((tX + 1) * (width / 5)).toFloat()
        val bottom = ((tY + 1) * (height / 5)).toFloat()
        val rect = RectF(left, top, right, bottom)
        canvas?.drawBitmap(BitmapFactory.decodeResource(resources, tileBomb), null, rect, null)

    }

    fun getTileBitmap(tileNum: Int): Bitmap {

        tileBitmap =
            when (tileNum) {
                1 -> BitmapFactory.decodeResource(resources, tile1)
                2 -> BitmapFactory.decodeResource(resources, tile2)
                3 -> BitmapFactory.decodeResource(resources, tile3)
                4 -> BitmapFactory.decodeResource(resources, tile4)
                5 -> BitmapFactory.decodeResource(resources, tile5)
                6 -> BitmapFactory.decodeResource(resources, tile6)
                7 -> BitmapFactory.decodeResource(resources, tile7)
                8 -> BitmapFactory.decodeResource(resources, tile8)
                else -> BitmapFactory.decodeResource(resources, tileUnknown)
            }

        return tileBitmap

    }

    fun resetGame() {
        MineSweeperModel.resetModel()
        resetTextView.text = context?.getString(R.string.Reset)
        invalidate()
    }


    fun drawGameArea(canvas: Canvas?) {

        for (row in 0 until 5) {
            for (col in 0 until 5) {

                val left = row * (width / 5).toFloat()
                val top = col * (height / 5).toFloat()
                val right = ((row + 1) * (width / 5)).toFloat()
                val bottom = ((col + 1) * (height / 5)).toFloat()
                val rect = RectF(left, top, right, bottom)

                tileBitmap = getTileBitmap(MineSweeperModel.getFieldContent(row, col))
                canvas?.drawBitmap(tileBitmap, null, rect, null)
            }
        }
    }
}