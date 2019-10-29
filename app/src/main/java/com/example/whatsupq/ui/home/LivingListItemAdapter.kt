package com.example.whatsupq.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.whatsupq.LivingItemInfoActivity
import com.example.whatsupq.R
import java.text.DecimalFormat

class LivingItem(var index : Int, var id : String , var livingImg: String, var livingBrand: String, var livingName: String,var livingCharge: String,var livingBeforeCharge: String)

class LivingListItemAdapter(val context: LivingListPlaceholderFragment, val itemList:ArrayList<LivingItem>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val format = DecimalFormat("###,###")
        val view: View = LayoutInflater.from(context.context).inflate(R.layout.fragment_home_living_list_item, null)

        val livingImageView = view.findViewById<ImageView>(R.id.item_image)
        val livingBrand = view.findViewById<TextView>(R.id.brand_name)
        val livingItemName = view.findViewById<TextView>(R.id.item_name)
        val livingCharge = view.findViewById<TextView>(R.id.charge)
        val livingBeforeCharge = view.findViewById<TextView>(R.id.beforecharge)
        val livingItem = itemList[p0]

        livingItem.id = itemList[p0].id
        Glide.with(this.context).load(itemList[p0].livingImg).into(livingImageView)
        livingImageView.scaleType = ImageView.ScaleType.FIT_XY
        livingBrand.text = livingItem.livingBrand
        if(livingItem.livingName.length >= 30) {
            livingItemName.text = livingItem.livingName.substring(0,21) + "..."
        } else {
            livingItemName.text = livingItem.livingName
        }
        livingCharge.text = format.format(livingItem.livingCharge.toInt()) + "원"
        livingBeforeCharge.text = format.format(livingItem.livingBeforeCharge.toInt()) + "원"
        view.setOnClickListener {
            var intent = Intent(context.context, LivingItemInfoActivity::class.java)
            intent.putExtra("product_id",livingItem.id)
            context.startActivity(intent)
        }
        return view
    }
    override fun getItem(p0: Int): Any {
       return itemList[p0]
    }

    override fun getItemId(p0: Int): Long {
       return 0
    }

    override fun getCount(): Int {
        return itemList.size
    }
}
