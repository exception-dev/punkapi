package com.ex.punkapi.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.AbsListView


class RefreshRecyclerView : RecyclerView {
    private var refreshCallback: OnRefreshListener? = null

    var isBottom = false
    var firstVisibleItem: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var beforeTotalCount: Int = 0
    var beforeSendTime: Long = 0

    val TIME: Int = 500

    constructor(context: Context) : super(context) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isBottom) {

                    val now = System.currentTimeMillis()

                    if(beforeTotalCount == layoutManager!!.itemCount &&
                            Math.abs(now - beforeSendTime) < TIME){
                        return
                    }

                    refreshCallback?.onRefresh()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {

                    if (layoutManager is LinearLayoutManager) {
                        visibleItemCount = childCount
                        totalItemCount = layoutManager!!.itemCount
                        firstVisibleItem =
                                (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                        isBottom = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount
                    }

                }
            }
        })
    }


    fun setOnRefreshListener(refreshCallback: OnRefreshListener) {
        this.refreshCallback = refreshCallback
    }

    interface OnRefreshListener {
        fun onRefresh()
    }

}