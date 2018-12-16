package com.example.reckon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

open class PageAdapter//Declare a secondary constructor that takes in the array of layout and its context
(var layouts: IntArray, var context: Context) : PagerAdapter() {
    var  inflater : LayoutInflater

    //Constructor Body
    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view : View = inflater.inflate(layouts[position], container, false)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view : View = `object` as View
        container.removeView(view)
    }
}