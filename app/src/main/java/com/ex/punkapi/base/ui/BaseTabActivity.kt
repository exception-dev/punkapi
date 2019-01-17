package com.ex.punkapi.base.ui

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.view.ViewGroup
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ex.punkapi.R
import kotlinx.android.synthetic.main.activity_beer_detail.*
import kotlinx.android.synthetic.main.tab_indicator.*
import kotlinx.android.synthetic.main.tab_indicator.view.*


open class BaseTabActivity : BaseActivity() {


    protected lateinit var pagerAdapter: ViewPagerFragmentAdapter


    protected open fun regPagerEvent() {

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                hideKeyBoard(true)
                pageScrolled(position)
            }

            override fun onPageSelected(position: Int) {
                hideKeyBoard(false)
                pageChanged(position)
                changeIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                hideKeyBoard(true)
            }
        })


        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                pageSelected(position)
                viewPager!!.setCurrentItem(position, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    protected fun pageChanged(position: Int) {}

    protected fun pageSelected(position: Int) {}

    protected fun pageScrolled(position: Int) {}

    protected fun changeIndicator(position: Int) {

        for (i in 0 until pagerAdapter.count) {
            val viewGroup = tabLayout!!.getTabAt(i)!!.customView
            val textView = viewGroup!!.text

            if (i == position) {
                textView.typeface = Typeface.DEFAULT_BOLD
            } else {
                textView.typeface = Typeface.DEFAULT
            }
        }
    }

    private fun hideKeyBoard(isRunnable: Boolean) {
        if (isRunnable) {
            viewPager!!.post { hideKeyBoard(false) }
        } else {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                viewPager!!.windowToken,
                0
            )
        }
    }



    protected open fun initTab() {

        pagerAdapter = ViewPagerFragmentAdapter(this)
        viewPager!!.adapter = pagerAdapter

        regPagerEvent()
    }


    protected fun makeMenuTab(
        view: View,
        tabID: Class<*>,
        bundle: Bundle?,
        activityCallbackListener: OnActivityCallbackListener?
    ) {
        val tab = tabLayout!!.newTab()
        tab.customView = view
        tabLayout!!.addTab(tab)
        pagerAdapter.addPage(tabID, bundle, activityCallbackListener)

        if (tabLayout!!.childCount == 1) {
            changeIndicator(0)
        }

    }


    protected fun getTabView(label: String): View {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val view = inflater!!.inflate(R.layout.tab_indicator, tabLayout, false)

        view.text.text = label

        return view
    }


    inner class ViewPagerFragmentAdapter(private val activity: FragmentActivity) :
        FragmentStatePagerAdapter(getSupportFragmentManager()) {

        private val fragmentMap:HashMap<String, Fragment> = HashMap()
        private val pageInfoList:ArrayList<PageInfo> = ArrayList()


        fun addPage(clss: Class<*>, bundle: Bundle?, activityCallbackListener: OnActivityCallbackListener?) {
            val info = PageInfo(clss, bundle, activityCallbackListener)
            pageInfoList.add(info)
            notifyDataSetChanged()
        }


        override fun getItem(position: Int): Fragment {

            val info = pageInfoList.get(position)

            val key = info.clss.getName() + String.format("%02d", position)


            println("key : " + key)

            if (fragmentMap.get(key) == null) {

                val m = Fragment.instantiate(activity, info.clss.getName(), info.args) as BaseFragment

                if (info.activityCallbackListener != null) {
                    m.setOnCallbackListener(info.activityCallbackListener)
                }

                fragmentMap.put(key, m)
            }


            return fragmentMap.get(key)!!
        }


        override fun getCount(): Int {
            return pageInfoList.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }


        override fun saveState(): Parcelable? {
            try {
                return super.saveState()
            } catch (e: Exception) {
                return null
            }

        }

        internal inner class PageInfo(
            val clss: Class<*>,
            val args: Bundle?,
            val activityCallbackListener: OnActivityCallbackListener?
        )
    }

    interface OnActivityCallbackListener {
        fun callback(`object`: Any)
    }

}