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
import com.twins.osama.wasfa.Classes.Recipe;
import com.twins.osama.wasfa.Helpar.OnDrawerItemClickListener;
import com.twins.osama.wasfa.R;

import java.util.ArrayList;
import java.util.Random;

import static com.twins.osama.wasfa.Helpar.Const.URL_Image;

/**
 * Created by Osama on 9/23/2017.
 */

public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.ViewHolder> implements Filterable {
    private ArrayList<Recipe> item = new ArrayList<>();
    private ArrayList<Recipe> itemFilter = new ArrayList<>();
    private Context context;
    private final OnDrawerItemClickListener listener;
    private Random mRandom = new Random();

    public AdapterRecipe(Context context, ArrayList originlItem, OnDrawerItemClickListener listener) {
        this.context = context;
        this.item = originlItem;
        this.itemFilter = originlItem;
        this.listener = listener;
    }

    @Override
    public AdapterRecipe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_recipe, parent, false);
        return new AdapterRecipe.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecipe.ViewHolder holder, final int position) {
        holder.title_recipe.setText(item.get(position).getTitel());
        holder.tvtime.setText(item.get(position).getDurtaion());
        if (item.get(position).getImgPath() != null) {
            Glide.with(context).load(URL_Image + item.get(position).getImgPath()).into(holder.imgrecipe);
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

    public void addItem(Recipe recipe) {
        item.add(recipe);
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


    protected int getRandomIntInRange(int max, int min) {
        return mRandom.nextInt((max - min) + min) + min;
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
                    ArrayList<Recipe> filteredList = new ArrayList<>();
                    for (Recipe recipe : itemFilter) {
                        if (recipe.getTitel().toLowerCase().contains(charString)) {
                            filteredList.add(recipe);
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
                item = (ArrayList<Recipe>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgrecipe;
        TextView title_recipe;
        TextView tvtime;
        CardView cv;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) itemView.findViewById(R.id.cardView_recipe);
            imgrecipe = (ImageView) view.findViewById(R.id.imgrecipe);
            title_recipe = (TextView) view.findViewById(R.id.title_recipe);
            tvtime = (TextView) view.findViewById(R.id.tvtime);
        }

//        public void filterList(ArrayList<Recipe> filterdNames) {
//            item = filterdNames;
//            notifyDataSetChanged();
//        }

    }
}