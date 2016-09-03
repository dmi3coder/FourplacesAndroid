package com.dmi3coder.fourplaces.menu;

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
import com.dmi3coder.fourplaces.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MenuAdapter extends BaseAdapter {
    private List<Meal> meals;
    private LayoutInflater inflater;
    private Context context;

    public MenuAdapter(List<Meal> meals, Context context){
        inflater = LayoutInflater.from(context);
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
        try {
            Meal currentMeal = meals.get(position);
            convertView = inflater.inflate(R.layout.menu_item, parent, false);
            currentMenuViewHolder = new MenuViewHolder(convertView);
            currentMenuViewHolder.name.setText(currentMeal.getName());
            currentMenuViewHolder.description.setText(currentMeal.getDescription());
            try {
                currentMenuViewHolder.price.setText(""+currentMeal.getPrice());
            } catch (Resources.NotFoundException e) {
                currentMenuViewHolder.price.setText("");
            }
            Glide.with(context).load(
                    currentMeal.getImageUlr())
                    .asBitmap()
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(250, 250) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            currentMenuViewHolder.mealImage.setImageBitmap(resource);
                        }
                    });
        }
        catch (Exception e){}
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
