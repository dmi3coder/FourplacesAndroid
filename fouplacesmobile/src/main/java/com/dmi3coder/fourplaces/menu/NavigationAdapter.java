package com.dmi3coder.fourplaces.menu;


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
        FOOD(R.drawable.food_o),COFFEE(R.drawable.coffee_o),NIGHTLIFE(R.drawable.night_o),FUN(R.drawable.fun_o),PUB(R.drawable.pub_o),FASTFOOD(R.drawable.pizza_o),SUSHI(R.drawable.shushi_o),ETC(R.drawable.other_o);

        private int id;

        NavigationItem(int id){
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


    }
}
