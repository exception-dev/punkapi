package com.ex.punkapi.dialog.ui

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.ex.punkapi.R
import com.ex.punkapi.main.ui.filter.FilterUtil
import kotlinx.android.synthetic.main.dialog_filter.*


class FilterDialog(context: Context, private val anchorView: View) : BaseDialog(context) {

    var filterListener:OnFilterApplyListener? = null

    init {

        setContentView(R.layout.dialog_filter)

        init()
        regEvent()

    }


    private fun init() {
        var params: WindowManager.LayoutParams = window.attributes


        params.gravity = Gravity.LEFT or Gravity.TOP

        var location = IntArray(2)
        anchorView.getLocationOnScreen(location)

        params.x = location[0] - (context.resources.getDimensionPixelSize(R.dimen.filter_dialog_width) - (anchorView.width / 2) - anchorView.paddingEnd  )
        params.y = location[1] + anchorView.height / 2

        window.attributes = params
    }

    private fun regEvent(){

        btnClose.setOnClickListener {
            dismiss()
        }

        btnApply.setOnClickListener { apply () }
    }

    private fun getAbv():FilterUtil.ABV_FILTER{
        return when(rgAbv.checkedRadioButtonId){
            R.id.rbAbvWeakly ->
                return FilterUtil.ABV_FILTER.WEAKLY
            R.id.rbAbvNomal ->
                return FilterUtil.ABV_FILTER.NORMAL
            R.id.rbAbvStrong ->
                return FilterUtil.ABV_FILTER.STRONGLY
            else ->
                return FilterUtil.ABV_FILTER.NONE
        }
    }

    private fun getIbu():FilterUtil.IBU_FILTER{
        return when(rgIbu.checkedRadioButtonId){
            R.id.rbIbuWeakly ->
                return FilterUtil.IBU_FILTER.WEAKLY
            R.id.rbIbuNomal ->
                return FilterUtil.IBU_FILTER.NORMAL
            R.id.rbIbuStrong ->
                return FilterUtil.IBU_FILTER.STRONGLY
            else ->
                return FilterUtil.IBU_FILTER.NONE
        }
    }


    private fun getEbc():FilterUtil.EBC_FILTER{
        return when(rgAbv.checkedRadioButtonId){
            R.id.rbEbcLight->
                return FilterUtil.EBC_FILTER.LIGHTLY
            R.id.rbEbcNomal ->
                return FilterUtil.EBC_FILTER.NORMAL
            R.id.rbEbcThick ->
                return FilterUtil.EBC_FILTER.THICK
            else ->
                return FilterUtil.EBC_FILTER.NONE
        }
    }

    private fun apply(){
        filterListener?.onApply(getAbv(), getIbu(), getEbc())
        dismiss()
    }


    fun setOnFilterApplyListener(listener: OnFilterApplyListener ){
        this.filterListener = listener
    }

    interface OnFilterApplyListener {
        fun onApply(abv: FilterUtil.ABV_FILTER, ibu:FilterUtil.IBU_FILTER, ebc:FilterUtil.EBC_FILTER)
    }


}