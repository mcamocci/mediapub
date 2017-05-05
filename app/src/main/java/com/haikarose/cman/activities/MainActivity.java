package com.haikarose.cman.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haikarose.cman.R;
import com.haikarose.cman.UPloadFileService;
import com.haikarose.cman.adapters.PostImageAdapter;
import com.haikarose.cman.pojos.Post;
import com.haikarose.cman.pojos.PostImageItem;
import com.haikarose.cman.pojos.UserPreference;
import com.haikarose.cman.tools.CommonInformation;
import com.haikarose.cman.tools.PreferenceManager;
import com.haikarose.cman.tools.TransferrableContent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import net.gotev.uploadservice.ContentType;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity extends AppCompatActivity {

    public static int PERMISSION_REQUEST_CODE=2;
    public static boolean granted=false;
    private LinearLayout buttonPublish;
    private RecyclerView resorcesRecyclerView;
    private PostImageAdapter adapter;
    private List<Object> objectsList=new ArrayList<>();
    private EditText writtenMessage;
    ArrayList<String> photoPaths=null;
    ArrayList<String> docPaths=null;
    private UserPreference userPreference;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writtenMessage=(EditText)findViewById(R.id.written_article);

        buttonPublish=(LinearLayout) findViewById(R.id.button_publish);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userPreference=PreferenceManager.UserStored(getBaseContext());


        resorcesRecyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       /* resorcesRecyclerView.setLayoutManager(layoutManager);

        adapter=new PostImageAdapter(getBaseContext(),objectsList);
        resorcesRecyclerView.setAdapter(adapter);*/

        //the permission issue
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            granted=true;
        }


        LinearLayout button=(LinearLayout)findViewById(R.id.add_file);
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
                    for(String photo:photoPaths)
                    {
                        Toast.makeText(getBaseContext(),photo,Toast.LENGTH_LONG).show();
                        PostImageItem imageItem=new PostImageItem();
                        imageItem.setUrl(photo);
                        imageItem.setType(new File(photo).getName());
                        objectsList.add(imageItem);

                    }

//                    adapter.notifyDataSetChanged();
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


                Intent intent=new Intent(getBaseContext(), UPloadFileService.class);
                Post post=new Post();
                post.setContent(writtenMessage.getText().toString());
                post.setDate(new Date().toString());
                List<PostImageItem> imagesItem=new ArrayList<>();
                if(photoPaths!=null){

                    //uploadMultipart();
                   /* if(photoPaths.size()>0){
                        for(String item:photoPaths){
                            PostImageItem imageItem=new PostImageItem();
                            imageItem.setUrl(item);
                            imageItem.setType(item);
                            imagesItem.add(imageItem);
                        }
                        post.setResourcesList(imagesItem);
                    }*/
                }

                if(photoPaths==null){
                    uploadMultipart();
                   /* publishNoResource(getBaseContext(), CommonInformation.POST_MESSAGE,
                            writtenMessage.getText().toString().trim());*/
                }else{
                    uploadMultipart();
                }
/*
                intent.putExtra(Post.EXCHANGE_ID, TransferrableContent.toJsonObject(post));
                startService(intent)*/;
                Toast.makeText(getBaseContext(), "Please wait while we publish", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"hey",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        if(writtenMessage!=null){
            if(writtenMessage.getText().toString().length()>6){
                dialog.show();
            }else{
                Toast.makeText(getBaseContext(), "Your post description is too short", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getBaseContext(),"You must include a message",Toast.LENGTH_LONG).show();
        }

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

    public void publishNoResource(final Context context, String url,String content){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("USER_ID",1);
        params.put("CONTENT",content);
        progress= ProgressDialog.show(MainActivity.this,"Please wait",
                "performing reset action", false);

        progress.show();


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Toast.makeText(context,"Please try again later",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progress.dismiss();
                Log.e("response",responseString);
                if(responseString.length()<8){
                    Snackbar.make(buttonPublish,
                            "Failed to publish , try again later"
                            ,Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(buttonPublish,"published",Snackbar.LENGTH_LONG).show();
                    Log.e("response",responseString);

                }


            }
        });

    }

    public void uploadMultipart() {


        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            UploadNotificationConfig uploadNotificationConfig=new UploadNotificationConfig();
            uploadNotificationConfig.setTitle("Completed");
            uploadNotificationConfig.setCompletedIconColor(android.R.color.holo_red_dark);
            uploadNotificationConfig.setCompletedMessage("The posting process were completed");
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId,CommonInformation.POST_MESSAGE)
                    //.addFileToUpload(path, "image") //Adding file
                    .addParameter("USER_ID","1") //Adding text parameter to the request
                    .addFileToUpload(photoPaths.get(0),"RESOURCES[]")
                    .addFileToUpload(photoPaths.get(1),"RESOURCES[]")
                    .addParameter("CONTENT",writtenMessage.getText().toString().trim()) //Adding text parameter to the request
                    .setNotificationConfig(uploadNotificationConfig)
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
