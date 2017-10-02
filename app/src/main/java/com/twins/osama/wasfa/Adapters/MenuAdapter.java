package com.twins.osama.wasfa.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twins.osama.wasfa.Classes.Menu;
import com.twins.osama.wasfa.Helpar.OnDrawerItemClickListener;
import com.twins.osama.wasfa.R;

import java.util.ArrayList;

import static com.twins.osama.wasfa.Helpar.Const.URL_Image;

/**
 * Created by Osama on 9/22/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements Filterable {
    private ArrayList<Menu> item = new ArrayList<>();
    private ArrayList<Menu> itemFilter = new ArrayList<>();
    private Context context;
    private final OnDrawerItemClickListener listener;

    public MenuAdapter(Context context, ArrayList originlIitem, OnDrawerItemClickListener listener) {
        this.context = context;
        this.item = originlIitem;
        this.listener = listener;
        this.itemFilter = originlIitem;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, final int position) {
        holder.text_main.setText(item.get(position).getCategory());
        if (item.get(position).getFilePath() != null) {
            Glide.with(context).load(URL_Image + item.get(position).getFilePath()).into(holder.img_hom_recview);
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public void addItem(Menu menu) {
        item.add(menu);
        notifyItemInserted(item.size());
    }

    public void removeItem(int position) {
        item.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, item.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_hom_recview;
        TextView text_main;
        CardView cv;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) itemView.findViewById(R.id.cardView_home);
            img_hom_recview = (ImageView) view.findViewById(R.id.img_hom_recview);
            text_main = (TextView) view.findViewById(R.id.text_main);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    item = itemFilter;
                } else {
                    ArrayList<Menu> filteredList = new ArrayList<>();
                    for (Menu androidVersion : itemFilter) {
                        if (androidVersion.getCategory().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    item = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = item;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                item = (ArrayList<Menu>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}