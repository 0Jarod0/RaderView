package com.example.haha.customview.download;

import java.io.Serializable;

/**
 * Created by haha on 2019/7/1.
 */

public class FileInfo implements Serializable {

    //文件id
    private int id;
    //文件下载地址
    private String url;
    //文件名
    private String name;
    //文件长度
    private int length;
    //文件下载完成进度
    private int finished;

    public FileInfo(){}
    public FileInfo(int id,String url,String name,int length,int finished){
        this.id = id;
        this.url = url;
        this.name = name;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{"+
                "id=" + id +
                ",url=" + url + '\''+
                ",name=" + name + '\'' +
                ",length=" + length +
                ",finished=" + finished + '}';
    }
}
