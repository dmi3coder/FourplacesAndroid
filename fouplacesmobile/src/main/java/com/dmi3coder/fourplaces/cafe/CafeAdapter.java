package com.dmi3coder.fourplaces.cafe;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dmi3coder.fourplaces.CafeActivity;
import com.dmi3coder.fourplaces.R;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.parceler.Parcels;

import java.util.List;



public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CafeViewHolder> {
    private List<Cafe> cafeList;
    private Context context;

    private static final String TAG = "dmi3debug";


    public static class CafeViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView type;
        protected ExpandableTextView descritption;
        protected TextView workTime;
        protected TextView position;
        protected ImageView cafeImage;
        protected LikeButton likeButton;
        protected View clickZone;
        protected View headerZone;
        public CafeViewHolder(View v){
            super(v);
            name = (TextView)v.findViewById(R.id.name);
            type = (TextView)v.findViewById(R.id.type);
            workTime = (TextView)v.findViewById(R.id.time);
            descritption = (ExpandableTextView)v.findViewById(R.id.description);
            position = (TextView)v.findViewById(R.id.address);
            cafeImage = (ImageView)v.findViewById(R.id.cafeImage);
            likeButton = (LikeButton)v.findViewById(R.id.star_button);
            clickZone  = v.findViewById(R.id.clickZone);
            headerZone = v.findViewById(R.id.HeaderZone);
        }
    }

    public  CafeAdapter(List<Cafe> cafeList, Context context){
        this.cafeList = cafeList;
        this.context = context;
    }


    public List<Cafe> getCafeList(){
        return cafeList;
    }


    @Override
    public void onBindViewHolder(final CafeViewHolder currentCafeHolder, final int cafeListPosition) {
    final Cafe currentCafe = cafeList.get(cafeListPosition);
        if(cafeListPosition ==0&&context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
        currentCafeHolder.headerZone.setVisibility(View.VISIBLE);
        }
        else {
            currentCafeHolder.headerZone.setVisibility(View.GONE);
        }
        Glide.with(context).load(R.drawable.no_image).into(currentCafeHolder.cafeImage);
        Glide.with(context).load(
                currentCafe.getImageUrl())
                .asBitmap()
                .centerCrop()
                .error(R.drawable.no_image)
                .into(new SimpleTarget<Bitmap>(250, 250) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        currentCafeHolder.cafeImage.setImageBitmap(resource);
                    }
                });
        currentCafeHolder.name.setText(currentCafe.getName());
        currentCafeHolder.type.setText(currentCafe.getType());
        currentCafeHolder.position.setText(currentCafe.getAddress());
        currentCafeHolder.workTime.setText(currentCafe.getWorkTime());
        currentCafeHolder.descritption.setText(currentCafe.getDescription());
        currentCafeHolder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked() {
            }

            @Override
            public void unLiked() {
            }
        });

        currentCafeHolder.clickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(context, CafeActivity.class);
                menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                menuIntent.putExtra("currentCafe", Parcels.wrap(Cafe.class,currentCafe));
                context.startActivity(menuIntent);
            }
        });
    }

    @Override
    public CafeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("dmi3debug", "onCreateViewHolder: ");
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cafe_item, parent, false);
        return new CafeViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        try {
            return cafeList.size();
        }catch (Exception e){
            return 0;
        }
    }


}
