package com.xp.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xp.movie.R;
import com.xp.movie.model.DrawerItem;

import java.util.ArrayList;


/**
 * Created by XP on 2015/5/1.
 */
public class DrawerItemAdapter extends ArrayAdapter {
    private int resourceId;

    public DrawerItemAdapter(Context context, int resourceId, ArrayList<DrawerItem> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItem drawerItem = (DrawerItem) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.itemIconView = (ImageView) convertView.findViewById(R.id.drawerItem_icon_view);
            viewHolder.itemNameView = (TextView) convertView.findViewById(R.id.drawerItem_name_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemIconView.setImageResource(drawerItem.getItemIconId());
        viewHolder.itemNameView.setText(drawerItem.getItemName());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView itemIconView;
        private TextView itemNameView;
    }
}
