package com.winechitpaing.truckrentproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.winechitpaing.tabscrollattacherlib.TabScrollAttacher
import com.winechitpaing.truckrentproject.R
import com.winechitpaing.truckrentproject.TabLoader
import com.winechitpaing.truckrentproject.adapter.ItemListAdapter
import com.winechitpaing.truckrentproject.data.Category
import com.winechitpaing.truckrentproject.data.DataFetcher
import com.winechitpaing.truckrentproject.data.Item
import kotlinx.android.synthetic.main.fragment_near_by_me.*

class NearByMeFragment : Fragment() {

    private lateinit var nearByMeViewModel: NearByMeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        nearByMeViewModel =
                ViewModelProviders.of(this).get(NearByMeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_near_by_me, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val categories = DataFetcher.fetchData(requireContext())

        /**
         * load recyclerview adapter
         */
        val adapter = ItemListAdapter()
        adapter.setItems(getAllItems(categories))
        recyclerView.adapter = adapter

        /**
         * Load tabs
         */
        TabLoader.loadTabs(tabLayout, categories)

        /**
         * SETUP ATTACHER
         */
        val indexOffsets = getCategoryIndexOffsets(categories)
        val attacher = TabScrollAttacher(tabLayout, recyclerView, indexOffsets) {
            scrollSmoothly()
        }

        attacher.attach()
    }

    /**
     * Calculate your index offset list.
     * Attacher will talk to recyclerview and tablayout
     * with offsets and indexes.
     */
    private fun getCategoryIndexOffsets(categories: List<Category>): List<Int> {
        val indexOffsetList = arrayListOf<Int>()
        categories.forEach { categoryItem ->
            if (indexOffsetList.isEmpty()) {
                indexOffsetList.add(0)
            } else {
                indexOffsetList.add(indexOffsetList.last() + categoryItem.itemList.size)
            }
        }
        return indexOffsetList
    }

    private fun getAllItems(categories: List<Category>): List<Item> {
        val items = arrayListOf<Item>()
        categories.forEach { items.addAll(it.itemList) }
        return items
    }
}