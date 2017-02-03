package com.haikarose.cman.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haikarose.cman.R;
import com.haikarose.cman.activities.ImageViewerActivity;
import com.haikarose.cman.pojos.PostImageItem;
import com.haikarose.cman.tools.CommonInformation;
import com.haikarose.cman.tools.TransferrableContent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by root on 1/31/17.
 */

public class PostImageAdapter extends RecyclerView.Adapter<PostImageAdapter.ItemHolder> {

    private List<Object> items;
    private Context firstContext;

    public PostImageAdapter(Context context, List<Object> items) {
        this.items = items;
        this.firstContext = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_image_item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        PostImageItem post = (PostImageItem) items.get(position);
        holder.setData(post);

    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private PostImageItem postImageItem;
        private ImageView promoImage;
        private Context context;

        public ItemHolder(View view) {
            super(view);
            context = view.getContext();
            view.setOnClickListener(this);
            this.promoImage = (ImageView) view.findViewById(R.id.image);
            promoImage.setOnClickListener(this);
            // this.button=(LinearLayout)view.findViewById(R.id.button);
        }

        public void setData(PostImageItem postImageItem) {
            this.postImageItem = postImageItem;
            if (postImageItem.getUrl() != null) {
                promoImage.setVisibility(View.VISIBLE);
                try {

                    URL url = new URL(postImageItem.getUrl());
                    Glide.with(firstContext).load(url.toString()).centerCrop().placeholder(android.R.drawable.editbox_dropdown_light_frame).into(promoImage);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                promoImage.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent;

            if (v.getId() == R.id.image) {
                intent = new Intent(firstContext, ImageViewerActivity.class);
                intent.putExtra(PostImageItem.EXCHANGE_RES_ID, TransferrableContent.toJsonObject(postImageItem));
                PostImageAdapter.ItemHolder.this.context.startActivity(intent);
            }

        }
    }
}