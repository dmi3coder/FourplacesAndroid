package goldenbyte.codemonkeys.goldenbyteproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import goldenbyte.codemonkeys.goldenbyteproject.R;
import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;

/**
 * Created by Naomi Blues on 03.01.2016 16:08.
 */
public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CafeViewHolder> {
    private ArrayList<Cafe> cafeList;
    ImageLoader imageLoader;

    private static final String TAG = "dmi3debug";


    public static class CafeViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView type;
        protected ExpandableTextView descritption;
        protected TextView workTime;
        protected TextView position;
        protected ImageView cafeImage;
        protected LikeButton likeButton;
        public CafeViewHolder(View v){
            super(v);
            name = (TextView)v.findViewById(R.id.name);
            type = (TextView)v.findViewById(R.id.type);
            workTime = (TextView)v.findViewById(R.id.time);
            descritption = (ExpandableTextView)v.findViewById(R.id.description);
            position = (TextView)v.findViewById(R.id.position);
            cafeImage = (ImageView)v.findViewById(R.id.cafeImage);
            likeButton = (LikeButton)v.findViewById(R.id.star_button);
        }
    }

    public  CafeAdapter(ArrayList<Cafe> cafeList, Context context){
        this.cafeList = cafeList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public void onBindViewHolder(final CafeViewHolder currentCafeHolder, int cafeListPosition) {
    final Cafe currentCafe = cafeList.get(cafeListPosition);
        try {
            imageLoader.loadImage(currentCafe.getImageUrl(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    super.onLoadingStarted(imageUri, view);
                    Log.d(TAG, "onLoadingStarted: image load started");

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                    Log.d(TAG, "onLoadingFailed: image load failed: " + failReason.toString());
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.d(TAG, "onLoadingComplete: image load complete");
                    currentCafeHolder.cafeImage.setImageBitmap(loadedImage);
                }
            });
        }catch (Exception e){
            Log.d(TAG, "onBindViewHolder: image error"+e.toString());
        }
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
