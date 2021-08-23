package com.example.haha.customview.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;

/**
 * Created by haha on 2020/3/24.
 */

public class HandThreadActivity  extends AppCompatActivity {

    private TextView textView;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String description = msg.getData().getString("description");
            textView.setText(description);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = findViewById(R.id.imgage_view);

        HandlerThread handlerThread = new HandlerThread("test-handler-thread");
        handlerThread.start();

        Handler childHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //运行在子线程
                String description = msg.getData().getString("description");
                Message message = mHandler.obtainMessage();
                Bundle data = message.getData();
                data.putString("description",description);
                mHandler.sendMessage(message);              //通过主线程得handler发送消息
            }
        };

        for (int i=0;i<10;i++){         //子线程childHandler发送消息
            Message message = childHandler.obtainMessage();
            Bundle data = new Bundle();
            data.putString("description","hello world"+i);
            message.setData(data);
            childHandler.sendMessageDelayed(message,1000*i);
        }
    }
}
