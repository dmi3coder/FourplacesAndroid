package studio.jhl.android4places.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import studio.jhl.android4places.R;


public class MenuAdapter extends BaseAdapter {
    private ArrayList<Meal> meals;
    private LayoutInflater inflater;
    private Context context;

    public MenuAdapter(ArrayList<Meal> meals,Context context){
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.meals = meals;
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @Override
    public Object getItem(int position) {
        return meals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuViewHolder currentMenuViewHolder;
        Meal currentMeal = meals.get(position);
        convertView = inflater.inflate(R.layout.menu_item,parent,false);
        currentMenuViewHolder = new MenuViewHolder(convertView);
        currentMenuViewHolder.name.setText(currentMeal.getName());
        currentMenuViewHolder.description.setText(currentMeal.getDescription());
        try {
            currentMenuViewHolder.price.setText(currentMeal.getPrice());
        }catch(Resources.NotFoundException e){
            currentMenuViewHolder.price.setText("");
        }
        Glide.with(context).load(
                currentMeal.getImageUrl())
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>(250, 250) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        currentMenuViewHolder.mealImage.setImageBitmap(resource);
                    }
                });
        return convertView;
    }

    public static class MenuViewHolder{
        public MenuViewHolder(View view){
            ButterKnife.bind(this,view);
        }
        @Bind(R.id.name)TextView name;
        @Bind(R.id.description)TextView description;
        @Bind(R.id.price)TextView price;
        @Bind(R.id.mealImage)ImageView mealImage;
    }
}
