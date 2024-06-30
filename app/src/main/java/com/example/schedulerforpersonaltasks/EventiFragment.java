package com.example.schedulerforpersonaltasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

public class EventiFragment extends Fragment {
    //per vedere se negli spinnere di priorità e classe è selezionata la voce "Nessuna"
    static boolean nessunaPriorita = true;
    static boolean nessunaClasse = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventi, container, false);

        HomeFragment.tmp = new ArrayList<>();
        nessunaPriorita = true;
        nessunaClasse = true;

        final Spinner spinnerFiltraPer = v.findViewById(R.id.spinnerFiltraPer);
        final Spinner spinnerOrdine = v.findViewById(R.id.spinnerOrdine);
        final Spinner spinnerMostraPrio = v.findViewById(R.id.spinnerMostraPrio);
        final Spinner spinnerMostraClasse = v.findViewById(R.id.spinnerMostraClasse);

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
                if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    nessunaPriorita = true;
                    //filtra listaEventi
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorDesc);
                    }
                } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    nessunaPriorita = true;
                     HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    nessunaPriorita = false;
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la priorità selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    nessunaPriorita = false;
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe e la priorità selezionate
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                            }
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                }
                //aggiorna subito EventiListaFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_container, new EventiListaFragment()).addToBackStack(null).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //spinner "mostra classe"
        ArrayList<String> cla = new ArrayList<>();
        cla.add(0,"Nessuna");
        for (int i = 0; i < HomeFragment.classes.size(); i++) {
            cla.add(i+1,HomeFragment.classes.get(i));
        }
        ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, cla);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMostraClasse.setAdapter(adapter12);
        spinnerMostraClasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    nessunaClasse = true;
                    //filtra listaEventi
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorDesc);
                    }
                } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    nessunaClasse = false;
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    nessunaClasse = true;
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la priorità selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    nessunaClasse = false;
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe e la priorità selezionate
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                            }
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                }
                //aggiorna subito EventiListaFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_container, new EventiListaFragment()).addToBackStack(null).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //spinner "filtra per"
        ArrayList<String> ordinaPer = new ArrayList<>();
        ordinaPer.add("Data");
        ordinaPer.add("Titolo");
        ordinaPer.add("Priorità");
        ordinaPer.add("Classe");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, ordinaPer);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltraPer.setAdapter(adapter1);
        spinnerFiltraPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    //filtra listaEventi
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorDesc);
                    }
                } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la priorità selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe e la priorità selezionate
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                            }
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                }
                //aggiorna subito EventiListaFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_container, new EventiListaFragment()).addToBackStack(null).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //spinner "ordine"
        ArrayList<String> ordine = new ArrayList<>();
        ordine.add("Crescente");
        ordine.add("Decrescente");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, ordine);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrdine.setAdapter(adapter2);
        spinnerOrdine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    //filtra listaEventi
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.listaEventi, Evento.ClasseComparatorDesc);
                    }
                } else if(spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) { //priorità = "Nessuna"
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la priorità selezionata
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                            HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                } else if(!spinnerMostraPrio.getSelectedItem().equals("Nessuna") && !spinnerMostraClasse.getSelectedItem().equals("Nessuna")) {
                    HomeFragment.tmp = new ArrayList<>();
                    //aggiungi elementi a tmp con la classe e la priorità selezionate
                    for (int i = 0; i < HomeFragment.listaEventi.size(); i++) {
                        if (HomeFragment.listaEventi.get(i).classe.equals(spinnerMostraClasse.getSelectedItem())) {
                            if (HomeFragment.listaEventi.get(i).priorita.equals(spinnerMostraPrio.getSelectedItem())) {
                                HomeFragment.tmp.add(HomeFragment.listaEventi.get(i));
                            }
                        }
                    }
                    //filtra tmp
                    if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Data") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.DataComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Titolo") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.TitoloComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Priorità") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.PrioComparatorDesc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Crescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorAsc);
                    } else if (spinnerFiltraPer.getSelectedItem().equals("Classe") && spinnerOrdine.getSelectedItem().equals("Decrescente")) {
                        Collections.sort(HomeFragment.tmp, Evento.ClasseComparatorDesc);
                    }
                }
                //aggiorna subito EventiListaFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_container, new EventiListaFragment()).addToBackStack(null).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return v;

    }

}
