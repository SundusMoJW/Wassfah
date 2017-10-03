package com.twins.osama.wasfa.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.twins.osama.wasfa.Activitiy.MainActivity;
import com.twins.osama.wasfa.Activitiy.ViewPagerActivity;
import com.twins.osama.wasfa.Adapters.AdapterRecipe;
import com.twins.osama.wasfa.Classes.Recipe;
import com.twins.osama.wasfa.Helpar.OnDrawerItemClickListener;
import com.twins.osama.wasfa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.twins.osama.wasfa.Activitiy.MainActivity.VisibilityBack;
import static com.twins.osama.wasfa.Helpar.Const.INDEX;
import static com.twins.osama.wasfa.Helpar.Const.MENU_CID;
import static com.twins.osama.wasfa.Helpar.Const.MENU_ID;
import static com.twins.osama.wasfa.Helpar.Const.RECIP_LIST;
import static com.twins.osama.wasfa.Helpar.Const.URL_RECIPE;

public class RecipeFragment extends Fragment {
    private RecyclerView gridView;
    private AdapterRecipe recipadapter;
    private ArrayList<Recipe>
            recipeList = new ArrayList();
    private RecyclerView.LayoutManager mLayoutManager;
    private int position;
    private ImageView imgprogress;
    private Animation animPrograss;

    public static RecipeFragment newInstance() {
        RecipeFragment fragment = new RecipeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        VisibilityBack(true);
        position = getArguments().getInt(MENU_ID);
        imgprogress = (ImageView) view.findViewById(R.id.imgprogress);
        gridView = view.findViewById(R.id.rvgrid);
        MainActivity.nav_back = 1;

        animPrograss = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
        animPrograss.setDuration(1000);
        imgprogress.startAnimation(animPrograss);
        animPrograss.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridView.setLayoutManager(mLayoutManager);
        getRecipeList();
        setHasOptionsMenu(true);
        return view;
    }

    public void getRecipeList() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, URL_RECIPE + position
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("WorcipeApp");
                    imgprogress.setVisibility(View.GONE);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int cid = object.optInt("cid");
                        int wid = object.optInt("wid");
                        String name_recip = object.optString("worcipe_heading");
                        String img_recip = object.optString("worcipe_image");
                        String worcipe_time = object.optString("worcipe_time");
                        String worcipe_content = object.optString("worcipe_content");
                        String worcipe_ingredients = object.optString("worcipe_ingredients");

                        Recipe menue = new Recipe(cid, wid, name_recip, img_recip, worcipe_time,
                                Html.fromHtml(worcipe_content).toString(), Html.fromHtml(worcipe_ingredients).toString(), false);
                        recipeList.add(menue);
                    }
                    recipadapter = new AdapterRecipe(getActivity(), recipeList, new OnDrawerItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                            intent.putExtra(MENU_CID, recipeList.get(position).getCid());
                            intent.putExtra(RECIP_LIST, recipeList);
                            intent.putExtra(INDEX, position);
                            startActivity(intent);
                        }
                    });
                    gridView.setAdapter(recipadapter);
                    recipadapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    imgprogress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error Response", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imgprogress.setVisibility(View.GONE);
            }
        });
//        int socketTimeout = 20000;//20 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        request.setRetryPolicy(policy);
        requestQueue.add(request);
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

                    if(recipadapter != null){
                        recipadapter.getFilter().filter(newText);
                    }
                    else{
                        getRecipeList();
                    }
                return true;
            }
        });
    }
}
