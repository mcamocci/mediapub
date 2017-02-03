package com.haikarose.cman.pojos;

import java.util.List;

/**
 * Created by root on 1/28/17.
 */

public class Post {

    public static final String PAGE="page";
    public static final String COUNT="count";
    public static final String TYPE_IMAGE="IMAGE";
    public static final String EXCHANGE_ID="EXCHANGE_ID";
    public static final String SEARCH="SEARCH";
    public static final String RESOURCES="RESOURCES[]";
    public static final String USER_ID="USER_ID";
    public static final String CONTENT="CONTENT";



    private String date;
    private String content;
    private String uploader;
    private int id;
    private List<PostImageItem> resources;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void addResourceList(PostImageItem resource){
        this.resources.add(resource);
    }

    public void setResourcesList(List<PostImageItem> resources){
        this.resources=resources;
    }

    public List<PostImageItem> getResources(){
        return this.resources;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.uploader;
    }
    public void setName(String name){
        this.uploader=name;
    }

}
