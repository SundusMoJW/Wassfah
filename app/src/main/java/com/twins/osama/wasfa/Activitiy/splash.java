package com.twins.osama.wasfa.Activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.twins.osama.wasfa.R;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
//import com.google.android.gms.cast.TextTrackStyle;

public class splash extends AppCompatActivity {
    private ImageView imglogo;
    private Animation translateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        imglogo = (ImageView) findViewById(R.id.imglogo);
        translateAnim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.trans_top_to_centar);
        imglogo.setAlpha(1.0f);
        imglogo.startAnimation(translateAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
