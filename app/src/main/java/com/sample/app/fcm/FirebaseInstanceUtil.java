package com.sample.app.fcm;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by swayangjit on 13/11/16.
 */

public class FirebaseInstanceUtil {

    private static FirebaseInstanceId sFirebaseInstanceId;

    public static FirebaseInstanceId getFirebaseInstanceId() {
        if (sFirebaseInstanceId == null) {
            sFirebaseInstanceId = FirebaseInstanceId.getInstance();
        }
        return sFirebaseInstanceId;
    }

}
