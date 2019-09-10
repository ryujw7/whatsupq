package com.example.whatsupq.ui.curation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.LivingItemInfoActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_curation_result_prodinfo.view.*

class CurationResultAdapter(val itemList: ArrayList<ProdInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_curation_result_prodinfo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        (holder as ViewHolder).apply{
            bind(item)
            holder.view.setOnClickListener {
                val intent = Intent(view.context, LivingItemInfoActivity::class.java)
                intent.putExtra("product_id", itemList[position].product_id)
                view.context.startActivity(intent)
            }
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val view = v
        fun bind(item : ProdInfo) {
            view.curation_result_prod_img.setImageBitmap(item.imgSrc)
            view.curation_result_prod_name.text = item.prodName
            view.curation_result_prod_cost.text = item.cost
        }

    }
}
