package com.green.mines.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.dong.eleven.customnavigation.R
import kotlinx.android.synthetic.main.layout_my_navigation.view.*

class MyNavigation : LinearLayout {

    var items: MutableList<MyItem> = ArrayList<MyItem>()

    var default_text_color: Int
    var pre_text_color: Int
    var text_size: Int
//    var default_color: Int
//    var pre_color: Int

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.myNavigation)
        default_text_color = attributes.getColor(R.styleable.myNavigation_default_text_color, Color.BLACK)
        pre_text_color = attributes.getColor(R.styleable.myNavigation_pre_text_color, Color.DKGRAY)
//        default_color = attributes.getColor(R.styleable.myNavigation_default_color, Color.GRAY)
//        pre_color = attributes.getColor(R.styleable.myNavigation_pre_color, Color.BLUE)
        text_size = attributes.getInteger(R.styleable.myNavigation_text_size, 12)
        attributes.recycle()
    }

    private fun initView() {
        if (items.size > 0) {
            for ((index, item) in items.withIndex()) {
                var rootView = View.inflate(context, R.layout.layout_my_navigation, null) as LinearLayout
                rootView.imgIv.setImageResource(item.srcId)
                rootView.nameTv.text = item.text
                rootView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f)
                rootView.setOnClickListener {
                    if (onNavigationListener != null) {
                        onNavigationListener!!.onClick(index)
                    }
                    setCurrentItem(index)
                }
                addView(rootView)
            }
        }
        setCurrentItem(0)
        invalidate()
    }

    fun addItem(srcId: Int, srcPreId: Int, text: String) {
        var myItem = MyItem()
        myItem.srcId = srcId
        myItem.srcPreId = srcPreId
        myItem.text = text
        items.add(myItem)
    }

    fun initItem() {
        initView()
    }

    inner class MyItem {
        var srcId: Int = 0
        var srcPreId: Int = 0
        var text: String? = null
    }

    fun setOnNavigationListener(onNavigationListener: OnNavigationListener) {
        this.onNavigationListener = onNavigationListener
    }

    fun setCurrentItem(position: Int) {
        for ((index, item) in items.withIndex()) {
            if (position == index) {
                var view = getChildAt(index) as LinearLayout
//                view.img_iv.setColorFilter(pre_color)
                view.imgIv.setImageResource(item.srcPreId)
                view.nameTv.setTextColor(pre_text_color)
            } else {
                var view = getChildAt(index) as LinearLayout
                view.imgIv.setImageResource(item.srcId)
                view.nameTv.setTextColor(default_text_color)
            }
        }
//        invalidate()
    }

    private var onNavigationListener: OnNavigationListener? = null

    interface OnNavigationListener {
        fun onClick(position: Int)
    }
}