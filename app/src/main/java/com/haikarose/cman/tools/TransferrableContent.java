package com.haikarose.cman.tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haikarose.cman.pojos.Post;
import com.haikarose.cman.pojos.PostImageItem;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by root on 1/31/17.
 */

public class TransferrableContent {



    public static String toJsonObject(PostImageItem items){
        String stringContent=new Gson().toJson(items);
        return stringContent;
    }

    public static String toJsonObject(Post items){
        String stringContent=new Gson().toJson(items);
        return stringContent;
    }

    public static String toJson(List<PostImageItem> items){
        String stringContent=new Gson().toJson(items);
        return stringContent;

    }

    public static Post fromJsonToPost(String content){
        return new Gson().fromJson(content,Post.class);
    }

    public static PostImageItem fromJsonToPostImageItem(String content){
        return new Gson().fromJson(content,PostImageItem.class);
    }

    public static List<PostImageItem> fromJsonToList(String content){
        Type listType = new TypeToken<List<PostImageItem>>() {}.getType();
        List<PostImageItem> postImageItems=new Gson().fromJson(content,listType);
        return postImageItems;
    }

}
