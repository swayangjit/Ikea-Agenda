package com.sample.app.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sample.app.util.NotificationManagerUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by swayangjit on 13/11/16.
 */

public class AgendaFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "GenieFirebaseMessagingService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.i(TAG, "Title: " + remoteMessage.getData().get("title"));
        Log.i(TAG, "Body: " + remoteMessage.getData().get("body"));
        Log.i(TAG, "size(): " + remoteMessage.getData().size());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            if (remoteMessage.getData().containsKey("notificationpayload")) {
                String payload = remoteMessage.getData().get("notificationpayload");
                try {
                    JSONObject jsonObject = new JSONObject(payload);
                    NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(AgendaFirebaseMessagingService.this);
                    Map<String, String> payloadMap = new HashMap<>();
                    payloadMap.put("title", jsonObject.get("title").toString());
                    payloadMap.put("message", jsonObject.get("msg").toString());
                    notificationManagerUtil.handleNotificationAction(payloadMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("Payload", "" + payload);
            }


            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
        }
        // [END receive_message]
    }
}

