package com.twins.osama.wasfa.Activitiy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.twins.osama.wasfa.Helpar.Utils;
import com.twins.osama.wasfa.R;

public class SettingActivity extends SwipeBackActivity{

    private TextView aboutApp;
    private TextView verion;
    private ImageView go_back;
    private TextView color;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        activity = this;
        aboutApp = (TextView) findViewById(R.id.about_app);
        verion = (TextView) findViewById(R.id.verion);
        go_back = (ImageView) findViewById(R.id.go_back);
        color = (TextView) findViewById(R.id.color);
//        color_array = getResources().obtainTypedArray(R.array.color_array);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.onCreateDialogSingleChoice((Activity) (SettingActivity.this), R.array.color_array).show();
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle((String) getResources().getText(R.string.info));
                builder.setMessage((String) getResources().getText(R.string.message));
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        verion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle((String) getResources().getText(R.string.verion));
                builder.setMessage((String) getResources().getText(R.string.verion1));
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
