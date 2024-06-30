package com.example.schedulerforpersonaltasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

public class PerDataEventiFragment extends Fragment {

    ArrayList<Evento> eventiInData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        //eventi in quella data
        eventiInData = new ArrayList<>();
        for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
            if(VisualizzaEventiPerData.data.equals(HomeFragment.listaEventi.get(i).data)) eventiInData.add(HomeFragment.listaEventi.get(i));
        }
        for (int i = 0; i < HomeFragment.listaInCorso.size(); i++) {
            if(VisualizzaEventiPerData.data.equals(HomeFragment.listaInCorso.get(i).data)) eventiInData.add(HomeFragment.listaInCorso.get(i));
        }
        for (int i = 0; i < HomeFragment.listaCompletati.size(); i++) {
            if(VisualizzaEventiPerData.data.equals(HomeFragment.listaCompletati.get(i).data)) eventiInData.add(HomeFragment.listaCompletati.get(i));
        }
        Collections.sort(eventiInData, Evento.DataComparatorAsc); //ordina gli eventi cronologicamente
        if(eventiInData.isEmpty()) { //se non ci sono eventi, visualizza "nessun evento creato"
            v = getLayoutInflater().inflate(R.layout.nessun_evento_creato, null);
            return v;
        } else { //se si filtra per qualche priorità
            v = inflater.inflate(R.layout.fragment_per_data_eventi, container, false);
            ListView listview = v.findViewById(R.id.ListViewEventiPerData);
            customAdapter custom = new customAdapter();
            listview.setAdapter(custom);
            return v;
        }
    }

    class customAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return eventiInData.size();

        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.evento_visualizzato, null);

            if(eventiInData.get(i).stato.equals("none")) {                                  //eventi in listaEventi
                TextView t = view.findViewById(R.id.titoloViewed);
                TextView d = view.findViewById(R.id.dataViewed);
                TextView o = view.findViewById(R.id.oraViewed);
                TextView p = view.findViewById(R.id.prioritaViewed);
                TextView c = view.findViewById(R.id.classeViewed);
                //stampa ogni evento dell'ArrayList
                t.setText(eventiInData.get(i).titolo);
                d.setText(eventiInData.get(i).data);
                o.setText(eventiInData.get(i).orario);
                p.setText(eventiInData.get(i).priorita);
                c.setText(eventiInData.get(i).classe);
                //elimina l'evento nella posizione i
                Button elimina = view.findViewById(R.id.buttonElimina);
                elimina.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finestra di conferma eliminazione
                        new AlertDialog.Builder(getActivity()).setTitle(eventiInData.get(i).titolo)
                                .setMessage("Sei sicuro di voler eliminare questo evento?")
                                .setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //rimuove da lista in attesa lo stesso evento rimosso dalla lista eventi
                                        for (int j = 0; j < HomeFragment.listaInAttesa.size(); j++) {
                                            if (eventiInData.get(i).titolo.equals(HomeFragment.listaInAttesa.get(j).titolo)) {
                                                if (eventiInData.get(i).data.equals(HomeFragment.listaInAttesa.get(j).data)) {
                                                    if (eventiInData.get(i).orario.equals(HomeFragment.listaInAttesa.get(j).orario)) {
                                                        HomeFragment.listaInAttesa.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                        for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                                            if (eventiInData.get(i).titolo.equals(HomeFragment.listaEventi.get(j).titolo)) {
                                                if (eventiInData.get(i).data.equals(HomeFragment.listaEventi.get(j).data)) {
                                                    if (eventiInData.get(i).orario.equals(HomeFragment.listaEventi.get(j).orario)) {
                                                        HomeFragment.listaEventi.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                        eventiInData.remove(i);
                                        saveData();
                                        loadData();
                                        //per ricaricare subito il fragment
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_perdata_container, new PerDataEventiFragment()).addToBackStack(null).commit();
                                    }
                                }).setNegativeButton("No", null).show();
                    }
                });
                //modifica l'evento nella posizione i
                Button modifica = view.findViewById(R.id.buttonModifica);
                modifica.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ModificaEventoActivity.class);
                        intent.putExtra("titolo", eventiInData.get(i).titolo);
                        intent.putExtra("data", eventiInData.get(i).data);
                        intent.putExtra("orario", eventiInData.get(i).orario);
                        intent.putExtra("priorita", eventiInData.get(i).priorita);
                        intent.putExtra("classe", eventiInData.get(i).classe);
                        startActivity(intent);
                    }
                });

            } else if(eventiInData.get(i).stato.equals("In corso")) {                   //eventi in listaIncorso
                view = getLayoutInflater().inflate(R.layout.evento_notificato, null);

                TextView t = view.findViewById(R.id.titoloViewedNot);
                TextView d = view.findViewById(R.id.dataViewedNot);
                TextView o = view.findViewById(R.id.oraViewedNot);
                TextView p = view.findViewById(R.id.prioritaViewedNot);
                TextView c = view.findViewById(R.id.classeViewedNot);
                TextView s = view.findViewById(R.id.statoViewedNot);
                //stampa ogni evento dell'ArrayList
                t.setText(eventiInData.get(i).titolo);
                d.setText(eventiInData.get(i).data);
                o.setText(eventiInData.get(i).orario);
                p.setText(eventiInData.get(i).priorita);
                c.setText(eventiInData.get(i).classe);
                s.setText(eventiInData.get(i).stato);
                //cambia stato
                Button cambiaStato = view.findViewById(R.id.buttonCambiaStato);
                cambiaStato.setText("Completato");
                cambiaStato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finestra di conferma eliminazione
                        new AlertDialog.Builder(getActivity()).setTitle(eventiInData.get(i).titolo)
                                .setMessage("Cambiare stato a 'Completato' ?")
                                .setPositiveButton("Cambia", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        eventiInData.get(i).setStato("Completato");
                                        HomeFragment.listaCompletati.add(eventiInData.get(i));
                                        for (int j = 0; j < HomeFragment.listaInCorso.size(); j++) {
                                            if (eventiInData.get(i).titolo.equals(HomeFragment.listaInCorso.get(j).titolo)) {
                                                if (eventiInData.get(i).data.equals(HomeFragment.listaInCorso.get(j).data)) {
                                                    if (eventiInData.get(i).orario.equals(HomeFragment.listaInCorso.get(j).orario)) {
                                                        HomeFragment.listaInCorso.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                        eventiInData.remove(i);
                                        saveData();
                                        loadData();
                                        //per ricaricare subito il fragment
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_perdata_container, new PerDataEventiFragment()).addToBackStack(null).commit();
                                    }
                                }).setNegativeButton("No", null).show();
                    }
                });

            } else if(eventiInData.get(i).stato.equals("Completato")) {                 //eventi in listaCompletati
                view = getLayoutInflater().inflate(R.layout.evento_notificato, null);

                TextView t = view.findViewById(R.id.titoloViewedNot);
                TextView d = view.findViewById(R.id.dataViewedNot);
                TextView o = view.findViewById(R.id.oraViewedNot);
                TextView p = view.findViewById(R.id.prioritaViewedNot);
                TextView c = view.findViewById(R.id.classeViewedNot);
                TextView s = view.findViewById(R.id.statoViewedNot);
                //stampa ogni evento dell'ArrayList
                t.setText(eventiInData.get(i).titolo);
                d.setText(eventiInData.get(i).data);
                o.setText(eventiInData.get(i).orario);
                p.setText(eventiInData.get(i).priorita);
                c.setText(eventiInData.get(i).classe);
                s.setText(eventiInData.get(i).stato);
                //cambia stato
                Button cambiaStato = view.findViewById(R.id.buttonCambiaStato);
                cambiaStato.setText("Cestino");
                cambiaStato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finestra di conferma eliminazione
                        new AlertDialog.Builder(getActivity()).setTitle(eventiInData.get(i).titolo)
                                .setMessage("Eliminare?")
                                .setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for (int j = 0; j < HomeFragment.listaCompletati.size(); j++) {
                                            if (eventiInData.get(i).titolo.equals(HomeFragment.listaCompletati.get(j).titolo)) {
                                                if (eventiInData.get(i).data.equals(HomeFragment.listaCompletati.get(j).data)) {
                                                    if (eventiInData.get(i).orario.equals(HomeFragment.listaCompletati.get(j).orario)) {
                                                        HomeFragment.listaCompletati.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                        eventiInData.remove(i);
                                        saveData();
                                        loadData();
                                        //per ricaricare subito il fragment
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_perdata_container, new PerDataEventiFragment()).addToBackStack(null).commit();
                                    }
                                }).setNegativeButton("No", null).show();
                    }
                });
            }

            return view;
        }
    }


    private void loadData() { //per caricare i dati che sono stati salvati localmente
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json_classes = sharedPreferences.getString("classi", null);
        String json_priority = sharedPreferences.getString("priorita", null);
        String json_listaEventi = sharedPreferences.getString("eventi", null);
        String json_listaInAttesa = sharedPreferences.getString("inAttesa", null);
        String json_listaInCorso = sharedPreferences.getString("inCorso", null);
        String json_listaCompletati = sharedPreferences.getString("completati", null);
        Type type_classi = new TypeToken<ArrayList<String>>() {}.getType();
        Type type_eventi = new TypeToken<ArrayList<Evento>>() {}.getType();
        HomeFragment.classes = gson.fromJson(json_classes, type_classi);
        HomeFragment.priorityNumbers = gson.fromJson(json_priority, type_classi);
        HomeFragment.listaEventi = gson.fromJson(json_listaEventi, type_eventi);
        HomeFragment.listaInAttesa = gson.fromJson(json_listaInAttesa, type_eventi);
        HomeFragment.listaInCorso = gson.fromJson(json_listaInCorso, type_eventi);
        HomeFragment.listaCompletati = gson.fromJson(json_listaCompletati, type_eventi);
    }

    public void saveData() { //per salvare i dati localmente
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json_listaEventi = gson.toJson(HomeFragment.listaEventi);
        String json_listaInAttesa = gson.toJson(HomeFragment.listaInAttesa);
        String json_listaInCorso = gson.toJson(HomeFragment.listaInCorso);
        String json_listaCompletati = gson.toJson(HomeFragment.listaCompletati);
        String json_classes = gson.toJson(HomeFragment.classes);
        String json_priority = gson.toJson(HomeFragment.priorityNumbers);
        editor.putString("eventi", json_listaEventi);
        editor.putString("inAttesa", json_listaInAttesa);
        editor.putString("inCorso", json_listaInCorso);
        editor.putString("completati", json_listaCompletati);
        editor.putString("classi", json_classes);
        editor.putString("priorita", json_priority);
        editor.apply();
    }
}
