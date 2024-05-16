package com.example.aboba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class IconAdapter extends BaseAdapter {

    private Context context;
    private int[] icons;

    public IconAdapter(Context context, int[] icons) {
        this.context = context;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
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
        imageView.setImageResource(icons[position]);

        return convertView;
    }
}
