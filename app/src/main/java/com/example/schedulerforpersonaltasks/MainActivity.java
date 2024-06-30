package com.example.schedulerforpersonaltasks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static boolean eventoModificato = false; //per vedere se si è stati prima in ModificaEventoActivity
    static boolean eventoCreato = false; //per vedere se si è stati prima in TaskCreatorActivity
    static boolean notifica = false;
    static boolean posticipato = false; //per vedere se, tramite la notifica, si è posticipato un evento
    static boolean eventoPerData = false; //per vedere se si arriva a TaskCreatorActivity da VisualizzaEventiPerData

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if(eventoModificato) {  //se si arriva da ModificaEventoActivity avviare EventiFragment
            eventoModificato = false;
            //avvia EventiFragment e nel ButtomNavigation menu seleziona "Eventi"
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setSelectedItemId(R.id.navigation_eventi);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventiFragment()).commit();
            Toast.makeText(this,"Evento modificato! Riceverai una notifica all'orario indicato", Toast.LENGTH_LONG).show();
        } else if(eventoCreato) { //se si arriva da TaskCreatorActivity avviare HomeFragment
            eventoCreato = false;
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setSelectedItemId(R.id.navigation_home);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            Toast.makeText(this,"Evento creato! Riceverai una notifica all'orario indicato", Toast.LENGTH_LONG).show();
        } /*else if(notifica) { //se si clicca su una notifica avviare NotificheFragment
            notifica = false;
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setSelectedItemId(R.id.navigation_notifiche);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificheFragment()).commit();
        }*/ else if(posticipato){ //se un evento è stato posticipato dall'action nella notifica
            posticipato = false;
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setSelectedItemId(R.id.navigation_home);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            Toast.makeText(this,"Evento posticipato! Riceverai una notifica all'orario indicato", Toast.LENGTH_LONG).show();
        } else { //se no HomeFragment
            //all'avvio dell'app, visualizza subito il fragment Home
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }


    //per aprire il fragment relativo ogni qual volta si clicca su una icona nella bottom nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectdFragment = null;
                    switch(menuItem.getItemId()) {
                        case R.id.navigation_home:
                            selectdFragment = new HomeFragment();
                            break;
                        case R.id.navigation_eventi:
                            selectdFragment = new EventiFragment();
                            break;
                        case R.id.navigation_notifiche:
                            selectdFragment = new NotificheFragment();
                            break;
                        case R.id.navigation_statistiche:
                            selectdFragment = new StatisticheFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectdFragment).commit();
                    return true;
                }
            };


    //tutti gli if servono per non far crashare l'app alla prima esecuzione, quando ancora non è stato invocato saveData()
    private void loadData() { //per caricare i dati che sono stati salvati localmente
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        if(sharedPreferences.getString("classi", null) != null) {
            String json_classes = sharedPreferences.getString("classi", null);
            Type type_classi = new TypeToken<ArrayList<String>>() {}.getType();
            HomeFragment.classes = gson.fromJson(json_classes, type_classi);
        }
        if(sharedPreferences.getString("priorita", null) != null) {
            String json_priority = sharedPreferences.getString("priorita", null);
            Type type_classi = new TypeToken<ArrayList<String>>() {}.getType();
            HomeFragment.priorityNumbers = gson.fromJson(json_priority, type_classi);
        }
        if(sharedPreferences.getString("eventi", null) != null) {
            String json_listaEventi = sharedPreferences.getString("eventi", null);
            Type type_eventi = new TypeToken<ArrayList<Evento>>() {}.getType();
            HomeFragment.listaEventi = gson.fromJson(json_listaEventi, type_eventi);
        }
        if(sharedPreferences.getString("inAttesa", null) != null) {
            String json_listaInAttesa = sharedPreferences.getString("inAttesa", null);
            Type type_eventi = new TypeToken<ArrayList<Evento>>() {}.getType();
            HomeFragment.listaInAttesa = gson.fromJson(json_listaInAttesa, type_eventi);
        }
        if(sharedPreferences.getString("inCorso", null) != null) {
            String json_listaInCorso = sharedPreferences.getString("inCorso", null);
            Type type_eventi = new TypeToken<ArrayList<Evento>>() {}.getType();
            HomeFragment.listaInCorso = gson.fromJson(json_listaInCorso, type_eventi);
        }
        if(sharedPreferences.getString("completati", null) != null) {
            String json_listaCompletati = sharedPreferences.getString("completati", null);
            Type type_eventi = new TypeToken<ArrayList<Evento>>() {}.getType();
            HomeFragment.listaCompletati = gson.fromJson(json_listaCompletati, type_eventi);
        }
    }



}
