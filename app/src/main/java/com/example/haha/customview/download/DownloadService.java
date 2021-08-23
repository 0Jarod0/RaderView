package com.example.haha.customview.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by haha on 2019/7/1.
 * 下载服务Service
 */

public class DownloadService extends Service {

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final int MSG_INIT = 10;
    public static final String UPDATE = "UPDATE";
    private DownloadTask task;
    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyDownload";

    public DownloadService(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //获取activity传来的参数
        if(ACTION_START.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo)intent.getSerializableExtra("fileInfo");
            Log.d("TAG-----", "onStartCommand: 开始下载"+fileInfo.toString());
            new Thread(new InitThread(fileInfo)).start();
        }
        if(ACTION_STOP.equals(intent.getAction())){
            Serializable fileInfo = (FileInfo)intent.getSerializableExtra("fileInfo");
            Log.d("TAG-----", "onStartCommand: 暂停下载"+fileInfo);
            if(task!=null){
                task.isDownload=true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Handler handler  =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.d("TAG", "handleMessage:-------------> "+fileInfo);

                    //启动下载任务
                    task = new DownloadTask(fileInfo,DownloadService.this);
                    task.download();
                    break;
            }
        }
    };

    private class InitThread implements Runnable{
        private FileInfo fileInfo;
        private RandomAccessFile ws;

        public InitThread(FileInfo threadInfo){
            this.fileInfo = threadInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(fileInfo.getUrl());
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setRequestMethod("GET");
                conn.connect();
                int length = -1;
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    length = conn.getContentLength();   //获取下载文件长度
                }

                if (length <= 0){
                    return;
                }
                //创建文件夹
                File dir = new File(path);
                if (!dir.exists()){
                    dir.mkdir();
                }
                File file = new File(dir,fileInfo.getName());
                this.ws = new RandomAccessFile(file,"rwd");
                ws.setLength(length);
                fileInfo.setLength(length);
                Message msg = new Message();
                msg.obj = fileInfo;
                msg.what = MSG_INIT;
                handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                conn.disconnect();
                try {
                    if(ws!=null)
                        ws.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
