package android.myapp.mydiary.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.myapp.mydiary.AddMyNotesActivity;
import android.myapp.mydiary.R;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtils {
    private static final int REMINDER_PENDING_ID = 300;
    private static final String REMINDER_CHANNEL_ID = "reminder_notification_channel";
    private static final int NOTIFICATION_ID = 200;

    /**
     * This method creates and return the pending intent, which triggers when the notification is pressed.
     * @param context
     * @return
     */
    private static PendingIntent contentIntent(Context context)
    {
        Intent intent = new Intent(context,AddMyNotesActivity.class);
        return PendingIntent.getActivity(
                context,
                REMINDER_PENDING_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT // If intent is created again keep the intent but update data.

        );

    }

    /**
     * This method will create the notification when WI-FI is on.
     * @param context
     */
    public static void remindUser(Context context)
    {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    REMINDER_CHANNEL_ID,
                    "notification_channel",
                    NotificationManager.IMPORTANCE_HIGH

            );
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //Build the notification using Notification Manager
        NotificationCompat.Builder notificationBuilder=
                 new NotificationCompat.Builder(context,REMINDER_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                 .setSmallIcon(R.mipmap.note_icon_)
                .setContentTitle("Time to write...")
                .setContentText("Let's write something about today's experience.")
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        {
         notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        //Notify notification using notification manager
        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build());
    }
    public static void clearNotification(Context context)
    {
        NotificationManager notificationManager = (NotificationManager)
                                       context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
