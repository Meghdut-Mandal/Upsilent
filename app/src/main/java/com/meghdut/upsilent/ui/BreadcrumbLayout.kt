package com.meghdut.upsilent.ui

import android.R.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.annotation.*
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.TintTypedArray
import androidx.core.view.isVisible
import com.meghdut.upsilent.R
import com.meghdut.upsilent.databinding.BreadcrumbItemBinding
import com.meghdut.upsilent.models.BreadcrumbData
import com.meghdut.upsilent.utils.layoutInflater
import java.nio.file.Path



fun Context.getDimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)
@ColorInt
fun Context.getColorByAttr(@AttrRes attr: Int): Int =
        getColorStateListByAttr(attr).defaultColor


@SuppressLint("RestrictedApi")
fun Context.getColorStateListByAttr(@AttrRes attr: Int): ColorStateList =
        obtainStyledAttributesCompat(attrs = intArrayOf(attr)).run { getColorStateList(0) }

@SuppressLint("RestrictedApi")
fun Context.obtainStyledAttributesCompat(
        set: AttributeSet? = null,
        @StyleableRes attrs: IntArray,
        @AttrRes defStyleAttr: Int = 0,
        @StyleRes defStyleRes: Int = 0
): TintTypedArray =
        TintTypedArray.obtainStyledAttributes(this, set, attrs, defStyleAttr, defStyleRes)

fun Context.withTheme(@StyleRes themeRes: Int): Context =
        if (themeRes != 0) ContextThemeWrapper(this, themeRes) else this


@SuppressLint("RestrictedApi")
fun Context.getResourceIdByAttr(@AttrRes attr: Int): Int =
        obtainStyledAttributesCompat(attrs = intArrayOf(attr)).run { getResourceId(0, 0) }

class BreadcrumbLayout : HorizontalScrollView {
    private val tabLayoutHeight = context.getDimensionPixelSize(R.dimen.tab_layout_height)
    private val itemColor =
            ColorStateList(
                    arrayOf(intArrayOf(attr.state_activated), intArrayOf()),
                    intArrayOf(
                            context.getColorByAttr(attr.textColorPrimary),
                            context.getColorByAttr(attr.textColorSecondary)
                    )
            )
    private val popupContext =
            context.withTheme(context.getResourceIdByAttr(R.attr.actionBarPopupTheme))

    private val itemsLayout: LinearLayout

    private lateinit var listener: Listener
    private lateinit var data: BreadcrumbData

    private var isLayoutDirty = true
    private var isScrollToSelectedItemPending = false
    private var isFirstScroll = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(
            context, attrs
    )

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
            context, attrs, defStyleAttr
    )

    constructor(
            context: Context,
            attrs: AttributeSet?,
            @AttrRes defStyleAttr: Int,
            @StyleRes defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        isHorizontalScrollBarEnabled = false
        itemsLayout = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
        itemsLayout.setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
        setPaddingRelative(0, 0, 0, 0)
        addView(itemsLayout, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightMeasureSpec = heightMeasureSpec
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            var height = tabLayoutHeight
            if (heightMode == MeasureSpec.AT_MOST) {
                height = height.coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun requestLayout() {
        isLayoutDirty = true

        super.requestLayout()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        isLayoutDirty = false
        if (isScrollToSelectedItemPending) {
            scrollToSelectedItem()
            isScrollToSelectedItemPending = false
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun setData(data: BreadcrumbData) {
        if (this::data.isInitialized && this.data == data) {
            return
        }
        this.data = data
        inflateItemViews()
        bindItemViews()
        scrollToSelectedItem()
    }

    private fun scrollToSelectedItem() {
        if (isLayoutDirty) {
            isScrollToSelectedItemPending = true
            return
        }
        val selectedItemView = itemsLayout.getChildAt(data.selectedIndex)
        val scrollX = if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            selectedItemView.left - itemsLayout.paddingStart
        } else {
            selectedItemView.right - width + itemsLayout.paddingStart
        }
        if (!isFirstScroll && isShown) {
            smoothScrollTo(scrollX, 0)
        } else {
            scrollTo(scrollX, 0)
        }
        isFirstScroll = false
    }

    private fun inflateItemViews() {
        // HACK: Remove/add views at the front so that ripple remains correct, as we are potentially
        // collapsing/expanding breadcrumbs at the front.
        for (index in data.paths.size until itemsLayout.childCount) {
            itemsLayout.removeViewAt(0)
        }
        for (index in itemsLayout.childCount until data.paths.size) {
            val binding = BreadcrumbItemBinding.inflate(context.layoutInflater, itemsLayout, false)
//            val menu = PopupMenu(popupContext, binding.root)
//                    .apply { inflate(R.menu.file_list_breadcrumb) }
            binding.root.setOnLongClickListener {
//                menu.show()
                true
            }
            binding.textText.setTextColor(itemColor)
            binding.arrowImage.imageTintList = itemColor
//            binding.root.tag = binding to menu
            itemsLayout.addView(binding.root, 0)
        }
    }

    private fun bindItemViews() {
        for (index in data.paths.indices) {
            @Suppress("UNCHECKED_CAST")
            val tag = itemsLayout.getChildAt(index).tag as Pair<BreadcrumbItemBinding, PopupMenu>
            val (binding, menu) = tag
            binding.textText.text = data.nameProducers[index](binding.textText.context)
            binding.arrowImage.isVisible = index != data.paths.size - 1
            binding.root.isActivated = index == data.selectedIndex
            val path = data.paths[index]
            binding.root.setOnClickListener {
                if (data.selectedIndex == index) {
                    scrollToSelectedItem()
                } else {
                    listener.navigateTo(path)
                }
            }

        }
    }

    interface Listener {
        fun navigateTo(path: Path)
        fun copyPath(path: Path)
        fun openInNewTask(path: Path)
    }
}
