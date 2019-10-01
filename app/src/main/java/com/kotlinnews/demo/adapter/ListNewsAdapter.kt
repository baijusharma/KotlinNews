package com.kotlinnews.demo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinnews.demo.ListNews
import com.kotlinnews.demo.NewsDetails
import com.kotlinnews.demo.R
import com.kotlinnews.demo.common.GlideApp
import com.kotlinnews.demo.common.ISO8601Parser
import com.kotlinnews.demo.interfaces.ItemClickListener

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
            .fitCenter()
            .override(200, 200)
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


    inner class ListNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val article_image = itemView.article_image
        val article_title = itemView.title
        val timeStamp = itemView.timestamp

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            /* Toast.makeText(
                 context, articleList.get(adapterPosition).url, Toast.LENGTH_SHORT
             ).show()
 */
            val intent = Intent(context, NewsDetails::class.java)
            intent.putExtra("webURL", articleList.get(adapterPosition).url)
            context.startActivity(intent)
        }
    }


}

