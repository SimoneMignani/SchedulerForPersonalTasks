package com.example.schedulerforpersonaltasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class NotificheFragment extends Fragment {

    //per vedere se negli spinnere di priorità e classe è selezionata la voce "Nessuna"
    static boolean nessunaPriorita = true;
    static boolean nessunaClasse = true;

    Spinner spinnerMostraPrio;
    Spinner spinnerMostraClasse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nessunaPriorita = true;
        nessunaClasse = true;

        View v = inflater.inflate(R.layout.fragment_notifiche, container, false);
        final BottomNavigationView bNav = v.findViewById(R.id.navNotifiche);
        bNav.setOnNavigationItemSelectedListener(nListener);
        //all'avvio del fragment, visualizza subito NotificheInAttesaFragment
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheInAttesaFragment()).commit();

        spinnerMostraPrio = v.findViewById(R.id.spinnerMostraPrioNot);
        spinnerMostraClasse = v.findViewById(R.id.spinnerMostraClaNot);

        //spinner "mostra priorità"
        ArrayList<String> prio = new ArrayList<>();
        prio.add("Nessuna");
        for (int i = 0; i < HomeFragment.priorityNumbers.size(); i++) {
            prio.add(HomeFragment.priorityNumbers.get(i));
        }
        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, prio);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMostraPrio.setAdapter(adapter11);
        spinnerMostraPrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (HomeFragment.inattesabool) { // se siamo in NotificheInAttesaFragment
                    if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = true;
                    } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = true;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe selezionata
                        for (int i = 0; i < HomeFragment.listaInAttesa.size(); i++) {
                            if (HomeFragment.listaInAttesa.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInAttesa.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la priorità selezionata
                        for (int i = 0; i < HomeFragment.listaInAttesa.size(); i++) {
                            if (HomeFragment.listaInAttesa.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInAttesa.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe e la priorità selezionate
                        for (int i = 0; i < HomeFragment.listaInAttesa.size(); i++) {
                            if (HomeFragment.listaInAttesa.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                if (HomeFragment.listaInAttesa.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                    HomeFragment.tmp.add(HomeFragment.listaInAttesa.get(i));
                                }
                            }
                        }
                    }
                    //aggiorna subito NotificheInAttesaFragment
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheInAttesaFragment()).addToBackStack(null).commit();

                } else if (HomeFragment.incorsobool) { //se siamo in NotificheInCorsoFragment
                    if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = true;
                    } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = true;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe selezionata
                        for (int i = 0; i < HomeFragment.listaInCorso.size(); i++) {
                            if (HomeFragment.listaInCorso.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInCorso.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la priorità selezionata
                        for (int i = 0; i < HomeFragment.listaInCorso.size(); i++) {
                            if (HomeFragment.listaInCorso.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInCorso.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe e la priorità selezionate
                        for (int i = 0; i < HomeFragment.listaInCorso.size(); i++) {
                            if (HomeFragment.listaInCorso.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                if (HomeFragment.listaInCorso.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                    HomeFragment.tmp.add(HomeFragment.listaInCorso.get(i));
                                }
                            }
                        }
                    }
                    //aggiorna subito NotificheInCorsoFragment
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheInCorsoFragment()).addToBackStack(null).commit();

                } else if (HomeFragment.completatibool) { //se siamo in NotificheCompletatiFragment
                    if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = true;
                    } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = true;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe selezionata
                        for (int i = 0; i < HomeFragment.listaCompletati.size(); i++) {
                            if (HomeFragment.listaCompletati.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaCompletati.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la priorità selezionata
                        for (int i = 0; i < HomeFragment.listaCompletati.size(); i++) {
                            if (HomeFragment.listaCompletati.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaCompletati   .get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaPriorita = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe e la priorità selezionate
                        for (int i = 0; i < HomeFragment.listaCompletati.size(); i++) {
                            if (HomeFragment.listaCompletati.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                if (HomeFragment.listaCompletati.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                    HomeFragment.tmp.add(HomeFragment.listaCompletati.get(i));
                                }
                            }
                        }
                    }
                    //aggiorna subito NotificheCompletatiFragment
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheCompletatiFragment()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner "mostra classe"
        ArrayList<String> cla = new ArrayList<>();
        cla.add("Nessuna");
        for (int i = 0; i < HomeFragment.classes.size(); i++) {
            cla.add(HomeFragment.classes.get(i));
        }
        ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, cla);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMostraClasse.setAdapter(adapter12);
        spinnerMostraClasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (HomeFragment.inattesabool) { // se siamo in NotificheInAttesaFragment
                    if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = true;
                    } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe selezionata
                        for (int i = 0; i < HomeFragment.listaInAttesa.size(); i++) {
                            if (HomeFragment.listaInAttesa.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInAttesa.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = true;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la priorità selezionata
                        for (int i = 0; i < HomeFragment.listaInAttesa.size(); i++) {
                            if (HomeFragment.listaInAttesa.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInAttesa.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe e la priorità selezionate
                        for (int i = 0; i < HomeFragment.listaInAttesa.size(); i++) {
                            if (HomeFragment.listaInAttesa.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                if (HomeFragment.listaInAttesa.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                    HomeFragment.tmp.add(HomeFragment.listaInAttesa.get(i));
                                }
                            }
                        }
                    }
                    //aggiorna subito NotificheInAttesaFragment
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheInAttesaFragment()).addToBackStack(null).commit();

                } else if (HomeFragment.incorsobool) { //se siamo in NotificheInCorsoFragment
                    if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = true;
                    } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe selezionata
                        for (int i = 0; i < HomeFragment.listaInCorso.size(); i++) {
                            if (HomeFragment.listaInCorso.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInCorso.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = true;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la priorità selezionata
                        for (int i = 0; i < HomeFragment.listaInCorso.size(); i++) {
                            if (HomeFragment.listaInCorso.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaInCorso.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe e la priorità selezionate
                        for (int i = 0; i < HomeFragment.listaInCorso.size(); i++) {
                            if (HomeFragment.listaInCorso.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                if (HomeFragment.listaInCorso.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                    HomeFragment.tmp.add(HomeFragment.listaInCorso.get(i));
                                }
                            }
                        }
                    }
                    //aggiorna subito NotificheInCorsoFragment
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheInCorsoFragment()).addToBackStack(null).commit();

                } else if (HomeFragment.completatibool) { //se siamo in NotificheCompletatiFragment
                    if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = true;
                    } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe selezionata
                        for (int i = 0; i < HomeFragment.listaCompletati.size(); i++) {
                            if (HomeFragment.listaCompletati.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaCompletati.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = true;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la priorità selezionata
                        for (int i = 0; i < HomeFragment.listaCompletati.size(); i++) {
                            if (HomeFragment.listaCompletati.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaCompletati.get(i));
                            }
                        }
                    } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                        nessunaClasse = false;
                        HomeFragment.tmp = new ArrayList<>();
                        //aggiungi elementi a tmp con la classe e la priorità selezionate
                        for (int i = 0; i < HomeFragment.listaCompletati.size(); i++) {
                            if (HomeFragment.listaCompletati.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                                if (HomeFragment.listaCompletati.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                    HomeFragment.tmp.add(HomeFragment.listaCompletati.get(i));
                                }
                            }
                        }
                    }
                    //aggiorna subito NotificheCompletatiFragment
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, new NotificheCompletatiFragment()).addToBackStack(null).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return v;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectdFragment = null;
                    switch(menuItem.getItemId()) {
                        case R.id.navigation_inattesa:
                            selectdFragment = new NotificheInAttesaFragment();
                            break;
                        case R.id.navigation_incorso:
                            selectdFragment = new NotificheInCorsoFragment();
                            break;
                        case R.id.navigation_completati:
                            selectdFragment = new NotificheCompletatiFragment();
                            break;
                    }
                    spinnerMostraClasse.setSelection(0);
                    spinnerMostraPrio.setSelection(0);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_notifiche_container, selectdFragment).commit();
                    return true;
                }
            };
}
