package com.kotlinnews.demo

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kotlinnews.demo.adapter.ListSourceAdapter
import com.kotlinnews.demo.common.Common
import com.kotlinnews.demo.interfaces.NewService
import com.kotlinnews.demo.model.Website
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var mService: NewService
    lateinit var adapter: ListSourceAdapter
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init cacheDB
        Paper.init(this)

        //Init Service
        mService = Common.newService

        //Init view\
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }

        recyclerView_news.setHasFixedSize(true)
        recyclerView_news.layoutManager = LinearLayoutManager(this)

        alertDialog = SpotsDialog.Builder().setContext(this).build()
        loadWebSiteSource(false)
    }

    private fun loadWebSiteSource(isToRefresh: Boolean) {
        if (!isToRefresh) {

            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null") {
                val webSite = Gson().fromJson<Website>(cache, Website::class.java)
                adapter = ListSourceAdapter(this, webSite)
                adapter.notifyDataSetChanged()
                recyclerView_news.adapter = adapter

            } else {
                // Load website and write cache
                alertDialog.show()
                //Fetch new data
                mService.sources.enqueue(object : retrofit2.Callback<Website> {

                    override fun onFailure(call: Call<Website>, t: Throwable) {
                        Toast.makeText(
                            baseContext, "Failed", Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<Website>, response: Response<Website>) {
                        adapter = ListSourceAdapter(baseContext, response.body()!!)
                        recyclerView_news.adapter = adapter
                        adapter.notifyDataSetChanged()

                        // Save to cache
                        Paper.book().write("cache", Gson().toJson(response.body()))

                        alertDialog.dismiss()
                    }

                })

            }
        } else {
            swipe_to_refresh.isRefreshing = true

            mService.sources.enqueue(object : retrofit2.Callback<Website> {

                override fun onFailure(call: Call<Website>, t: Throwable) {
                    Toast.makeText(
                        baseContext, "Failed", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<Website>, response: Response<Website>) {
                    adapter = ListSourceAdapter(baseContext, response.body()!!)
                    adapter.notifyDataSetChanged()
                    recyclerView_news.adapter = adapter

                    // Save to cache
                    Paper.book().write("cache", Gson().toJson(response.body()))

                    swipe_to_refresh.isRefreshing = false
                }

            })
        }

    }
}
