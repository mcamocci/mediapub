package com.haikarose.cman.tools;

/**
 * Created by root on 7/30/16.
 */
public class StringUpperHelper {

    public static String doUpperlization(String string){
        String converted=null;
        String strings=string.substring(0,1).toUpperCase()+string.substring(1,string.length()).toLowerCase();
        converted=strings;
        return converted;
    }
}
