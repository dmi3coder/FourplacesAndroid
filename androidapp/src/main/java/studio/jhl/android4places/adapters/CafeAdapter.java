package studio.jhl.android4places.adapters;

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
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import studio.jhl.android4places.CafeActivity;
import studio.jhl.android4places.R;
import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.bean.ParcelCafe;

/**
 * Created by dmi3coder on 03.01.2016 16:08.
 */
public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CafeViewHolder> {
    private List<Cafe> cafeList;
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

    public  CafeAdapter(List<Cafe> cafeList, Context context){
        this.cafeList = cafeList;
        this.context = context;
        realm = Realm.getInstance(context);
    }


    public List<Cafe> getCafeList(){
        return cafeList;
    }

    public List<ParcelCafe> getParcelCafeList(){
        List<ParcelCafe> parcelCafes = new ArrayList<>();
        for (Cafe cafe
                : cafeList
             ) {
            ParcelCafe parcelCafe =new ParcelCafe(cafe.getName(),cafe.getType(),cafe.getDescription(),cafe.getWorkTime(),cafe.getPosition(),cafe.getImageUrl(),cafe.getId(),cafe.getPhoneNumber(),cafe.getCoordinates());
           parcelCafes.add(parcelCafe);
        }
        return parcelCafes;
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
                Log.d(TAG, "liked: commited");
            }

            @Override
            public void unLiked() {
                // Get a Realm instance for this thread
                Realm realm = Realm.getInstance(context);
                realm.beginTransaction();
                Cafe cafe = realm.where(Cafe.class).equalTo("id", currentCafe.getId()).findFirst();
                cafe.removeFromRealm();
                realm.commitTransaction();
                Log.d(TAG, "liked: commited");
            }
        });// TODO: 04.01.2016 make like/unlike event

        currentCafeHolder.likeButton.setLiked(realm.where(Cafe.class).equalTo("id", currentCafe.getId()).findFirst()!=null);
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
