package goldenbyte.codemonkeys.goldenbyteproject.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import goldenbyte.codemonkeys.goldenbyteproject.R;
import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;

/**
 * Created by dmi3coder on 03.01.2016 16:08.
 */
public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CafeViewHolder> {
    private ArrayList<Cafe> cafeList;
    ImageLoader imageLoader;

    public static class CafeViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView type;
        protected TextView descritption;
        protected TextView workTime;
        protected TextView position;
        protected ImageView cafeImage;
        protected LikeButton likeButton;
        protected ProgressBar progressBar;
        public CafeViewHolder(View v){
            super(v);
            name = (TextView)v.findViewById(R.id.name);
            type = (TextView)v.findViewById(R.id.type);
            workTime = (TextView)v.findViewById(R.id.time);
            descritption = (TextView)v.findViewById(R.id.description);
            position = (TextView)v.findViewById(R.id.position);
            cafeImage = (ImageView)v.findViewById(R.id.imageView);
            likeButton = (LikeButton)v.findViewById(R.id.star_button);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        }
    }

    public  CafeAdapter(ArrayList<Cafe> cafeList){
        this.cafeList = cafeList;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public void onBindViewHolder(final CafeViewHolder currentCafeHolder, int cafeListPosition) {
    final Cafe currentCafe = cafeList.get(cafeListPosition);
        try {
            imageLoader.loadImage(currentCafe.getImageUrl(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    super.onLoadingStarted(imageUri, view);
                    currentCafeHolder.progressBar.setVisibility(View.VISIBLE);

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    super.onLoadingFailed(imageUri, view, failReason);
                    currentCafeHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    currentCafeHolder.cafeImage.setImageBitmap(loadedImage);
                }
            });
        }catch (Exception e){

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
