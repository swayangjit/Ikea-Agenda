package com.sample.app.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by swayangjit on 10/11/16.
 */

public class NetworkUtil {
    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
