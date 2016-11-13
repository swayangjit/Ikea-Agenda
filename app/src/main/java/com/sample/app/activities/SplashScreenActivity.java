package com.sample.app.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.sample.app.R;
import com.sample.app.fcm.FirebaseInstanceUtil;
import com.sample.app.reciever.NetworkChangeReceiver;
import com.sample.app.util.PreferenceUtil;

/**
 * Created by swayangjit on 10/11/16.
 */

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initFCM();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                ;
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void initFCM() {
        if (!TextUtils.isEmpty(PreferenceUtil.getInstance().getFcmToken())) { // Disable the NetworkChangeReceiver if FCM token is available.
            ComponentName receiver = new ComponentName(SplashScreenActivity.this, NetworkChangeReceiver.class);

            PackageManager pm = this.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        } else {    // Get the FCM token if its not available.
            String token = FirebaseInstanceUtil.getFirebaseInstanceId().getToken();
            Log.d("SPlashScreenActivity", "InstanceID token: " + token);

            if (!TextUtils.isEmpty(token)) {
                PreferenceUtil.getInstance().setFcmToken(token);
            }
        }
    }
}
