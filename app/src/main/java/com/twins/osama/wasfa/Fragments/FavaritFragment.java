package com.twins.osama.wasfa.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.twins.osama.wasfa.Activitiy.MainActivity;
import com.twins.osama.wasfa.Activitiy.ViewPagerActivity;
import com.twins.osama.wasfa.Adapters.AdapterRecipe;
import com.twins.osama.wasfa.Classes.Recipe;
import com.twins.osama.wasfa.Helpar.OnDrawerItemClickListener;
import com.twins.osama.wasfa.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.twins.osama.wasfa.Activitiy.MainActivity.VisibilityBack;
import static com.twins.osama.wasfa.Helpar.Const.FAVRIT_POSSITION;
import static com.twins.osama.wasfa.Helpar.Const.IF_FROM_FAVRIT;


public class FavaritFragment extends Fragment {

    private RecyclerView gridView;
    private AdapterRecipe recipadapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView imgprogress_favarit;
    private Animation animPrograss;
    private Realm realm;
    private ArrayList<Recipe> list = new ArrayList<>();
    private TextView noitem;

    public static FavaritFragment newInstance() {
        FavaritFragment fragment = new FavaritFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favarit, container, false);
        MainActivity.nav_back = 1;
        noitem = (TextView) view.findViewById(R.id.noitem);
        gridView = view.findViewById(R.id.rvgrid_favrit);
        imgprogress_favarit = (ImageView) view.findViewById(R.id.imgprogress_favarit);
        VisibilityBack(true);
        noitem.setVisibility(View.GONE);
        animPrograss = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
        animPrograss.setDuration(1000);
        imgprogress_favarit.startAnimation(animPrograss);
        animPrograss.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
        getData();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (recipadapter.getItemCount() != 0&&!(list.isEmpty())){
                    recipadapter.getFilter().filter(newText);
                } else
                    Toast.makeText(getContext(), "No Item", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
      getData();
    }
    public void getData(){
        realm.beginTransaction();
        RealmResults<Recipe> result = realm.where(Recipe.class).findAll();
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridView.setLayoutManager(mLayoutManager);
        if (!(result.isEmpty())) {
            noitem.setVisibility(View.GONE);
            imgprogress_favarit.setVisibility(View.GONE);
            realm.commitTransaction();
            list = (ArrayList<Recipe>) realm.copyFromRealm(result);
            recipadapter = new AdapterRecipe(getActivity(), list, new OnDrawerItemClickListener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                    intent.putExtra(IF_FROM_FAVRIT, true);
                    intent.putExtra(FAVRIT_POSSITION, position);
                    startActivity(intent);
                }
            });
            gridView.setAdapter(recipadapter);
            recipadapter.notifyDataSetChanged();
        } else {
            realm.cancelTransaction();
            imgprogress_favarit.setVisibility(View.GONE);
            noitem.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
    }
}
