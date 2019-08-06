package com.example.whatsupq.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.whatsupq.R

class LivingItem(s: String, s1: String, s2: String, s3: String, s4: String) {
    lateinit var livingImg : String
    lateinit var livingBrand : String
    lateinit var livingName : String
    lateinit var livingCharge : String
    lateinit var livingBeforeCharge : String
}
class LivingListAdapter(val context: LivingListPlaceholderFragment, val itemList:ArrayList<LivingItem>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context.context).inflate(R.layout.fragment_home_living_list_item, null)
        val livingImageView = view.findViewById<ImageButton>(R.id.item_image)
        val livingBrand = view.findViewById<TextView>(R.id.brand_name)
        val livingItemName = view.findViewById<TextView>(R.id.item_name)
        val livingCharge = view.findViewById<TextView>(R.id.charge)
        val livingBeforeCharge = view.findViewById<TextView>(R.id.beforecharge)

        val livingItem = itemList[p0]
        val resourceId = context.resources.getIdentifier(livingItem.livingImg,"drawable","com.example.whatsupq")
        livingImageView.setImageResource(resourceId)
        livingBrand.text = livingItem.livingBrand
        livingItemName.text = livingItem.livingName
        livingCharge.text = livingItem.livingCharge
        livingBeforeCharge.text = livingItem.livingBeforeCharge
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
