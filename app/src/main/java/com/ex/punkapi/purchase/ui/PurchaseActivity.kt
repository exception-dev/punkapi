package com.ex.punkapi.purchase.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ex.punkapi.R
import com.ex.punkapi.base.glide.GlideApp
import com.ex.punkapi.base.ui.BaseActivity
import com.ex.punkapi.common.Constants
import com.ex.punkapi.main.ui.MainActivity
import com.ex.punkapi.model.BeerModel
import com.ex.punkapi.util.AlertUtils
import com.ex.punkapi.util.Utils
import kotlinx.android.synthetic.main.activity_purchase.*



class PurchaseActivity : BaseActivity() {

    private var beerId:Long = 0
    private var beerModel: BeerModel? = null

    val price:Long = 10000
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        getIntentData()
        init()
        regEvent()
    }

    private fun getIntentData(){
        beerId = intent.getLongExtra(Constants.Key.BEER_ID, 0);

    }

    private fun init() {
        val realm = app.getRealm()
        beerModel = realm.where(BeerModel::class.java).equalTo("id", beerId).findFirst()

        if(beerModel == null){
            finish()
        }

        bindData()
        calculate(0)
    }

    var addCountListener = View.OnClickListener{
        view ->

        var add = view.tag.toString().toInt()

        calculate(add)


    }

    private fun regEvent() {
        btnMinus.setOnClickListener(addCountListener)
        btnPlus.setOnClickListener(addCountListener)

        btnPurchase.setOnClickListener {
            checkPurchase()
        }
    }

    private fun bindData(){
        txtTitle.text = beerModel?.name

        GlideApp.with(this)
            .load(beerModel?.imageUrl)
            .into(imgBeer)

        txtPrice.text = Utils.getCurrency(price)
    }

    private fun calculate(add: Int){
        if(count + add < Constants.MIN_COUNT || count + add > Constants.MAX_COUNT){
            return
        }
        count += add
        txtCount.text = count.toString()
        txtTotPrice.text = Utils.getCurrency(price * count)
    }

    private fun checkPurchase() {
        if(editTextUser.text.isEmpty()){
            AlertUtils.toastShort(this, getString(R.string.msg_input_name))
            editTextUser.requestFocus()
            return
        }

        AlertUtils.showConfirm(this, getString(R.string.quest_purchase),
            DialogInterface.OnClickListener { dialog, which -> purchase() }, null
        )
    }

    private fun purchase() {
        AlertUtils.showAlert(this, getString(R.string.msg_complete_purchase),
            DialogInterface.OnClickListener { dialog, which -> complete() })
    }

    private fun complete(){
        /**
         * main 으로 넘어갔을때 이름이 toast로 떠야하는게 어느 구현방법을 보기위함인지 몰라
         * onNewIntent 와 callback 등록이용 2가지 방식으로 구현
         * onNewIntent는 toast, callback 이용은 snackbar
         */

        val name = editTextUser.text.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(Constants.Key.USER_NAME, name)
        startActivity(intent)
        finish()

        app.purchaseCompleteManager.purcharseComplete(name)
    }
}
