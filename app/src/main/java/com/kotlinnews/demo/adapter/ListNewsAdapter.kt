package com.kotlinnews.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinnews.demo.R
import com.kotlinnews.demo.common.GlideApp
import com.kotlinnews.demo.common.ISO8601Parser

import com.kotlinnews.demo.model.Article
import kotlinx.android.synthetic.main.news_layout.view.*
import java.lang.Exception
import java.util.*

class ListNewsAdapter(private val articleList: MutableList<Article>, private val context: Context) :
    RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        return ListNewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {

        GlideApp.with(context)
            .load(articleList.get(position).urlToImage)
            .placeholder(R.drawable.ic_launcher_background)
            .fitCenter()
            .into(holder.article_image);

        if (articleList.get(position).title.length > 65) {
            holder.article_title.text = articleList.get(position).title.substring(0, 65) + "..."
        } else {
            holder.article_title.text = articleList.get(position).title
        }

        if (articleList.get(position).publishedAt != null) {

            var date: Date? = null
            try {
                date = ISO8601Parser.parse(articleList.get(position).publishedAt)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            holder.timeStamp.setReferenceTime(date!!.time)
        }


    }


    class ListNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val article_image = itemView.article_image
        val article_title = itemView.title
        val timeStamp = itemView.timestamp

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


}

