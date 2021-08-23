package com.example.haha.customview.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by haha on 2019/7/1.
 * 下载任务类
 */

public class DownloadTask {

    private FileInfo fileInfo = null;
    private Context context;
    private ThreadDao threadDao;
    public boolean isDownload;
    private int finished;

    public DownloadTask(FileInfo fileInfo,Context context){
        this.fileInfo = fileInfo;
        this.context = context;
        this.threadDao = new ThreadDaoImpl(context);
    }

    //下载文件
    public void download(){
        //读取数据库线程信息
        List<ThreadInfo> threadInfos = threadDao.getThreadInfo(fileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if (threadInfos.size() == 0){
            threadInfo = new ThreadInfo(0,fileInfo.getUrl(),0,fileInfo.getLength(),0);
        }else{
            threadInfo = threadInfos.get(0);
            Log.d("TAG------",threadInfo.getEnd() + "---download:----"+threadInfo.getFinished());
        }
        //创建子线程下载文件
        new Thread(new DownloadThread(threadInfo)).start();
    }

    private class DownloadThread implements Runnable{

        private ThreadInfo threadInfo;
        public DownloadThread(ThreadInfo threadInfo){
            this.threadInfo = threadInfo;
        }
        @Override
        public void run() {
            //向数据库加入线程信息
            if (!threadDao.findThreadInfo(threadInfo.getUrl(),threadInfo.getId())){
                threadDao.addThreadInfo(threadInfo);
            }
            try {
                URL url  =new URL(threadInfo.getUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5*1000);
                //设置下载位置
                int start=threadInfo.getStart()+threadInfo.getFinished();
                conn.setRequestProperty("Range","bytes="+start+"-"+threadInfo.getEnd());
                //设置文件写入位置
                File file  =new File(DownloadService.path,fileInfo.getName());
                RandomAccessFile ws = new RandomAccessFile(file,"rwd");
                ws.seek(start);
                //开始下载
                //注意别写成200了不然就无法完成断点下载，206 Partial Content 客户发送了一个带有Range头的GET请求，服务器完成了它（HTTP 1.1新）。
                if(conn.getResponseCode()==206){

                    InputStream inputStream = conn.getInputStream();
                    byte[] data = new byte[1024];
                    int len=-1;
                    Intent intent=new Intent(DownloadService.UPDATE);
                    finished=threadInfo.getFinished();

                    //读取文件
                    while ((len=inputStream.read(data))!=-1){
                        //写入文件
                        ws.write(data,0,len);
                        //发送广播更新进度条
                        finished+=len;

                        intent.putExtra("finished", finished * 100 / fileInfo.getLength());
                        context.sendBroadcast(intent);
                        Log.d("TAG----******-----", threadInfo.getEnd()+"---download: ---"+finished);
                        //下载暂停，保存下载进度
                        if(isDownload){

                            threadDao.updateThreadInfo(threadInfo.getUrl(),threadInfo.getId(),finished);

                            return;
                        }

                    }

                    //删除线程信息
                    threadDao.deleteThreadInfo(threadInfo.getUrl(),threadInfo.getId());
                    ws.close();
                    inputStream.close();
                    conn.disconnect();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
