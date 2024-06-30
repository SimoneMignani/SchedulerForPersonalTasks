package com.example.schedulerforpersonaltasks;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HomeFragment extends Fragment implements Serializable {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ITALIAN);
    private TextView currentMonth;
    private CompactCalendarView compactCalendarView;

    static ArrayList<String> classes =  new ArrayList<String>();
    static ArrayList<String> priorityNumbers =  new ArrayList<String>();
    static ArrayList<Evento> listaEventi = new ArrayList<Evento>();
    static ArrayList<Evento> listaInAttesa = new ArrayList<Evento>();
    static ArrayList<Evento> listaInCorso = new ArrayList<Evento>();
    static ArrayList<Evento> listaCompletati = new ArrayList<Evento>();
    static ArrayList<Evento> tmp = new ArrayList<Evento>(); //temporaneo, non viene mai salvato il contenuto
    //per controllare in che fragment delle notifiche siamo
    static boolean inattesabool = false;
    static boolean incorsobool = false;
    static boolean completatibool = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //serve per avere l'intestazione del datepickerdialog in italiano (in TaskCreatorActivity e ModificaEventoActivity)
        Locale loc = Locale.ITALIAN;
        Configuration configuration = this.getResources().getConfiguration();
        configuration.setLocale(loc);

        Button nuovoEvento = view.findViewById(R.id.buttonAggiungiEvento); //nei fragment non si può utilizzare solamente findViewById, ma deve essere preceduto da una View
        nuovoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskCreatorActivity.class); //intent che avvia l'activity TaskCreatorActivity
                startActivity(intent); //avvio dell'activity definita dall'Intent "intent"
            }
        });

        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        //cambio lingua calendario in italiano
        Calendar c = Calendar.getInstance();
        TimeZone tz = c.getTimeZone();
        compactCalendarView.setLocale(tz, Locale.ITALIAN);
        //visualizzo il mese corrente
        currentMonth = view.findViewById(R.id.tw_month);
        currentMonth.setText(simpleDateFormat.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        circleUnderDate();  //aggiunge i pallini
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(java.util.Date dateClicked) {
                //assegno un nuovo formato alla data
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = df.format(dateClicked);
                String[] arrDate = strDate.split("/");
                Intent intent = new Intent(getActivity(), VisualizzaEventiPerData.class);
                intent.putExtra("day", Integer.parseInt(arrDate[0]));
                intent.putExtra("month", Integer.parseInt(arrDate[1]));
                intent.putExtra("year", Integer.parseInt(arrDate[2]));
                getActivity().startActivity(intent);
            }

            @Override
            public void onMonthScroll(java.util.Date firstDayOfNewMonth) {
                currentMonth.setText(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });

        return view;
    }

    //pallini sotto le date che hanno eventi
    private void circleUnderDate(){
        for (int i = 0; i < listaEventi.size() ; i++) {  //gli eventi da notificare hanno pallini blu
            String[] d = listaEventi.get(i).data.split("/");
            Date date = Date.valueOf(d[2]+"-"+d[1]+"-"+d[0]); //anno-mese-giorno
            compactCalendarView.addEvent( new Event(Color.BLUE, date.getTime(), listaEventi.get(i).titolo));
        }
        for (int i = 0; i < listaInCorso.size() ; i++) {  //gli in corso hanno pallini rossi
            String[] d = listaInCorso.get(i).data.split("/");
            Date date = Date.valueOf(d[2]+"-"+d[1]+"-"+d[0]); //anno-mese-giorno
            compactCalendarView.addEvent( new Event(Color.RED, date.getTime(), listaInCorso.get(i).titolo));
        }
        for (int i = 0; i < listaCompletati.size() ; i++) {  //i completati hanno pallini verdi
            String[] d = listaCompletati.get(i).data.split("/");
            Date date = Date.valueOf(d[2]+"-"+d[1]+"-"+d[0]); //anno-mese-giorno
            compactCalendarView.addEvent( new Event(Color.GREEN, date.getTime(), listaCompletati.get(i).titolo));
        }
    }

}
