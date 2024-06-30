package com.example.schedulerforpersonaltasks;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class InCorsoDaNotifica extends BroadcastReceiver {

    Context c;

    @Override
    public void onReceive(Context context, Intent intent) {
        c = context;
        loadData();
        String titolo = intent.getStringExtra("titolo");
        String data = intent.getStringExtra("data");
        String ora = intent.getStringExtra("ora");
        for (int i = 0; i < HomeFragment.listaInAttesa.size(); i++) {
            if(titolo.equals(HomeFragment.listaInAttesa.get(i).titolo)) {
                if(data.equals(HomeFragment.listaInAttesa.get(i).data)) {
                    if(ora.equals(HomeFragment.listaInAttesa.get(i).orario)) {
                        HomeFragment.listaInAttesa.remove(i);
                    }
                }
            }
        }
        for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
            if(titolo.equals(HomeFragment.listaEventi.get(i).titolo)) {
                if (data.equals(HomeFragment.listaEventi.get(i).data)) {
                    if (ora.equals(HomeFragment.listaEventi.get(i).orario)) {
                        HomeFragment.listaEventi.get(i).setStato("In corso");
                        HomeFragment.listaInCorso.add(HomeFragment.listaEventi.get(i));
                        HomeFragment.listaEventi.remove(i);
                    }
                }
            }
        }
        saveData();
        //per cancellare la notifica dopo aver cliccato su "in corso"
        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.cancel(TaskCreatorActivity.notificationId);
    }


    private void loadData() { //per caricare i dati che sono stati salvati localmente
        SharedPreferences sharedPreferences = c.getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        if (sharedPreferences.getString("eventi", null) != null) {
            String json_listaEventi = sharedPreferences.getString("eventi", null);
            Type type_eventi = new TypeToken<ArrayList<Evento>>() {
            }.getType();
            HomeFragment.listaEventi = gson.fromJson(json_listaEventi, type_eventi);
        }
        if (sharedPreferences.getString("inAttesa", null) != null) {
            String json_listaInAttesa = sharedPreferences.getString("inAttesa", null);
            Type type_eventi = new TypeToken<ArrayList<Evento>>() {
            }.getType();
            HomeFragment.listaInAttesa = gson.fromJson(json_listaInAttesa, type_eventi);
        }
        if (sharedPreferences.getString("inCorso", null) != null) {
            String json_listaInCorso = sharedPreferences.getString("inCorso", null);
            Type type_eventi = new TypeToken<ArrayList<Evento>>() {
            }.getType();
            HomeFragment.listaInCorso = gson.fromJson(json_listaInCorso, type_eventi);
        }
    }

    public void saveData() { //per salvare i dati localmente
        SharedPreferences sharedPreferences = c.getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json_listaEventi = gson.toJson(HomeFragment.listaEventi);
        String json_listaInAttesa = gson.toJson(HomeFragment.listaInAttesa);
        String json_listaInCorso = gson.toJson(HomeFragment.listaInCorso);
        editor.putString("eventi", json_listaEventi);
        editor.putString("inAttesa", json_listaInAttesa);
        editor.putString("inCorso", json_listaInCorso);
        editor.apply();
    }

}
