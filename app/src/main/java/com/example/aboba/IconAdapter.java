package com.example.aboba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class IconAdapter extends BaseAdapter {

    private Context context;
    private int[] iconArray;

    public IconAdapter(Context context, int[] iconArray) {
        this.context = context;
        this.iconArray = iconArray;
    }

    @Override
    public int getCount() {
        return iconArray.length;
    }

    @Override
    public Object getItem(int position) {
        return iconArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.icon_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.icon_image);
        imageView.setImageResource(iconArray[position]);

        return convertView;
    }
}
