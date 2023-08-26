package ru.gozerov.presentation.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.text.InputFilter
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import ru.gozerov.presentation.R
import kotlin.properties.Delegates

class VerificationCodeView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var textViewCount by Delegates.notNull<Int>()

    private var cellHeight by Delegates.notNull<Float>()
    private var innerMargin by Delegates.notNull<Float>()

    private var textSize by Delegates.notNull<Float>()

    private var orientation by Delegates.notNull<Int>()

    private val textViews = mutableListOf<TextView>()

    var onTextChangedListener: ((String) -> Unit)? = null

    private var text = ""

    init {
        if (attrs != null) initAttrs(context, attrs, defStyleAttr, defStyleRes)
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = orientation
        (0 until textViewCount).forEachIndexed { i, v ->
            val textView = TextView(this.context)

            val margin = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, innerMargin, resources.displayMetrics) / 2).toInt()

            val height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cellHeight, resources.displayMetrics).toInt()
            textView.background = AppCompatResources.getDrawable(context, R.drawable.verification_text_view_background)
            textView.height = height
            textView.gravity = Gravity.CENTER
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            textView.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black, context.theme)))

            val layoutParams = MarginLayoutParams(context, attrs)
            when(i) {
                0 -> layoutParams.marginEnd = margin
                textViewCount - 1 -> layoutParams.marginStart = margin
                else -> {
                    layoutParams.marginStart = margin
                    layoutParams.marginEnd = margin
                }
            }
            textView.layoutParams = layoutParams
            textViews.add(textView)
            linearLayout.addView(textView)
        }
        this.addView(linearLayout)
        val editText = EditText(context)
        linearLayout.gravity = Gravity.CENTER_HORIZONTAL
        editText.gravity = Gravity.CENTER_HORIZONTAL
        editText.isCursorVisible = false
        editText.background = null
        editText.filters = arrayOf(InputFilter.LengthFilter(textViewCount))
        editText.inputType = EditorInfo.TYPE_CLASS_NUMBER

        editText.textSize = 0f
        editText.addTextChangedListener { editable ->
            text = editable.toString()
            val t = Array<String?>(textViewCount) { null }
            editable?.forEachIndexed { i, c ->
                t[i] = c.toString()
            }
            fillText(t.toList())
            onTextChangedListener?.invoke(text)
        }

        this.addView(editText)
    }

    private fun fillText(t: List<String?>) {
        t.forEachIndexed { i, c ->
            textViews[i].text = c
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateViewSizes()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView, defStyleAttr, defStyleRes)
        textViewCount = attrsArray.getInt(R.styleable.VerificationCodeView_textViewCount, DEFAULT_TEXT_VIEW_COUNT)
        cellHeight = attrsArray.getDimension(R.styleable.VerificationCodeView_cellHeight, DEFAULT_CELL_SIZE)
        innerMargin = attrsArray.getDimension(R.styleable.VerificationCodeView_innerMargin, DEFAULT_INNER_MARGIN)
        textSize = attrsArray.getDimension(R.styleable.VerificationCodeView_android_textSize, DEFAULT_TEXT_SIZE)
        orientation = attrsArray.getInt(R.styleable.VerificationCodeView_android_orientation, LinearLayout.HORIZONTAL)
        attrsArray.recycle()
    }

    private fun updateViewSizes() {
        val margin = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, innerMargin, resources.displayMetrics) / 2).toInt()
        val width = (width - paddingLeft - paddingRight) / textViewCount - (2 * margin)
        ((this.children.first { it is LinearLayout }) as LinearLayout).children.forEach {
            it.updateLayoutParams<LinearLayout.LayoutParams> {
                this.width = width
            }
        }
    }

    override fun onDetachedFromWindow() {
        onTextChangedListener = null
        super.onDetachedFromWindow()
    }

    companion object {

        const val DEFAULT_TEXT_VIEW_COUNT = 4
        const val DEFAULT_CELL_SIZE = 60f
        const val DEFAULT_INNER_MARGIN = 8f
        const val DEFAULT_TEXT_SIZE = 12f

    }

}