package com.example.schedulerforpersonaltasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String titolo = intent.getStringExtra("titolo");
        String data = intent.getStringExtra("data");
        String ora = intent.getStringExtra("ora");

        //MainActivity.notifica = true;

        //quando si clicca sulla notifica, si va in NotificheFragment
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        //quando si clicca su "in corso" nella notifica
        Intent InCorsoDaNotificaIntent = new Intent(context, InCorsoDaNotifica.class);
        InCorsoDaNotificaIntent.putExtra("titolo", titolo);
        InCorsoDaNotificaIntent.putExtra("data", data);
        InCorsoDaNotificaIntent.putExtra("ora", ora);
        PendingIntent actionInCorsoIntent = PendingIntent.getBroadcast(context, 0, InCorsoDaNotificaIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //quando si clicca su "posticipa" nella notifica
        Intent PosticipaDaNotificaIntent = new Intent(context, PosticipaReceiver.class);
        PosticipaDaNotificaIntent.putExtra("titoloPost", titolo);
        PosticipaDaNotificaIntent.putExtra("dataPost", data);
        PosticipaDaNotificaIntent.putExtra("oraPost", ora);
        PendingIntent actionPosticipaIntent = PendingIntent.getBroadcast(context, 0, PosticipaDaNotificaIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int color = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        //come sarà fatta la notifica
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_collections_bookmark_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round))
                .setContentTitle(titolo)
                .setContentText(data + ", " + ora + ", in attesa")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .addAction(R.drawable.ic_trending_up_black_24dp, "In corso", actionInCorsoIntent)
                .addAction(R.drawable.ic_access_time_black_24dp, "Posticipa", actionPosticipaIntent)
                .setColor(color)
                .setAutoCancel(true);


        //notifica
        myNotificationManager.notify(TaskCreatorActivity.notificationId, builder.build());
    }



}

