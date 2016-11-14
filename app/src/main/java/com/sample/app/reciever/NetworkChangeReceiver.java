package com.sample.app.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sample.app.util.PreferenceUtil;

/**
 * Created by swayangjit on 13/11/16.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "InstanceID token: " + token);

            if (!TextUtils.isEmpty(token)) {
                PreferenceUtil.getInstance().setFcmToken(token);
            }
        }
    }
}

