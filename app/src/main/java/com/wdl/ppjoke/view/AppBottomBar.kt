package com.wdl.ppjoke.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.wdl.ppjoke.R
import com.wdl.ppjoke.utils.AppConfig

@SuppressLint("Range", "RestrictedApi", "unused")
class AppBottomBar constructor(
    context: Context, attrs: AttributeSet, defStyleAttr: Int
) : BottomNavigationView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    companion object {
        private val sIcons = intArrayOf(
            R.drawable.icon_tab_home,
            R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find,
            R.drawable.icon_tab_mine
        )
    }

    init {
        val bottomBar = AppConfig.getBottomBarConfig()
        bottomBar?.apply {
            val states = arrayOfNulls<IntArray>(2)
            states[0] = intArrayOf(android.R.attr.state_selected)
            states[1] = intArrayOf()
            val colors = intArrayOf(
                Color.parseColor(activeColor),
                Color.parseColor(inActiveColor)
            )
            val colorStateList = ColorStateList(states, colors)

            itemTextColor = colorStateList
            itemIconTintList = colorStateList
            //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
            //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
            //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
            //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
            labelVisibilityMode = LABEL_VISIBILITY_LABELED
            selectedItemId = selectTab

            // 添加
            for (tab in tabs.filter { it.enable }) {
                val id = getTabId(tab.pageUrl)
                if (id < 0) continue
                val menuItem: MenuItem = menu.add(0, id, tab.index, tab.title)
                menuItem.setIcon(sIcons[tab.index])
            }

            tabs.filter { it.enable }.forEachIndexed { index, tab ->
                val menuView = getChildAt(0) as BottomNavigationMenuView
                val menuItemView = menuView.getChildAt(index) as BottomNavigationItemView

                menuItemView.setIconSize(dp2Px(tab.size))

                // 中间发布按钮
                if (tab.title.isEmpty()) {
                    // 设置着色
                    menuItemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.tintColor)))
                    // 去除点击浮动效果
                    menuItemView.setShifting(false)
                }
            }
        }
    }

    private fun getTabId(pageUrl: String): Int {
        val destinations = AppConfig.getDestinationConfig()
        if (destinations.isNullOrEmpty()) return -1
        return destinations[pageUrl]?.id ?: -1
    }

    private fun dp2Px(dpValue: Int): Int {
        val metrics = context.resources.displayMetrics
        return (metrics.density * dpValue + 0.5f).toInt()
    }


}
