package goldenbyte.codemonkeys.goldenbyteproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import goldenbyte.codemonkeys.goldenbyteproject.R;
import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;

/**
 * Created by Naomi Blues on 03.01.2016 16:08.
 */
public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CafeViewHolder> {
    private ArrayList<Cafe> cafeList;
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
        protected RelativeLayout clickZone;
        public CafeViewHolder(View v){
            super(v);
            name = (TextView)v.findViewById(R.id.name);
            type = (TextView)v.findViewById(R.id.type);
            workTime = (TextView)v.findViewById(R.id.time);
            descritption = (ExpandableTextView)v.findViewById(R.id.description);
            position = (TextView)v.findViewById(R.id.position);
            cafeImage = (ImageView)v.findViewById(R.id.cafeImage);
            likeButton = (LikeButton)v.findViewById(R.id.star_button);
            clickZone  = (RelativeLayout)v.findViewById(R.id.clickZone);
        }
    }

    public  CafeAdapter(ArrayList<Cafe> cafeList, Context context){
        this.cafeList = cafeList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final CafeViewHolder currentCafeHolder, int cafeListPosition) {
    final Cafe currentCafe = cafeList.get(cafeListPosition);
//        Glide.with(context).load(currentCafe.getImageUrl()).into(currentCafeHolder.cafeImage);
        Glide.with(context).load(
                currentCafe.getImageUrl())
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>(250, 250) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        currentCafeHolder.cafeImage.setImageBitmap(resource);
                    }
                });
        currentCafeHolder.name.setText(currentCafe.getName());
        currentCafeHolder.type.setText(currentCafe.getType());
        currentCafeHolder.position.setText(currentCafe.getPosition());
        currentCafeHolder.workTime.setText(currentCafe.getWorkTime());
        currentCafeHolder.descritption.setText(currentCafe.getDescription());
        currentCafeHolder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked() {
            }

            @Override
            public void unLiked() {
            }
        });// TODO: 04.01.2016 make like/unlike event
        currentCafeHolder.likeButton.setLiked(false);// TODO: 1/9/16 make like checker from realm
        currentCafeHolder.clickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
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
