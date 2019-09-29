package com.kotlinnews.demo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kotlinnews.demo.ListNews
import com.kotlinnews.demo.R
import com.kotlinnews.demo.interfaces.ItemClickListener
import com.kotlinnews.demo.model.Website
import kotlinx.android.synthetic.main.source_news_layout.view.*

class ListSourceAdapter(private val mContext: Context, private val website: Website) :
    RecyclerView.Adapter<ListSourceAdapter.ListSourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        return ListSourceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.source_news_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return website.sources.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        holder.sourceTitle.text = website.sources.get(position).name

        holder.setItemClickListner(object : ItemClickListener {

            override fun onClick(view: View, pos: Int) {
                super.onClick(view, pos)

                val intent = Intent(mContext, ListNews::class.java)
                intent.putExtra("source", website.sources[pos].id.toString())
                mContext.startActivity(intent)
            }
        })
    }


    inner class ListSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var itemClickListener: ItemClickListener

        var sourceTitle = itemView.textView_source_news

        init {
            itemView.setOnClickListener(this)
        }

        fun setItemClickListner(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(view: View?) {
            itemClickListener.onClick(view!!, adapterPosition)
        }

    }
}