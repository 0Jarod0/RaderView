package com.example.haha.customview.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;
import com.example.haha.customview.download.DownloadService;
import com.example.haha.customview.download.FileInfo;
import com.example.haha.customview.download.ThreadDaoImpl;
import com.example.haha.customview.download.ThreadInfo;

import java.util.List;

/**
 * Created by haha on 2019/7/1.
 */

public class DownloadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tv,mStart,mStop;

    private boolean isDownload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        progressBar= (ProgressBar) this.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        tv = (TextView) this.findViewById(R.id.progressTv);
        mStart = (TextView)findViewById(R.id.start);
        mStop = (TextView)findViewById(R.id.stop);

        //开始下载
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isDownload){
                    FileInfo fileInfo  =new FileInfo(0,"http://b.hiphotos.baidu.com/image/pic/item/0823dd54564e925838c205c89982d158ccbf4e26.jpg","110.jpg",0,0);
                    Intent intent = new Intent(DownloadActivity.this,DownloadService.class);
                    intent.setAction(DownloadService.ACTION_START);
                    intent.putExtra("fileInfo",fileInfo);
                    startService(intent);
                    isDownload=true;
                }
            }
        });
        //停止下载
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  downloadFileUtil.setIsDownload(false);
                isDownload=false;
                FileInfo fileInfo  =new FileInfo(0,"http://b.hiphotos.baidu.com/image/pic/item/0823dd54564e925838c205c89982d158ccbf4e26.jpg","112.jpg",0,0);
                Intent intent = new Intent(DownloadActivity.this,DownloadService.class);
                intent.setAction(DownloadService.ACTION_STOP);
                intent.putExtra("fileInfo",fileInfo);
                startService(intent);
            }
        });

        init();

    }
    //初始化
    private void init() {

        //当打开下载界面显示历史下载进度
        ThreadDaoImpl dao  =new ThreadDaoImpl(this);
        List<ThreadInfo> threadInfos = dao.getThreadInfo("http://b.hiphotos.baidu.com/image/pic/item/0823dd54564e925838c205c89982d158ccbf4e26.jpg");
        if(threadInfos.size()>0){
            ThreadInfo threadInfo = threadInfos.get(0);
            int value=threadInfo.getFinished()*100/threadInfo.getEnd();
            progressBar.setProgress(value);
            tv.setText(value+"%");
        }
        //注册广播
        IntentFilter intent = new IntentFilter();
        intent.addAction(DownloadService.UPDATE);
        registerReceiver(reciver,intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    BroadcastReceiver reciver  =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int finished = intent.getIntExtra("finished",-1);
            if(finished!=-1){
                if ( finished<=100) {
                    progressBar.setProgress( finished);
                    tv.setText( finished+"%");
                    if( finished==100){
                        isDownload=false;
                        Toast.makeText(DownloadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };
}
