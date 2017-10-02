package com.twins.osama.wasfa.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.twins.osama.wasfa.Classes.Recipe;
import com.twins.osama.wasfa.Fragments.contentFragment;

import java.util.ArrayList;

import static com.twins.osama.wasfa.Helpar.Const.ARG_OBJECT;
import static com.twins.osama.wasfa.Helpar.Const.ARRAY_SLIDER;

/**
 * Created by Osama on 7/24/2017.
 */

public class SliderAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Recipe> arrSlider;

    public SliderAdapter(FragmentManager fm, ArrayList<Recipe> arrSlider){
        super(fm);
        this.arrSlider = arrSlider;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new contentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OBJECT, i);
        args.putParcelableArrayList(ARRAY_SLIDER, arrSlider);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return arrSlider.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}