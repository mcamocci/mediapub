package com.haikarose.cman;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.haikarose.cman.pojos.Post;
import com.haikarose.cman.pojos.PostImageItem;
import com.haikarose.cman.tools.CommonInformation;
import com.haikarose.cman.tools.PreferenceManager;
import com.haikarose.cman.tools.TransferrableContent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;


public class UPloadFileService extends IntentService {

    public UPloadFileService() {
        super("UPloadFileService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            Post post= TransferrableContent.fromJsonToPost(intent.getStringExtra(Post.EXCHANGE_ID));


            AsyncHttpClient client=new AsyncHttpClient();
            RequestParams params=new RequestParams();
            params.put(Post.USER_ID, PreferenceManager.UserStored(getBaseContext()).getId());
            params.put(Post.CONTENT,post.getContent());

            if(!(post.getResources()==null)){
                String[] items=null;
                int index=0;
                for(PostImageItem item:post.getResources()){
                    items[index]=item.getUrl();
                }
                params.put(Post.RESOURCES,items);

            }

            client.post(getBaseContext(), CommonInformation.POST_MESSAGE, params, new TextHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    if(Integer.parseInt(responseString)==1){
                        Toast.makeText(getBaseContext(),"Your post has been uploaded",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getBaseContext(),"Your post has failed to be uploaded",Toast.LENGTH_LONG).show();
                    }


                }
            });

        }
    }

}
