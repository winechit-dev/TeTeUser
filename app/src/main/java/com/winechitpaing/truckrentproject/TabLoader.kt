package com.winechitpaing.truckrentproject

import com.google.android.material.tabs.TabLayout
import com.winechitpaing.truckrentproject.data.Category

object TabLoader {

    fun loadTabs(tabLayout: TabLayout, tabItems: List<Category>) {
        tabItems.forEach { tabData ->
            tabLayout.newTab().also { tab ->
                tab.text = tabData.categoryName
                tabLayout.addTab(tab)
            }
        }
    }
}