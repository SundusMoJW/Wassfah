package com.twins.osama.wasfa.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.twins.osama.wasfa.Activitiy.MainActivity;
import com.twins.osama.wasfa.Adapters.MenuAdapter;
import com.twins.osama.wasfa.Classes.Menu;
import com.twins.osama.wasfa.Helpar.OnDrawerItemClickListener;
import com.twins.osama.wasfa.Helpar.Utils;
import com.twins.osama.wasfa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.twins.osama.wasfa.Activitiy.MainActivity.VisibilityBack;
import static com.twins.osama.wasfa.Helpar.Const.MENU_ID;
import static com.twins.osama.wasfa.Helpar.Const.URL_MENU;

public class HomeFragment extends Fragment {
    private ArrayList<Menu> menuList = new ArrayList<>();
    private MenuAdapter melsadapter;
    private RecyclerView recyclerView;
    private ImageView progress_image;
    private Animation animPrograss;
    private FragmentTransaction mFragmentTransaction;
    private Button refresh;
    private LinearLayout llrefresh;

    // TODO: Rename parameter arguments, choose names that match
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity.nav_back = 0;

        progress_image = (ImageView) view.findViewById(R.id.progress_image);
        recyclerView = view.findViewById(R.id.rvmenu);
        llrefresh = (LinearLayout) view.findViewById(R.id.llrefresh);
        refresh = (Button) view.findViewById(R.id.refresh);

        VisibilityBack(false);

//        ((MainActivity) getActivity()).findViewById(R.id.go_back).setVisibility(View.GONE);
        animPrograss = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
        animPrograss.setDuration(1000);
        progress_image.startAnimation(animPrograss);
        animPrograss.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
        if (Utils.isNetworkOnline(getContext())) {
            progress_image.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            llrefresh.setVisibility(View.GONE);
            getMenuList();
        } else {

            progress_image.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            llrefresh.setVisibility(View.VISIBLE);
        }
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Utils.refrshActivity(getActivity());
                getMenuList();
            }
        });

        return view;
    }

    public void getMenuList() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, URL_MENU
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("WorcipeApp");
                    progress_image.setVisibility(View.GONE);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String category_name = object.optString("category_name");
                        int cid = object.optInt("cid");
                        String category_image = object.optString("category_image");
                        Menu menu = new Menu(cid, category_name, category_image);
                        menuList.add(menu);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    melsadapter = new MenuAdapter(getActivity(), menuList, new OnDrawerItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            RecipeFragment fragment = new RecipeFragment();
                            FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                            mFragmentTransaction = mFragmentManager.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putInt(MENU_ID, menuList.get(position).getId());
                            fragment.setArguments(bundle);
                            mFragmentTransaction.replace(R.id.frame_layout, fragment);
                            mFragmentTransaction.addToBackStack(null);
                            mFragmentTransaction.commit();
                            mFragmentManager.executePendingTransactions();
                        }
                    });
                    recyclerView.setAdapter(melsadapter);
                    melsadapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    progress_image.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error Response", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_image.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                llrefresh.setVisibility(View.VISIBLE);
            }
        });
//        int socketTimeout = 20000;//20 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        request.setRetryPolicy(policy);
        requestQueue.add(request);
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
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
//                if (Utils.isNetworkOnline(getContext())) {
                    if(melsadapter != null){
                        melsadapter.getFilter().filter(newText);
                    }
                    else{
                        Log.d("///","Null earch");
                    }
//                } else {
//                    Toast.makeText(getActivity(), getResources().getString(R.string.offNet), Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        });
    }
}


