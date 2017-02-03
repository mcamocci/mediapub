package com.haikarose.cman.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haikarose.cman.R;
import com.haikarose.cman.pojos.Post;
import com.haikarose.cman.pojos.UserPreference;
import com.haikarose.cman.tools.PreferenceManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity extends AppCompatActivity {

    public static int PERMISSION_REQUEST_CODE=2;
    public static boolean granted=false;
    private LinearLayout buttonPublish;

    ArrayList<String> photoPaths=null;
    ArrayList<String> docPaths=null;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPublish=(LinearLayout)findViewById(R.id.button_publish);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userPreference=PreferenceManager.UserStored(getBaseContext());


        //the permission issue
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            granted=true;
        }


        ImageView button=(ImageView)findViewById(R.id.add_file);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    if(granted || Build.VERSION.SDK_INT <23){
                        showPhotoNetDialog(getBaseContext(),"Select photoes");
                    }else{
                        Toast.makeText(getBaseContext(),"Permission to read file are required",Toast.LENGTH_SHORT).show();
                    }
            }
        });

        buttonPublish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showNetDialog(getBaseContext(),"Publish content");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                granted=true;
            }else{
                granted=false;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS));
                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        Intent intent;
        if(id==android.R.id.home){
            finish();
        }else if(id==R.id.about){
            intent=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }else if(id==R.id.report){
            intent=new Intent(MainActivity.this,ReportProblemActivity.class);
            startActivity(intent);
        }else if(id==R.id.logout){
            PreferenceManager.ClearLogins(getBaseContext());
            finish();
        }
        return true;
    }

    //login//
    public void doTask(final String url, final String content,List<String> files){

        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put(Post.USER_ID,userPreference.getId());
        params.put(Post.CONTENT,content);
        if(!(files==null)){
            for(String file:files){
                try {
                    params.put(Post.RESOURCES,getFile(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

        client.post(getBaseContext(), url, params, new TextHttpResponseHandler() {

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

    private File getFile(String name) {
        File file = new File(name);
        if (!file.exists()) {
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(file));
                output.write(name);
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public  void showNetDialog(final Context context, String title){

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_view_one);
        dialog.setTitle(title);

        final TextView editTextKeywordToBlock=(TextView) dialog.findViewById(R.id.editTextKeywordsToBlock);
        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        TextView cancel= (TextView)dialog.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FilePickerBuilder.getInstance().setMaxCount(10)
                        .setSelectedFiles(docPaths)
                        .setActivityTheme(R.style.AppTheme)
                        .pickPhoto(MainActivity.this);//pickDocument(MainActivity.this);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public  void showPhotoNetDialog(final Context context, String title){

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_view);
        dialog.setTitle(title);

        final TextView editTextKeywordToBlock=(TextView) dialog.findViewById(R.id.editTextKeywordsToBlock);
        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        TextView cancel= (TextView)dialog.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FilePickerBuilder.getInstance().setMaxCount(10)
                        .setSelectedFiles(docPaths)
                        .setActivityTheme(R.style.AppTheme)
                        .pickPhoto(MainActivity.this);//pickDocument(MainActivity.this);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
