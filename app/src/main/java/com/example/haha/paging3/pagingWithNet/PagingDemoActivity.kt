package com.example.haha.paging3.pagingWithNet

import android.media.MediaCodec
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haha.customview.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingDemoActivity:AppCompatActivity() {

    companion object{
        const val TAG = "PagingDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)

        val mainModel = MainActivityViewModel()
        val  dataAdapter = DataRecycleViewAdapter()
        val rv = findViewById<RecyclerView>(R.id.rv_page)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = dataAdapter
        findViewById<TextView>(R.id.tv_check).setOnClickListener {
            lifecycleScope.launch {
                mainModel.getData().collectLatest {
                    dataAdapter.submitData(it)
                }
            }
        }

        dataAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.NotLoading -> {
                    Log.d(TAG, "is NotLoading")
                }
                is LoadState.Loading -> {
                    Log.d(TAG, "is Loading")
                }
                is LoadState.Error -> {
                    Log.d(TAG, "is Error")
                }
            }
        }
    }
}