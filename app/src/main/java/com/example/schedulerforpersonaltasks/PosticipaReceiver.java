package com.example.schedulerforpersonaltasks;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PosticipaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String titolo = intent.getStringExtra("titoloPost");
        String data = intent.getStringExtra("dataPost");
        String ora = intent.getStringExtra("oraPost");

        Intent i = new Intent();
        i.setClassName("com.example.schedulerforpersonaltasks", "com.example.schedulerforpersonaltasks.PosticipaDaNotifica");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("titoloPosticipa", titolo);
        i.putExtra("dataPosticipa", data);
        i.putExtra("oraPosticipa", ora);
        context.startActivity(i);
        //per cancellare la notifica dopo aver cliccato su "posticipa"
        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.cancel(TaskCreatorActivity.notificationId);
    }
}
