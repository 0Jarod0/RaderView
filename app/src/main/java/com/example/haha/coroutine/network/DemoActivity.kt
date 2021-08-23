package com.example.haha.coroutine.network

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.haha.customview.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DemoActivity : AppCompatActivity(){

    private lateinit var tvContent:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        tvContent = findViewById(R.id.tv_content)

        var retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(DemoService::class.java)

        tvContent.setOnClickListener {
//            apiService.queryData().enqueue(object :Callback<BaseReq<DataReqBean>>{
//                override fun onResponse(
//                    call: Call<BaseReq<DataReqBean>>,
//                    response: Response<BaseReq<DataReqBean>>
//                ) {
//                    tvContent.text = response.code().toString()
//                    var data: BaseReq<DataReqBean>? = response.body()
//                    Log.i("获取到的数据", "onResponse: "+data?.data()?.curPage)
//                }
//
//                override fun onFailure(call: Call<BaseReq<DataReqBean>>, t: Throwable) {
//                    tvContent.text = t.toString()
//                }
//
//            })

            GlobalScope.launch (Dispatchers.Main){
                val result1 =
                    async {
                        apiService.queryDataKotlin()
                    }
                val result2 =
                    async {
                        apiService.queryDataKotlin()
                    }
                tvContent.text = result1.await().toString() + "\n==\n"+result2.await().toString()+"接口2"
            }
        }
    }
}