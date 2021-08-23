package com.example.haha.customview.download;

import java.util.List;

/**
 * Created by haha on 2019/7/1.
 * 数据库访问接口
 */

public interface ThreadDao {
    void addThreadInfo(ThreadInfo threadInfo);
    void deleteThreadInfo(String url,int thread_id);
    void updateThreadInfo(String url,int thread_id,int finished);
    List<ThreadInfo> getThreadInfo(String url);
    boolean findThreadInfo(String url,int thread_id);
}
