package com.ex.punkapi.main.ui

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
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList
import android.text.Spannable
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.style.ForegroundColorSpan
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main_left_item.view.*
import kotlinx.android.synthetic.main.activity_main_item.view.*



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

    inner class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val VIEW_TYPE_FIRST = 0
        private val VIEW_TYPE_SECOND = 1
        private val VIEW_TYPE_THIRD = 2

        inner class ImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            val img = v.img
            val name = v.findViewById<TextView>(R.id.name)
            val tagline = v.findViewById<TextView>(R.id.tagline)

            val info = v.info
            val description = v.findViewById<TextView>(R.id.description)

        }

        inner class TextViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            val name = v.findViewById<TextView>(R.id.name)
            val firstBrewed = v.firstBrewed
            val description = v.findViewById<TextView>(R.id.description)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            println("**************onCreateViewHolder***************")
            println("viewType : $viewType")

            var v: View? = null

            when (viewType) {
                VIEW_TYPE_FIRST ->{
                    v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_left_item, parent, false)
                    return ImageViewHolder(v)
                }
                VIEW_TYPE_SECOND ->{
                    v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_right_item, parent, false)
                    return ImageViewHolder(v)
                }
                else ->{
                    v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_item, parent, false)
                    return TextViewHolder(v)
                }

            }


        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


            when (getItemViewType(position)) {
                VIEW_TYPE_FIRST, VIEW_TYPE_SECOND -> {
                    bindImageHolder(holder as ImageViewHolder, position)
                }

                VIEW_TYPE_THIRD -> {
                    bindTextHolder(holder as TextViewHolder, position)
                }
            }



        }

        private fun bindImageHolder(holder: ImageViewHolder, position: Int){
            val data = dataList[position]

            GlideApp.with(this@MainActivity)
                .load(data.imageUrl)
                .centerCrop()
                .into(holder.img)

            holder.name.text = data.name
            holder.tagline.text = data.tagline

            holder.description.text = data.description

            val builder = SpannableStringBuilder()

            makeSpanText(builder, getString(R.string.abv), data.abv)
            makeSpanText(builder, getString(R.string.ibu), data.ibu)
            makeSpanText(builder, getString(R.string.srm), data.srm)
            makeSpanText(builder, getString(R.string.ebc), data.ebc)
            makeSpanText(builder, getString(R.string.ph), data.ph)

            holder.info.text = builder
        }

        private fun bindTextHolder(holder: TextViewHolder, position: Int){
            val data = dataList[position]



            holder.name.text = data.name
            holder.firstBrewed.text = data.firstBrewed
            holder.description.text = data.description

        }


        override fun getItemViewType(position: Int):Int {

            val data = dataList[position]

            //data 의 구분 값이 명확하면 이곳에서 나누나 주류 분류가 명확하지 않아 viewType 관련 과제는 랜덤으로 처리

             when (position % 9) {
                0, 1, 2 -> return VIEW_TYPE_FIRST
                3, 4, 5 ->return VIEW_TYPE_SECOND


                else -> return VIEW_TYPE_THIRD
             }

        }

        private fun makeSpanText(builder: SpannableStringBuilder, label: String, value: Number?) : SpannableStringBuilder{
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
