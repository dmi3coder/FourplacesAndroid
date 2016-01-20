package studio.jhl.android4places.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import io.realm.Realm;
import studio.jhl.android4places.CafeActivity;
import studio.jhl.android4places.R;
import studio.jhl.android4places.bean.Cafe;

/**
 * Created by dmi3coder on 03.01.2016 16:08.
 */
public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CafeViewHolder> {
    private ArrayList<Cafe> cafeList;
    private Context context;
    private static Realm realm;

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
            realm = Realm.getDefaultInstance();
            name = (TextView)v.findViewById(R.id.name);
            type = (TextView)v.findViewById(R.id.type);
            workTime = (TextView)v.findViewById(R.id.time);
            descritption = (ExpandableTextView)v.findViewById(R.id.description);
            position = (TextView)v.findViewById(R.id.position);
            cafeImage = (ImageView)v.findViewById(R.id.cafeImage);
            likeButton = (LikeButton)v.findViewById(R.id.star_button);
            clickZone  = v.findViewById(R.id.clickZone);
            headerZone = v.findViewById(R.id.HeaderZone);
        }
    }

    public  CafeAdapter(ArrayList<Cafe> cafeList, Context context){
        this.cafeList = cafeList;
        this.context = context;
        realm = Realm.getInstance(context);
    }

    @Override
    public void onBindViewHolder(final CafeViewHolder currentCafeHolder, int cafeListPosition) {
    final Cafe currentCafe = cafeList.get(cafeListPosition);
//        Glide.with(context).load(currentCafe.getImageUrl()).into(currentCafeHolder.cafeImage);
        if(cafeListPosition ==0){
        currentCafeHolder.headerZone.setVisibility(View.VISIBLE);
        }
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
                Cafe cafe = currentCafe;
                realm.beginTransaction();
                realm.copyToRealm(cafe);
                realm.commitTransaction();
            }

            @Override
            public void unLiked() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Get a Realm instance for this thread
                        Realm realm = Realm.getInstance(context);
                        realm.beginTransaction();
                        Cafe cafe = realm.where(Cafe.class).equalTo("id", currentCafe.getId()).findFirst();
                        Log.d(TAG,"removed cafeID equals "+cafe.getId());
                        cafe.removeFromRealm();
                        realm.commitTransaction();
                    }
                });
                thread.start();
            }
        });// TODO: 04.01.2016 make like/unlike event

        currentCafeHolder.likeButton.setLiked(realm.where(Cafe.class).equalTo("id", currentCafe.getId()).findFirst()!=null);// TODO: 1/9/16 make like checker from realm
        currentCafeHolder.clickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(context, CafeActivity.class);
                menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                menuIntent.putExtra("menu_id",currentCafe.getId());
                menuIntent.putExtra("img_url",currentCafe.getImageUrl());
                menuIntent.putExtra("cafe_name",currentCafe.getName());
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
