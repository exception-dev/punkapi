package com.ex.punkapi.main.ui

import android.content.Intent
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
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.style.ForegroundColorSpan
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main_left_item.view.*
import kotlinx.android.synthetic.main.activity_main_item.view.*
import android.widget.ImageView
import com.ex.punkapi.dialog.ui.FilterDialog
import com.ex.punkapi.main.ui.filter.FilterUtil
import com.ex.punkapi.manager.callback.OnPurchaseCompleteListener
import com.ex.punkapi.util.AlertUtils
import com.ex.punkapi.util.Utils
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main_right_item.view.*


class MainActivity : BaseActivity(), OnPurchaseCompleteListener {

    private var page = 1
    private var hasData = false

    private lateinit var adapter: ListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private val dataList = mutableListOf<BeerModel>()


    private lateinit var realm: Realm

    private val MSG_FINISH = 1
    private val DELAY:Long = 3000

    private var isFinishFlag = false
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_FINISH ->

                    isFinishFlag = false

            }
        }
    }

    private var filterDialog: FilterDialog? = null
    private var abv = FilterUtil.ABV_FILTER.NONE
    private var ibu = FilterUtil.IBU_FILTER.NONE
    private var ebc = FilterUtil.EBC_FILTER.NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        init()
        regEvent()
        initData()
    }

	override fun onBackPressed() {


		if (!isFinishFlag) {
			isFinishFlag = true;
            AlertUtils.toastShort(this, getString(R.string.msg_finish))
			handler.sendEmptyMessageDelayed(MSG_FINISH, DELAY);
			return;
		}
		super.onBackPressed();
	}

    override fun onDestroy() {
        app.purchaseCompleteManager.removeCallback(this)
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(intent?.hasExtra(Constants.Key.USER_NAME)!!){
            AlertUtils.toastShort(this, intent?.getStringExtra(Constants.Key.USER_NAME)!!)
        }
    }

    private fun init(){
        app.purchaseCompleteManager.addCallback(this)
        realm = app.getRealm()

        layoutManager = LinearLayoutManager(this);
        recyclerView.layoutManager = layoutManager
        adapter = ListAdapter()
        recyclerView.adapter = adapter

//        val minAbv:BeerModel = realm.where(BeerModel::class.java).sort("abv", Sort.ASCENDING).findFirst()!!
//        val maxAbv:BeerModel = realm.where(BeerModel::class.java).sort("abv", Sort.DESCENDING).findFirst()!!
//
//        val minibu:BeerModel = realm.where(BeerModel::class.java).isNotNull("ibu").isNotEmpty("ibu").sort("ibu", Sort.ASCENDING).findFirst()!!
//        val maxibu:BeerModel = realm.where(BeerModel::class.java).sort("ibu", Sort.DESCENDING).findFirst()!!
//
//        val minEbc:BeerModel = realm.where(BeerModel::class.java).isNotNull("ebc").isNotEmpty("ebc").sort("ebc", Sort.ASCENDING).findFirst()!!
//        val maxEbc:BeerModel = realm.where(BeerModel::class.java).sort("ebc", Sort.DESCENDING).findFirst()!!
//
//        println("minAbv.abv : ${minAbv.abv}")
//        println("maxAbv.abv : ${maxAbv.abv}")
//
//        println("minibu.ibu : ${minibu.ibu}")
//        println("maxibu.ibu : ${maxibu.ibu}")
//
//        println("minEbc.ebc : ${minEbc.ebc}")
//        println("maxEbc.ebc : ${maxEbc.ebc}")

    }


    private fun initData() {
        page = 1
        getData()
    }

    private fun getData() {

        val parameter = mutableMapOf<String, Any>()
        parameter.put("page", page)
        parameter.put("per_page", Constants.PER_PAGE)

        if(abv != FilterUtil.ABV_FILTER.NONE){
            parameter.putAll(FilterUtil.getAbvParam(abv))
        }

        if(ibu != FilterUtil.IBU_FILTER.NONE){
            parameter.putAll(FilterUtil.getIbuParam(ibu))
        }

        if(ebc != FilterUtil.EBC_FILTER.NONE){
            parameter.putAll(FilterUtil.getEbcParam(ebc))
        }

        requestCall(apiService.beers(parameter),  object : NetWorkCallback<MutableList<BeerModel>>(this)   {
            override fun onSuccess(call: Call<MutableList<BeerModel>>, response: Response<MutableList<BeerModel>>, data: MutableList<BeerModel>) {

                realm.beginTransaction()

                realm.insertOrUpdate(data)
                realm.commitTransaction()



                if(page == 1){
                    dataList.clear();
                }
                hasData = data.size >= Constants.PER_PAGE
                dataList.addAll(data)
                adapter.notifyDataSetChanged()

                if(page == 1){
                    layoutManager.scrollToPosition(0)
                }



            }

            override fun onComplete(call: Call<MutableList<BeerModel>>, response: Response<MutableList<BeerModel>>) {
                swipeRefreshLayout.isRefreshing = false
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

        swipeRefreshLayout.setOnRefreshListener { initData() }

        btnFilter.setOnClickListener{
            showFilter()
        }
    }

    private fun showFilter(){

        if(filterDialog == null){
            filterDialog = FilterDialog(this, btnFilter)
            filterDialog!!.setOnFilterApplyListener(object : FilterDialog.OnFilterApplyListener {
                override fun onApply(abv: FilterUtil.ABV_FILTER, ibu: FilterUtil.IBU_FILTER, ebc: FilterUtil.EBC_FILTER) {
                    this@MainActivity.abv = abv
                    this@MainActivity.ibu = ibu
                    this@MainActivity.ebc = ebc

                    initData()
                }
            })
        }


        if(!filterDialog!!.isShowing){
            filterDialog!!.show()
        }

    }



    inner class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val VIEW_TYPE_FIRST = 0
        private val VIEW_TYPE_SECOND = 1
        private val VIEW_TYPE_THIRD = 2

        inner class InfoImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            val img = v.findViewById<ImageView>(R.id.img)
            val name = v.findViewById<TextView>(R.id.name)

            val info = v.info
            val description = v.findViewById<TextView>(R.id.description)

        }

        inner class TaglineImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            val img = v.findViewById<ImageView>(R.id.img)
            val name = v.findViewById<TextView>(R.id.name)

            val tagline = v.tagline

        }

        inner class TextViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            val name = v.findViewById<TextView>(R.id.name)
            val firstBrewed = v.firstBrewed
            val description = v.findViewById<TextView>(R.id.description)

        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            var v: View?

            when (viewType) {
                VIEW_TYPE_FIRST ->{
                    v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_left_item, parent, false)
                    return InfoImageViewHolder(v)
                }
                VIEW_TYPE_SECOND ->{
                    v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_right_item, parent, false)
                    return TaglineImageViewHolder(v)
                }
                else ->{
                    v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_item, parent, false)
                    return TextViewHolder(v)
                }

            }


        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


            when (getItemViewType(position)) {
                VIEW_TYPE_FIRST -> {
                    bindInfoImageHolder(holder as InfoImageViewHolder, position)
                }

                VIEW_TYPE_SECOND -> {
                    bindTaglineImageHolder(holder as TaglineImageViewHolder, position)
                }

                VIEW_TYPE_THIRD -> {
                    bindTextHolder(holder as TextViewHolder, position)
                }
            }

            holder.itemView.setOnClickListener{
                val data = dataList[position]
                val intent = Intent(this@MainActivity, BeerDetailActivity::class.java)
                intent.putExtra(Constants.Key.BEER_ID, data.id)
                startActivity(intent)
            }



        }

        private fun bindInfoImageHolder(holder: InfoImageViewHolder, position: Int){
            val data = dataList[position]

            GlideApp.with(this@MainActivity)
                .load(data.imageUrl)
                .into(holder.img)

            holder.name.text = data.name

            holder.description.text = data.description

            val builder = SpannableStringBuilder()

            makeSpanText(builder, getString(R.string.abv), data.abv)
            makeSpanText(builder, getString(R.string.ibu), data.ibu)
            makeSpanText(builder, getString(R.string.srm), data.srm)

            holder.info.text = builder
        }

        private fun bindTaglineImageHolder(holder: TaglineImageViewHolder, position: Int){
            val data = dataList[position]

            GlideApp.with(this@MainActivity)
                .load(data.imageUrl)
                .into(holder.img)

            holder.name.text = data.name

            holder.tagline.text = data.tagline
        }

        private fun bindTextHolder(holder: TextViewHolder, position: Int){
            val data = dataList[position]



            holder.name.text = data.name
            holder.firstBrewed.text = Utils.toDateString(data?.firstBrewed, "mm-yyyyy", "yyyy-mm")
            holder.description.text = data.description

        }


        override fun getItemViewType(position: Int):Int {

            //val data = dataList[position]
            //data 의 구분 값이 명확하면 이곳에서 나누나 주류 분류가 명확하지 않아 viewType 관련 과제는 랜덤으로 처리

             when (position % 9) {
                0, 1, 2 -> return VIEW_TYPE_FIRST
                3, 4, 5 ->return VIEW_TYPE_SECOND


                else -> return VIEW_TYPE_THIRD
             }

        }

        private fun makeSpanText(builder: SpannableStringBuilder, label: String, value: String?) : SpannableStringBuilder{
            if (value != null){
                if(builder.isNotEmpty()){
                    builder.append("\n")
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


    override fun onComplete(name: String) {
        AlertUtils.snackbar(recyclerView, name)
    }

}
