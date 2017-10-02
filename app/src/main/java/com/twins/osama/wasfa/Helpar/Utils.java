package com.twins.osama.wasfa.Helpar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.twins.osama.wasfa.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Osama on 9/25/2017.
 */

public class Utils {
    public static boolean isNetworkOnline(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    public static void refrshActivity(Activity activity) {
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    public static Dialog onCreateDialogSingleChoice(final Context activity, int array) {
       final ArrayList itemsTitles = new ArrayList<>(Arrays.asList(activity.getResources().getStringArray(R.array.colors)));

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select Color")
                .setSingleChoiceItems(array,0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        activity.getResources().getString()
//                        activity.getResources().s(R.color.backLogo);itemsTitles.get(which);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }


}
