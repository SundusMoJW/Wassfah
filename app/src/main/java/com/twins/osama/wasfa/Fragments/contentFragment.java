package com.twins.osama.wasfa.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twins.osama.wasfa.Classes.Recipe;
import com.twins.osama.wasfa.R;

import java.util.ArrayList;

import static com.twins.osama.wasfa.Helpar.Const.ARG_OBJECT;
import static com.twins.osama.wasfa.Helpar.Const.ARRAY_SLIDER;
import static com.twins.osama.wasfa.Helpar.Const.URL_Image;


public class contentFragment extends Fragment {
    private ImageView img;
    private TextView content;
    private TextView dtvcontent;
    private TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        Bundle args = getArguments();
        final ArrayList<Recipe> list = args.getParcelableArrayList(ARRAY_SLIDER);
        final int position = args.getInt(ARG_OBJECT);
        img = (ImageView) view.findViewById(R.id.main_backdrop);
        toolbar_title=(TextView)view.findViewById(R.id.toolbar_title);
        Glide.with(getContext()).load(URL_Image + list.get(position).getImgPath()).into(img);

        content = (TextView) view.findViewById(R.id.content);
        dtvcontent = (TextView) view.findViewById(R.id.dtvcontent);

        content.setText(list.get(position).getDescription());
        dtvcontent.setText(list.get(position).getSteps());
        toolbar_title.setText(list.get(position).getTitel());
        return view;
    }

 }

