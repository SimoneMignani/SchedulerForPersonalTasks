package com.example.schedulerforpersonaltasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class NotificheInAttesaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        HomeFragment.inattesabool = true; //per controllare che siamo in NotificheInAttesaFragment
        HomeFragment.incorsobool = false;
        HomeFragment.completatibool = false;

        //******per visualizzare elementi nella lista "in attesa"******
        if(!HomeFragment.listaEventi.isEmpty()) {
            Calendar rightNow = Calendar.getInstance();
            int currentYear = rightNow.get(Calendar.YEAR);
            int currentMonth = rightNow.get(Calendar.MONTH)+1;
            int currentDay = rightNow.get(Calendar.DAY_OF_MONTH);
            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
            int currentMinute = rightNow.get(Calendar.MINUTE);
            //la data è appositamente inversa per controllare che la data dell'evento sia minore di quella attuale
            //(se fosse normale non si riuscirebbe a fare il confronto)
            String dateIn = "";
            if(currentDay < 10 && currentMonth < 10) {
                dateIn = currentYear + "0" + currentMonth + "0" + currentDay;
            } else if(currentDay < 10) {
                dateIn = currentYear + currentMonth +"0"+currentDay;
            } else if(currentMonth < 10) {
                dateIn = currentYear + "0"+currentMonth +currentDay;
            } else {
                dateIn = currentYear +currentMonth +currentDay+"";
            }

            String time = "";
            if (currentHour < 10 && currentMinute < 10) {
                time = "0" + currentHour + ":" + "0" + currentMinute;
            } else if (currentHour < 10) {
                time = "0" + currentHour + ":" + currentMinute;
            } else if (currentMinute < 10) {
                time = currentHour + ":" + "0" + currentMinute;
            } else {
                time = currentHour + ":" + currentMinute;
            }

            for (int i = 0; i < HomeFragment.listaEventi.size(); i++) { //scorre tutto listaEventi
                String[] di = HomeFragment.listaEventi.get(i).data.split("/");
                int d = dateIn.compareTo(di[2]+di[1]+di[0]+"");
                int t = time.compareTo(HomeFragment.listaEventi.get(i).orario);
                if(HomeFragment.listaInAttesa.isEmpty()) { //se lista in attesa vuota
                    //orario evento antecedente o uguale all'orario attuale (nella giorna attuale) oppure data evento antecendente alla data attuale
                    if ((d == 0 && t >= 0) || (d > 0)) {
                        HomeFragment.listaInAttesa.add(HomeFragment.listaEventi.get(i));
                        saveData();
                        loadData();
                    }
                }
                if(!HomeFragment.listaInAttesa.isEmpty()) { //se lista in attesa non vuota
                    //orario evento antecedente o uguale all'orario attuale (nella giorna attuale) oppure data evento antecendente alla data attuale
                    if ((d == 0 && t >= 0) || (d > 0)) {
                        boolean insert = true; //per verificare che l'evento non ci sia già
                        for (int j = 0; j < HomeFragment.listaInAttesa.size(); j++) {
                            //verifico se l'evento esiste già nella listaInAttesa
                            if (HomeFragment.listaEventi.get(i).titolo.equals(HomeFragment.listaInAttesa.get(j).titolo)) {
                                if (HomeFragment.listaEventi.get(i).data.equals(HomeFragment.listaInAttesa.get(j).data)) {
                                    if (HomeFragment.listaEventi.get(i).orario.equals(HomeFragment.listaInAttesa.get(j).orario)) {
                                        insert = false; //esiste già
                                    }
                                }
                            }
                            if(j == HomeFragment.listaInAttesa.size()-1){ //una volta controllato tutto listaInAttesa
                                if (insert) { //se l'evento non esiste già in listaInAttesa, inseriscilo
                                    HomeFragment.listaInAttesa.add(HomeFragment.listaEventi.get(i));
                                    saveData();
                                    loadData();
                                }
                            }
                        }
                    }
                }
            }
        }
        //******************************
        View v;
        if(HomeFragment.listaInAttesa.isEmpty()) { //se non ci sono eventi, visualizza "nessun evento"
            v = getLayoutInflater().inflate(R.layout.nessun_evento_creato,null);
            TextView noEvent = v.findViewById(R.id.textViewNEC);
            noEvent.setText("Nessun evento");
            return v;
        } else if(HomeFragment.tmp.isEmpty() && !NotificheFragment.nessunaPriorita) { //se si filtra per qualche priorità
            v = getLayoutInflater().inflate(R.layout.nessun_evento_creato, null);
            TextView noEvent = v.findViewById(R.id.textViewNEC);
            noEvent.setText("Nessun evento visualizzabile");
            return v;
        } else if(HomeFragment.tmp.isEmpty() && !NotificheFragment.nessunaClasse) { //se si filtra per qualche classe
            v = getLayoutInflater().inflate(R.layout.nessun_evento_creato, null);
            TextView noEvent = v.findViewById(R.id.textViewNEC);
            noEvent.setText("Nessun evento visualizzabile");
            return v;
        } else { //se no visualizza la lista degli eventi
            v = inflater.inflate(R.layout.fragment_notifiche_inattesa, container, false);
            ListView listview = v.findViewById(R.id.listViewInAttesa);
            customAdapter custom = new customAdapter();
            listview.setAdapter(custom);
            return v;
        }
    }


    class customAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(NotificheFragment.nessunaPriorita && NotificheFragment.nessunaClasse) {
                return HomeFragment.listaInAttesa.size();
            } else {
                return HomeFragment.tmp.size();
            }
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
            view = getLayoutInflater().inflate(R.layout.evento_notificato,null);

            TextView t = view.findViewById(R.id.titoloViewedNot);
            TextView d = view.findViewById(R.id.dataViewedNot);
            TextView o = view.findViewById(R.id.oraViewedNot);
            TextView p = view.findViewById(R.id.prioritaViewedNot);
            TextView c = view.findViewById(R.id.classeViewedNot);
            TextView s = view.findViewById(R.id.statoViewedNot);

            //non si filtra per nessuna priorità ne classe
            if(NotificheFragment.nessunaPriorita && NotificheFragment.nessunaClasse) {
                //stampa ogni evento dell'ArrayList
                t.setText(HomeFragment.listaInAttesa.get(i).titolo);
                d.setText(HomeFragment.listaInAttesa.get(i).data);
                o.setText(HomeFragment.listaInAttesa.get(i).orario);
                p.setText(HomeFragment.listaInAttesa.get(i).priorita);
                c.setText(HomeFragment.listaInAttesa.get(i).classe);
                HomeFragment.listaInAttesa.get(i).setStato("In attesa");
                s.setText(HomeFragment.listaInAttesa.get(i).stato);
                //cambia stato
                Button cambiaStato = view.findViewById(R.id.buttonCambiaStato);
                cambiaStato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finestra di conferma eliminazione
                        new AlertDialog.Builder(getActivity()).setTitle(HomeFragment.listaInAttesa.get(i).titolo)
                                .setMessage("Cambiare stato a 'In corso' ?")
                                .setPositiveButton("Cambia", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //quando un evento passa a "in corso", si elimina lo stesso evento dalla sezione Eventi
                                        for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                                            if (HomeFragment.listaInAttesa.get(i).titolo.equals(HomeFragment.listaEventi.get(j).titolo)) {
                                                if (HomeFragment.listaInAttesa.get(i).data.equals(HomeFragment.listaEventi.get(j).data)) {
                                                    if (HomeFragment.listaInAttesa.get(i).orario.equals(HomeFragment.listaEventi.get(j).orario)) {
                                                        HomeFragment.listaEventi.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                        HomeFragment.listaInAttesa.get(i).setStato("In corso");
                                        HomeFragment.listaInCorso.add(HomeFragment.listaInAttesa.get(i));
                                        HomeFragment.listaInAttesa.remove(i);
                                        saveData();
                                        loadData();
                                        //per ricaricare subito il fragment
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheInAttesaFragment()).addToBackStack(null).commit();
                                    }
                                }).setNegativeButton("No", null).show();
                    }
                });
            } else { //se si filtra per una priorità o per una classe (o entrambi)
                //stampa ogni evento dell'ArrayList
                t.setText(HomeFragment.tmp.get(i).titolo);
                d.setText(HomeFragment.tmp.get(i).data);
                o.setText(HomeFragment.tmp.get(i).orario);
                p.setText(HomeFragment.tmp.get(i).priorita);
                c.setText(HomeFragment.tmp.get(i).classe);
                HomeFragment.tmp.get(i).setStato("In attesa");
                s.setText(HomeFragment.tmp.get(i).stato);
                //cambia stato
                Button cambiaStato = view.findViewById(R.id.buttonCambiaStato);
                cambiaStato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finestra di conferma eliminazione
                        new AlertDialog.Builder(getActivity()).setTitle(HomeFragment.tmp.get(i).titolo)
                                .setMessage("Cambiare stato a 'In corso' ?")
                                .setPositiveButton("Cambia", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //quando un evento passa a "in corso", si elimina lo stesso evento dalla sezione Eventi
                                        for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                                            if (HomeFragment.tmp.get(i).titolo.equals(HomeFragment.listaEventi.get(j).titolo)) {
                                                if (HomeFragment.tmp.get(i).data.equals(HomeFragment.listaEventi.get(j).data)) {
                                                    if (HomeFragment.tmp.get(i).orario.equals(HomeFragment.listaEventi.get(j).orario)) {
                                                        HomeFragment.listaEventi.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                        //rimuove da listaInAttesa lo stesso evento rimosso dalla lista tmp
                                        for (int j = 0; j < HomeFragment.listaInAttesa.size(); j++) {
                                            if (HomeFragment.tmp.get(i).titolo.equals(HomeFragment.listaInAttesa.get(j).titolo)) {
                                                if (HomeFragment.tmp.get(i).data.equals(HomeFragment.listaInAttesa.get(j).data)) {
                                                    if (HomeFragment.tmp.get(i).orario.equals(HomeFragment.listaInAttesa.get(j).orario)) {
                                                        HomeFragment.listaInAttesa.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                        HomeFragment.tmp.get(i).setStato("In corso");
                                        HomeFragment.listaInCorso.add(HomeFragment.tmp.get(i));
                                        HomeFragment.tmp.remove(i);
                                        saveData();
                                        loadData();
                                        //per ricaricare subito il fragment
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheInAttesaFragment()).addToBackStack(null).commit();
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
