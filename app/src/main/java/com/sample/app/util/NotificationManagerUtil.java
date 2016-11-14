package com.sample.app.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.sample.app.R;
import com.sample.app.activities.MainActivity;

import java.util.Map;

/**
 * Created by swayangjit on 13/11/16.
 */

public class NotificationManagerUtil {
    private Context mContext;

    public NotificationManagerUtil(Context context) {
        mContext = context;
    }


    public void handleNotificationAction(Map<String, String> data) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }

        builder.setContentTitle(data.get("title"));
        builder.setContentText(data.get("message"));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(data.get("message")));
        builder.setAutoCancel(true);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(defaultSoundUri);
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        // TODO:
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1 /* ID of notification */, notification);
    }
}
//}
