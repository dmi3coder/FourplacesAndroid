package com.dmi3coder.fourplaces.cafe;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmi3coder.fourplaces.R;

public class NavigationAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;

    public NavigationAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return NavigationItem.values().length+1;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.navigation_item,null,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.navigation_image);
        TextView textView = (TextView) view.findViewById(R.id.navigation_text);
        if(i == NavigationItem.values().length){
            imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.favi_o));
            textView.setText("Favorite");
        }
        else {
            imageView.setImageDrawable(ContextCompat.getDrawable(context,NavigationItem.values()[i].id));
//            textView.setText();

        }
        return view;
    }

    public enum NavigationItem{
        FOOD(R.drawable.food_o,"food"),
        COFFEE(R.drawable.coffee_o,"cafe"),
        NIGHTLIFE(R.drawable.night_o,"nightclub"),
        FUN(R.drawable.fun_o,"fun"),
        PUB(R.drawable.pub_o,"pub"),
        FASTFOOD(R.drawable.pizza_o,"pizza"),
        SUSHI(R.drawable.shushi_o,"sushi"),
        ETC(R.drawable.other_o,"etc");

        private int id;
        private String name;

        NavigationItem(int id,String name){
            this.id = id;

            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
