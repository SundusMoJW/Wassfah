package com.twins.osama.wasfa.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twins.osama.wasfa.Classes.DrawerItem;
import com.twins.osama.wasfa.Helpar.OnDrawerItemClickListener;
import com.twins.osama.wasfa.R;

import java.util.ArrayList;


/**
 * Created by Osama on 7/23/2017.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<DrawerItem> items;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private OnDrawerItemClickListener listener;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView textView;
        ImageView imageView;
        public LinearLayout drawer_Item;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.text_home);
                drawer_Item = (LinearLayout) itemView.findViewById(R.id.drower_Item);
                imageView = (ImageView) itemView.findViewById(R.id.image_home);
                Holderid = 1;
            } else {
                Holderid = 0;
            }
        }

        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;
            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }
    }

    public ArrayList<DrawerItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DrawerItem> items) {
        this.items = items;
    }

    public MyAdapter(Activity context, ArrayList<DrawerItem> items, OnDrawerItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toolbar, parent, false);
            ViewHolder vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false); //Inflating the layout
            ViewHolder vhHeader = new ViewHolder(v, viewType);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        if (holder.Holderid == 0) {
        } else {
            DrawerItem item = items.get(position - 1);//0 is header
            holder.textView.setText(item.getTitle());
            if (item.isSelected()) {
                holder.textView.setTextColor(Color.parseColor("#ffffff"));
                holder.drawer_Item.setBackgroundColor(Color.parseColor("#D35650"));
                holder.imageView.setImageResource(item.getImage());
            } else {
                holder.textView.setTextColor(Color.parseColor("#ee333333"));
                holder.drawer_Item.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.imageView.setImageResource(item.getImage());
            }
            holder.drawer_Item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position - 1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }
}
