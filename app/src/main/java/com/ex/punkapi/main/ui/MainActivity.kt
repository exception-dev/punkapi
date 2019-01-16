package com.ex.punkapi.main.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ex.punkapi.R
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


            val test = v.test
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {


            val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_item, parent, false)
            return ViewHolder(v, viewType)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = dataList.get(position)

            val requestOptions = RequestOptions().transforms(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.round_corners)))


            holder.test.text = data.toString()


//            GlideApp.with(this@PlayerActivity)
//                .load(data.img)
//                .apply(requestOptions)
//                .into(holder.imgPlayer)
//
//            holder.txtName.text = data.name
//            holder.txtTeam.text = data.team
//            holder.txtNation.text = data.nation
//            GlideApp.with(this@PlayerActivity)
//                .load(data.nationLogo)
//                .into(holder.imgNationLogo)
//
//            holder.txtFavorite.isSelected = data.isFavorite
//            holder.txtFavorite.text = data.favorite.toString()
//            holder.txtReply.text = data.reply.toString()
//
//            holder.layoutContent.setOnClickListener{
//
//                var intent = Intent(this@PlayerActivity, PlayerDetailActivity::class.java)
//                intent.putExtra(PLAYER_ID, data.playerId)
//                startActivity(intent)
//            }
        }


        override fun getItemCount(): Int {
            return dataList.size
        }
    }


}
