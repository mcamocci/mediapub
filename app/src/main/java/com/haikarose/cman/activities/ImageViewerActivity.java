package com.haikarose.cman.activities;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haikarose.cman.R;
import com.haikarose.cman.pojos.PostImageItem;
import com.haikarose.cman.tools.TransferrableContent;

import java.net.MalformedURLException;
import java.net.URL;

public class ImageViewerActivity extends AppCompatActivity {


    DownloadManager downloadManager;
    private PostImageItem postImageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.photo_viewer));
        postImageItem= TransferrableContent.fromJsonToPostImageItem(getIntent().getStringExtra(PostImageItem.EXCHANGE_RES_ID));

        ImageView promo_image = (ImageView) findViewById(R.id.imageView2);
        Context context = getBaseContext();


        URL url1 = null;
        try {
            url1 = new URL(postImageItem.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Glide.with(context).load(url1).placeholder(android.R.drawable.editbox_dropdown_light_frame).into(promo_image);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_gallery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }else if(id==R.id.remove){

        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    public  void showNetDialog(final Context context,String title){

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_view);
        dialog.setTitle(title);

        final TextView editTextKeywordToBlock=(TextView) dialog.findViewById(R.id.editTextKeywordsToBlock);
        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        TextView cancel= (TextView)dialog.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
