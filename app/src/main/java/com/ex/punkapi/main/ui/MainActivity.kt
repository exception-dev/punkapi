package com.ex.punkapi.main.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ex.punkapi.R
import com.ex.punkapi.base.glide.GlideApp
import com.ex.punkapi.base.ui.BaseActivity
import com.ex.punkapi.common.Constants
import com.ex.punkapi.model.BeerModel
import com.ex.punkapi.network.callback.NetWorkCallback
import com.ex.punkapi.widget.RefreshRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_item.view.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList
import android.text.Spannable
import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan


class MainActivity : BaseActivity() {

    private var page = 1
    private var hasData = false

    private lateinit var adapter: ListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private val dataList = ArrayList<BeerModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        init()
        regEvent()
        initData()
    }

    private fun init(){
        layoutManager = LinearLayoutManager(this);
        recyclerView.layoutManager = layoutManager
        adapter = ListAdapter()
        recyclerView.adapter = adapter
    }


    private fun initData() {
        page = 1
        getData()
    }

    private fun getData() {
        val parameter = HashMap<String, Any>()
        parameter.put("page", page)
        parameter.put("per_page", Constants.PER_PAGE)
        println("***********getData*************")
        requestCall(apiService.beers(parameter),  object : NetWorkCallback<ArrayList<BeerModel>>(this)   {
            override fun onSuccess(call: Call<ArrayList<BeerModel>>, response: Response<ArrayList<BeerModel>>, data: ArrayList<BeerModel>) {

                println("data : $data")

                if(page == 1){
                    dataList.clear();
                }
                hasData = data.size >= Constants.PER_PAGE
                dataList.addAll(data)
                adapter.notifyDataSetChanged()

            }
        })

    }

    private fun moreData() {


        page++
        if (!hasData) {
            return
        }
        getData()
    }


    private fun regEvent() {
        recyclerView.setOnRefreshListener(object : RefreshRecyclerView.OnRefreshListener {
            override fun onRefresh() {

                moreData()
            }
        })


    }

    inner class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
        inner class ViewHolder(v: View, viewType: Int) : RecyclerView.ViewHolder(v) {

            val img = v.img
            val name = v.name
            val tagline = v.tagline
//            val abv = v.abv
//            val ibu = v.ibu
//            val ebc = v.ebc
//            val srm = v.srm
//            val ph = v.ph

            val info = v.info
            val description = v.description
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {


            val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_item, parent, false)
            return ViewHolder(v, viewType)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = dataList.get(position)

            GlideApp.with(this@MainActivity)
                .load(data.imageUrl)
                .centerCrop()
                .into(holder.img)

            holder.name.text = data.name
            holder.tagline.text = data.tagline
//            holder.abv.text = data.abv?.toString()
//            holder.ibu.text = data.ibu?.toString()
//            holder.ebc.text = data.ebc?.toString()
//            holder.srm.text = data.srm?.toString()

            holder.description.text = data.description

            val builder = SpannableStringBuilder()

            makeSpanText(builder, getString(R.string.abv), data.abv)
            makeSpanText(builder, getString(R.string.ibu), data.ibu)
            makeSpanText(builder, getString(R.string.srm), data.srm)
            makeSpanText(builder, getString(R.string.ebc), data.ebc)
            makeSpanText(builder, getString(R.string.ph), data.ph)

            holder.info.text = builder


        }

        private fun makeSpanText(builder: SpannableStringBuilder, label: String, value: Number?) : SpannableStringBuilder{
            //val builder = SpannableStringBuilder()
            if (value != null){
                if(builder.isNotEmpty()){
                    builder.append("\r\n")
                }
                val startIndex = builder.length
                builder.append("$label : $value");
                builder.setSpan(ForegroundColorSpan(ContextCompat.getColor(this@MainActivity, R.color.titleTextColor)), startIndex, startIndex + label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                builder.setSpan(StyleSpan(Typeface.BOLD), startIndex, startIndex + label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return builder
        }


        override fun getItemCount(): Int {
            return dataList.size
        }
    }


}
