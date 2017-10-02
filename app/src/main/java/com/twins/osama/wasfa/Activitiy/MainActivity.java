package com.twins.osama.wasfa.Activitiy;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.twins.osama.wasfa.Adapters.MyAdapter;
import com.twins.osama.wasfa.Classes.DrawerItem;
import com.twins.osama.wasfa.Fragments.FavaritFragment;
import com.twins.osama.wasfa.Fragments.HomeFragment;
import com.twins.osama.wasfa.Fragments.RecipeFragment;
import com.twins.osama.wasfa.Helpar.OnDrawerItemClickListener;
import com.twins.osama.wasfa.R;

import java.util.ArrayList;
import java.util.Arrays;

import static com.twins.osama.wasfa.Helpar.Const.IF_FROM_FAVRIT;
import static com.twins.osama.wasfa.Helpar.Const.IF_FROM_RECIPE;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<DrawerItem> items;
    private ArrayList<String> itemsTitles;
    public static int nav_back = 0;
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout Drawer;
    private Fragment fragment = null;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView menu;
    private TypedArray navMenuIcons;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Toolbar toolbar;
    private static ImageView go_back;
    private boolean ifFromFavarit;
    private boolean ifFromRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ifFromFavarit = getIntent().getBooleanExtra(IF_FROM_FAVRIT, false);
        ifFromRecipe = getIntent().getBooleanExtra(IF_FROM_RECIPE, false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fillData();
        go_back = (ImageView) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();
            }
        });
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mFragmentManager = getSupportFragmentManager();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(this, items, new OnDrawerItemClickListener() {
            @Override
            public void onClick(int position) {

                Drawer.closeDrawer(mRecyclerView);
                startEvent(position);
                updateDrawer(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        }; // Drawer Toggle Object Made
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
        Drawer.setDrawerListener(mDrawerToggle);
        if (ifFromFavarit) {
            fragment = new FavaritFragment();
            updateDrawer(1);
        } else if (ifFromRecipe) {
            fragment = new RecipeFragment();
            updateDrawer(0);
        } else {
            fragment = new HomeFragment();
            updateDrawer(0);
        }
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_layout, fragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    private void fillData() {
        itemsTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.drawerItems)));
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        items = new ArrayList<>();
        for (int i = 0; i < itemsTitles.size(); i++)
            items.add(new DrawerItem(itemsTitles.get(i), navMenuIcons.getResourceId(i, -1)
                    , i == 0 ? true : false));
        navMenuIcons.recycle();
    }

    public void updateDrawer(int position) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setIsSelected(i == position ? true : false);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void startEvent(int position) {
        switch (position) {
            case 0:
                nav_back = 0;
                fragment = new HomeFragment();
                menu.setVisibility(View.VISIBLE);
                break;
            case 1:
                nav_back = 1;
                fragment = new FavaritFragment();
                menu.setVisibility(View.VISIBLE);
                break;
            case 2:
                nav_back = 1;
                fragment = new HomeFragment();
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_layout, fragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
        updateDrawer(position);
    }

    private void toggleSlidingMenu() {
        if (Drawer.isDrawerOpen(mRecyclerView)) {
            Drawer.closeDrawer(mRecyclerView);
            mAdapter.notifyDataSetChanged();
        } else {
            Drawer.openDrawer(mRecyclerView);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
            if (mFragmentManager.getBackStackEntryCount() > 1){
                mFragmentManager.popBackStackImmediate();
                mFragmentManager.beginTransaction().commit();

        }else{
            if (Drawer.isDrawerOpen(mRecyclerView)) {
                Drawer.closeDrawer(mRecyclerView);
                mAdapter.notifyDataSetChanged();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("هل تريد الخروج من التطبيق ؟")
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.show();
        }

    }
public  static void VisibilityBack(boolean isVisibile){
    if(isVisibile==true)
    go_back.setVisibility(View.VISIBLE);
else go_back.setVisibility(View.GONE);
}
}