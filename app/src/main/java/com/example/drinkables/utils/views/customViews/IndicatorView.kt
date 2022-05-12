package com.example.drinkables.utils.views.customViews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.toRectF
import com.example.drinkables.R
import java.math.RoundingMode

private const val MEASURED_ERROR = "Error with measured"

class IndicatorView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    //region Attributes
    private var _value: Float
    private var _maxValue: Float
    private val defaultColor: Int
    private val fillColor: Int
    private val textWidth: Int
    private val textColor: Int

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        with(context.obtainStyledAttributes(attrs, R.styleable.customView)) {
            _value = getFloat(R.styleable.customView_indicator_value, DEFAULT_VALUE)
            _maxValue = getFloat(R.styleable.customView_max_value, DEFAULT_MAX_VALUE)
            defaultColor = getColor(R.styleable.customView_default_color, DEFAULT_COLOR)
            fillColor = getColor(R.styleable.customView_fill_color, DEFAULT_FILL_COLOR)
            textWidth = getDimension(R.styleable.customView_text_width, DEFAULT_TEXT_WIDTH.toFloat()).toInt()
            textColor = getColor(R.styleable.customView_text_color, DEFAULT_TEXT_COLOR)
        }
    }
    //endregion
    //region Tools

    var value
        set(f) {
            _value = f
        }
        get() = _value

    var maxValue
        set(f) {
            _maxValue = f
        }
        get() = _maxValue

    private val rectViewLimit = Rect()

    private val cornerValue = 50

    private val textPoint = Point()

    private val paintFillBar = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = fillColor
    }

    private val paintDefaultBar = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = defaultColor
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = textWidth.toFloat()
        textAlign = Paint.Align.RIGHT
    }

    private val porterDuffMode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private val barBackgroundBitmap: Bitmap by lazy {
        Bitmap.createBitmap(
            rectViewLimit.width(),
            rectViewLimit.height(),
            Bitmap.Config.ARGB_8888
        )
    }

    private val barBitmap: Bitmap by lazy {
        Bitmap.createBitmap(
            rectViewLimit.width(),
            rectViewLimit.height(),
            Bitmap.Config.ARGB_8888
        )
    }

    private var isViewOnScreen: Boolean = false

    private var animatedValuePositionX = 0

    private var animatedValue = 0f

    private var barAnimator = ValueAnimator()

    private var valueAnimator = ValueAnimator()

    private val calculateValuePosition: Int
        get() = (rectViewLimit.left + (((rectViewLimit.right - rectViewLimit.left) / 100) * (_value / (_maxValue / 100)))).toInt()

    //endregion
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = calculateViewSize(DEFAULT_VIEW_WIDTH, widthMeasureSpec)
        val height = calculateViewSize(DEFAULT_VIEW_HEIGHT, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectViewLimit.apply {
            left = paddingLeft
            right = width - paddingRight
            top = paddingTop
            bottom = height - paddingBottom
        }
        startAnimations()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawBar(canvas)
            drawValueText(canvas)
        }
    }

    private fun drawBar(canvas: Canvas) {
        canvas.drawBitmap(barBitmap, null, rectViewLimit, paintFillBar)
    }

    private fun drawValueText(canvas: Canvas) {
        canvas.drawText(
            animatedValue.toString(), textPoint.x.toFloat(),
            textPoint.y.toFloat(), paintText
        )
    }

    private fun prepareTextPoint(text: String) {
        textPoint.apply {
            x = animatedValuePositionX
            y = (rectViewLimit.centerY() - (paintText.descent() + paintText.ascent()) / 2).toInt()
        }
    }

    private fun prepareBarBackgroundBitmap() {
        barBackgroundBitmap.applyCanvas {
            val rectFill = Rect().apply {
                left = 0
                right = animatedValuePositionX
                top = 0
                bottom = rectViewLimit.height()
            }
            val rectDefault = Rect().apply {
                left = animatedValuePositionX
                right = rectViewLimit.width()
                top = 0
                bottom = rectViewLimit.height()
            }
            drawRect(rectFill, paintFillBar)
            drawRect(rectDefault, paintDefaultBar)
        }
    }

    private fun prepareBarBitmap() {
        barBitmap.applyCanvas {
            val barRect = Rect().apply {
                left = 0
                right = rectViewLimit.width()
                top = 0
                bottom = rectViewLimit.height()
            }
            drawRoundRect(barRect.toRectF(), cornerValue.toFloat(), cornerValue.toFloat(), paintDefaultBar)
            drawBitmap(barBackgroundBitmap, null, barRect, paintDefaultBar.apply { xfermode = porterDuffMode })
            paintFillBar.xfermode = null
        }
    }

    private fun calculateViewSize(calculatedSize: Int, measureSpec: Int): Int {
        val measuredSize = MeasureSpec.getSize(measureSpec)
        return when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.UNSPECIFIED -> calculatedSize
            MeasureSpec.EXACTLY -> measuredSize
            MeasureSpec.AT_MOST -> measuredSize
            else -> error(MEASURED_ERROR)
        }
    }

    private fun prepareBarAnimator(elementDuration: Long): ValueAnimator {
        val animator = ValueAnimator.ofInt(0, calculateValuePosition).apply {
            duration = elementDuration
            interpolator = LinearInterpolator()
            addUpdateListener { animatorValue ->
                animatedValuePositionX = animatorValue.animatedValue as Int
                prepareBarBackgroundBitmap()
                prepareBarBitmap()
                invalidate()
            }
        }
        return animator
    }

    private fun prepareValueAnimator(elementDuration: Long): ValueAnimator {
        val animator = ValueAnimator.ofFloat(0f, _value).apply {
            duration = elementDuration
            interpolator = LinearInterpolator()
            addUpdateListener {
                this@IndicatorView.animatedValue =
                    (it.animatedValue as Float).toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
                prepareTextPoint(this@IndicatorView.animatedValue.toString())
                invalidate()
            }
        }
        return animator
    }

    private fun startAnimations() {
        barAnimator = prepareBarAnimator(2000)
        valueAnimator = prepareValueAnimator(2000)
        barAnimator.start()
        valueAnimator.start()
    }

    companion object {
        private const val DEFAULT_VALUE = 50f
        private const val DEFAULT_MAX_VALUE = 100f
        private const val DEFAULT_COLOR = Color.GRAY
        private const val DEFAULT_FILL_COLOR = Color.RED
        private const val DEFAULT_VIEW_WIDTH = 400
        private const val DEFAULT_VIEW_HEIGHT = 100
        private const val DEFAULT_TEXT_WIDTH = 100
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
    }
}