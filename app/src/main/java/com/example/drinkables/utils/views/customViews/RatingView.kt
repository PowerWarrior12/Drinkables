package com.example.drinkables.utils.views.customViews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.graphics.applyCanvas
import com.example.drinkables.R
import com.example.drinkables.utils.views.generateBitmap
import java.math.RoundingMode
import kotlin.math.abs

private const val MEASURED_ERROR = "Error with measured"
private const val STATUS_ANIMATION_DURATION = 200000L

class RatingView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    // region Attributes
    var value: Int
        get() = _value
        set(v) {
            _value = v
            statusPosition = calculateStatusPosition
            invalidate()
        }
    private val iconId: Int
    private val defaultColor: Int
    private val selectedColor: Int
    private var _value: Int

    private val itemSize: Int
    private val itemsIndent: Int
    private val itemsCount: Int

    // region Tools
    // region Transformations
    private val rectView by lazy { Rect(0, 0, width, height) }

    private val markItems: Map<Int, MarkItemUI>

    private val calculateViewWidth
        get() = (itemSize + itemsIndent) * itemsCount - itemsIndent

    private val calculateViewHeight
        get() = itemSize

    private val calculateStatusPosition
        get() = _value * (itemSize + itemsIndent)

    private var statusPosition = calculateStatusPosition

    private var onItemClickListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(rating: Int) {}
    }

    // endregion
    //Bitmaps
    private val iconsBitmap: Bitmap by lazy { Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8) }

    private val statusBitmap: Bitmap by lazy { Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) }

    //Paints
    private val iconsPaint = Paint()

    private val statusPaint = Paint()

    private val statusPorterDuffMode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    //endregion

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        context.obtainStyledAttributes(attrs, R.styleable.customView).apply {
            try {
                defaultColor = getColor(R.styleable.customView_default_color, DEFAULT_COLOR)
                selectedColor = getColor(R.styleable.customView_selected_color, DEFAULT_SELECTED_COLOR)
                _value = getInteger(R.styleable.customView_value, DEFAULT_VALUE)
                itemSize = getDimension(R.styleable.customView_item_size, DEFAULT_ITEM_SIZE.toFloat()).toInt()
                itemsIndent = getDimension(R.styleable.customView_items_indent, DEFAULT_ITEMS_INDENT.toFloat()).toInt()
                itemsCount = getInteger(R.styleable.customView_items_count, DEFAULT_ITEMS_COUNT)
                iconId = getResourceId(R.styleable.customView_view_icon, DEFAULT_ICON)
            } finally {
                recycle()
            }
            markItems = createMarkItemsUI()
        }
    }

    // endregion

    // region Overrides
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = calculateDefaultSize(calculateViewWidth, widthMeasureSpec)
        val height = calculateDefaultSize(calculateViewHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        prepareIconsBitmap()
        prepareStatusBitmap()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!
        canvas.drawBitmap(iconsBitmap, null, rectView, iconsPaint)
        statusPaint.xfermode = statusPorterDuffMode
        canvas.drawBitmap(statusBitmap, null, rectView, statusPaint)
        statusPaint.xfermode = null
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        return handleClick(event)
    }

    fun addOnItemClickListener(callback: OnItemClickListener) {
        onItemClickListener = callback
    }

    //endregion
    //region Prepare bitmaps
    private fun prepareIconsBitmap() {
        val x = rectView
        if (iconsBitmap.isRecycled) {
            iconsBitmap.recycle()
        }
        iconsBitmap.applyCanvas {
            markItems.forEach { (_, markItemUI) ->
                markItemUI.drawItem(this, iconsPaint)
            }
        }
    }

    private fun prepareStatusBitmap() {
        if (statusBitmap.isRecycled) {
            statusBitmap.recycle()
        }
        statusBitmap.applyCanvas {
            statusPaint.color = selectedColor
            drawRect(0f, 0f, statusPosition.toFloat(), height.toFloat(), statusPaint)

            statusPaint.color = defaultColor
            drawRect(statusPosition.toFloat(), 0f, width.toFloat(), height.toFloat(), statusPaint)
        }
    }

    //endregion
    //region Calculating functions
    private fun getIndexByXPosition(position: Int): Int {
        return (position / (itemSize + itemsIndent)).toBigDecimal().setScale(0, RoundingMode.DOWN).toInt()
    }

    private fun calculateDurationForStatus(): Long {
        return STATUS_ANIMATION_DURATION / (2 * width - abs(calculateStatusPosition - statusPosition))
    }

    private fun calculateDefaultSize(calculatingSize: Int, measureSpec: Int): Int {
        val measuredSize = MeasureSpec.getSize(measureSpec)

        val size = when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.UNSPECIFIED -> measuredSize
            MeasureSpec.EXACTLY -> measuredSize
            MeasureSpec.AT_MOST -> calculatingSize
            else -> error(MEASURED_ERROR)
        }

        return size
    }

    //endregion
    private fun handleClick(event: MotionEvent): Boolean {
        return if (event.pointerCount == 1 && event.action == MotionEvent.ACTION_DOWN) {
            val xPosition = event.x
            val index = getIndexByXPosition(xPosition.toInt())

            if (markItems.containsKey(index)) {
                val markItem = markItems[index]!!
                if (markItem.isCurrentPoint(xPosition.toInt())) {
                    _value = index + 1
                    onItemClickListener.onItemClick(_value)
                    val animator = prepareChangeStatusAnimator(calculateDurationForStatus())
                    animator.start()
                }
            }
            false
        } else false
    }

    private fun createMarkItemsUI(): Map<Int, MarkItemUI> {
        val marksList = mutableMapOf<Int, MarkItemUI>()
        val bitmap = generateBitmap(resources, context, iconId)
        repeat(itemsCount) { index ->
            marksList[index] = MarkItemUI(bitmap).apply {
                updateRect(index, itemSize, itemsIndent)
            }
        }
        return marksList
    }

    private fun prepareChangeStatusAnimator(durationForElement: Long): ValueAnimator {
        val animator = ValueAnimator.ofInt(statusPosition, calculateStatusPosition).apply {
            interpolator = LinearInterpolator()
            duration = durationForElement
            addUpdateListener { animatorValue ->
                statusPosition = animatorValue.animatedValue as Int
                prepareStatusBitmap()
                invalidate()
            }
        }
        return animator
    }

    fun interface OnItemClickListener {
        fun onItemClick(rating: Int)
    }

    /**
     * Special class, for helping work with items
     */
    class MarkItemUI(private val iconBitmap: Bitmap) {
        private val rect = Rect()

        fun drawItem(canvas: Canvas, paint: Paint) {
            canvas.drawBitmap(iconBitmap, null, rect, paint)
        }

        fun isCurrentPoint(positionX: Int): Boolean {
            return rect.left <= positionX && positionX <= rect.right
        }

        fun updateRect(index: Int, itemSize: Int, itemsIndent: Int) {
            rect.apply {
                left = index * (itemSize + itemsIndent)
                right = left + itemSize
                top = 0
                bottom = itemSize
            }
        }
    }

    companion object {
        private const val DEFAULT_ITEM_SIZE = 64
        private const val DEFAULT_COLOR = Color.GRAY
        private const val DEFAULT_SELECTED_COLOR = Color.RED
        private const val DEFAULT_VALUE = 3
        private const val DEFAULT_ITEMS_INDENT = 4
        private const val DEFAULT_ITEMS_COUNT = 5
        private const val DEFAULT_ICON = R.drawable.ic_heart
    }
}