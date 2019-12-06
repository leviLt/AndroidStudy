package com.levi.magicindicator

import android.content.Context
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class MainActivity : AppCompatActivity() {
    val titles = arrayOf("即将开赛 123", "今日 66", "早盘 98")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val titleView = TitleView(this@MainActivity)
                titleView.normalColor = ContextCompat.getColor(this@MainActivity, R.color.colorAccent)
                titleView.selectedColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
                titleView.text = titles[index]
                titleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return titleView
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                var indicator = LinePagerIndicator(this@MainActivity)
                indicator.lineWidth = 20.toPX(this@MainActivity).toFloat()
                indicator.lineHeight = 1.toPX(this@MainActivity).toFloat()
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                return indicator
            }
        }
        tabLayout.navigator = commonNavigator
        viewPager.adapter = FragmentAdapter(supportFragmentManager)
        ViewPagerHelper.bind(tabLayout, viewPager)

        val fragmentContainerHelper = FragmentContainerHelper(tabLayout)
        fragmentContainerHelper.setInterpolator(OvershootInterpolator(2.0f))
        fragmentContainerHelper.setDuration(300)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                fragmentContainerHelper.handlePageSelected(position)
            }
        })

    }

    inner class FragmentAdapter constructor(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return MagicindicatorFragment.newInstance(titles[position])
        }

        override fun getCount(): Int {
            return titles.size
        }

    }

    inner class TitleView(context: Context) : SimplePagerTitleView(context) {
        override fun onSelected(index: Int, totalCount: Int) {
            super.onSelected(index, totalCount)
            setBackgroundResource(R.drawable.select_bg)
        }

        override fun onDeselected(index: Int, totalCount: Int) {
            super.onDeselected(index, totalCount)
            setBackgroundResource(R.drawable.no_select_bg)
        }
    }
}
