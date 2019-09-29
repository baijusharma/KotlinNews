package com.kotlinnews.demo

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinnews.demo.adapter.ListNewsAdapter
import com.kotlinnews.demo.common.Common
import com.kotlinnews.demo.common.GlideApp
import com.kotlinnews.demo.interfaces.NewService
import com.kotlinnews.demo.model.News
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source = ""
    var webHostUrl: String? = ""

    lateinit var mService: NewService
    lateinit var dialog: AlertDialog
    lateinit var mAdapter: ListNewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_news)

        mService = Common.newService

        dialog = SpotsDialog.Builder().setContext(this).build()

        swipe_to_refresh.setOnRefreshListener {
            loadNews(source, true) }

        diagonal_layout.setOnClickListener {

        }

        recyclerView_list_news.setHasFixedSize(true)
        recyclerView_list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            source = intent.getStringExtra("source")
            if (!source.isEmpty()) {
                loadNews(source, false)
            }
        }
    }

    private fun loadNews(source: String, isToRefresh: Boolean) {
        if (isToRefresh) {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source))
                .enqueue(object : Callback<News> {

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        // Get first article of hot news
                        GlideApp.with(this@ListNews)
                            .load(response.body()!!.articles[0].urlToImage)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(top_image)

                        top_title.text = response.body()!!.articles[0].title
                        top_author.text = response.body()!!.articles[0].author

                        webHostUrl = response.body()!!.articles[0].url

                        //Load all remaining article
                        val removeFirstArticle = response.body()!!.articles
                        // Move first article to hot news
                        removeFirstArticle.removeAt(0)

                        mAdapter = ListNewsAdapter(removeFirstArticle, baseContext)
                        mAdapter.notifyDataSetChanged()
                        recyclerView_list_news.adapter = mAdapter

                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }
                })

        } else {

            swipe_to_refresh.isRefreshing = true

            mService.getNewsFromSource(Common.getNewsAPI(source))
                .enqueue(object : Callback<News> {

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipe_to_refresh.isRefreshing = false
                        // Get first article of hot news
                        GlideApp.with(this@ListNews)
                            .load(response.body()!!.articles[0].urlToImage)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(top_image)

                        top_title.text = response.body()!!.articles[0].title
                        top_author.text = response.body()!!.articles[0].author

                        webHostUrl = response.body()!!.articles[0].url

                        //Load all remaining article
                        val removeFirstArticle = response.body()!!.articles
                        // Move first article to hot news
                        removeFirstArticle.removeAt(0)

                        mAdapter = ListNewsAdapter(removeFirstArticle, baseContext)
                        mAdapter.notifyDataSetChanged()
                        recyclerView_list_news.adapter = mAdapter

                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }
                })

        }
    }
}