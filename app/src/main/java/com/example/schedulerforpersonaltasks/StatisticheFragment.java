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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticheFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistiche, container, false);
        view = v;
        final Spinner spinner1 = v.findViewById(R.id.spinnerClasPrio);
        final Spinner spinner2 = v.findViewById(R.id.spinnerPercQuant);

        //spinner1 priorità-classi-notifiche
        ArrayList<String> what = new ArrayList<String>();
        what.add("Classi");
        what.add("Priorità");
        what.add("Notifiche");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, what);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spinner1.getSelectedItem().equals("Classi") && spinner2.getSelectedItem().equals("%")) {
                    setupPieChartClassesPerc();
                } else if(spinner1.getSelectedItem().equals("Classi") && spinner2.getSelectedItem().equals("n")) {
                    setupPieChartClassesCount();
                } else if(spinner1.getSelectedItem().equals("Priorità") && spinner2.getSelectedItem().equals("%")) {
                    setupPieChartPriorityPerc();
                } else if(spinner1.getSelectedItem().equals("Priorità") && spinner2.getSelectedItem().equals("n")) {
                    setupPieChartPriorityCount();
                } else if(spinner1.getSelectedItem().equals("Notifiche") && spinner2.getSelectedItem().equals("%")) {
                    setupPieChartNotifyPerc();
                } else if(spinner1.getSelectedItem().equals("Notifiche") && spinner2.getSelectedItem().equals("n")) {
                    setupPieChartNotifyCount();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //spinner2 %-n
        ArrayList<String> how = new ArrayList<String>();
        how.add("%");
        how.add("n");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, how);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spinner1.getSelectedItem().equals("Classi") && spinner2.getSelectedItem().equals("%")) {
                    setupPieChartClassesPerc();
                } else if(spinner1.getSelectedItem().equals("Classi") && spinner2.getSelectedItem().equals("n")) {
                    setupPieChartClassesCount();
                } else if(spinner1.getSelectedItem().equals("Priorità") && spinner2.getSelectedItem().equals("%")) {
                    setupPieChartPriorityPerc();
                } else if(spinner1.getSelectedItem().equals("Priorità") && spinner2.getSelectedItem().equals("n")) {
                    setupPieChartPriorityCount();
                } else if(spinner1.getSelectedItem().equals("Notifiche") && spinner2.getSelectedItem().equals("%")) {
                    setupPieChartNotifyPerc();
                } else if(spinner1.getSelectedItem().equals("Notifiche") && spinner2.getSelectedItem().equals("n")) {
                    setupPieChartNotifyCount();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        return v;
    }


    //utlizzo classi in percentuale
    private void setupPieChartClassesPerc() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        float[] count = new float[HomeFragment.classes.size()];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        //aggiungere elementi al PieChart
        for (int i = 0; i < HomeFragment.classes.size(); i++) {
            for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                if(HomeFragment.listaEventi.get(j).classe.equals(HomeFragment.classes.get(i))) {
                    count[i]++;
                }
            }
            if(count[i] > 0) {
                count[i] = count[i]/HomeFragment.listaEventi.size();
                count[i] = count[i]*100;
                pieEntries.add(new PieEntry(count[i],HomeFragment.classes.get(i)));
            }
        }
        PieDataSet dataSet = new PieDataSet(pieEntries,"");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        //visualizzare il chart
        PieChart chart = view.findViewById(R.id.chart);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleRadius(0f);
        Description d = new Description();
        d.setText("Utilizzo in % di ciascuna classe");
        d.setTextSize(10f);
        chart.setDescription(d);
        chart.setData(data);
        chart.animateY(500);
        chart.invalidate();
    }


    //utilizzo classi in numero
    private void setupPieChartClassesCount() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        int[] count = new int[HomeFragment.classes.size()];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        //aggiungere elementi al PieChart
        for (int i = 0; i < HomeFragment.classes.size(); i++) {
            for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                if(HomeFragment.listaEventi.get(j).classe.equals(HomeFragment.classes.get(i))) {
                    count[i]++;
                }
            }
            if(count[i] > 0) pieEntries.add(new PieEntry(count[i],HomeFragment.classes.get(i)));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        //visualizzare il chart
        PieChart chart = view.findViewById(R.id.chart);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleRadius(0f);
        Description d = new Description();
        d.setText("Frequenza di utilizzo di ciascuna classe");
        d.setTextSize(10f);
        chart.setDescription(d);
        chart.setData(data);
        chart.animateY(500);
        chart.invalidate();
    }


    //utlizzo priorità in percentuale
    private void setupPieChartPriorityPerc() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        float[] count = new float[HomeFragment.priorityNumbers.size()];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        //aggiungere elementi al PieChart
        for (int i = 0; i < HomeFragment.priorityNumbers.size(); i++) {
            for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                if(HomeFragment.listaEventi.get(j).priorita.equals(HomeFragment.priorityNumbers.get(i))) {
                    count[i]++;
                }
            }
            if(count[i] > 0) {
                count[i] = count[i]/HomeFragment.listaEventi.size();
                count[i] = count[i]*100;
                pieEntries.add(new PieEntry(count[i],HomeFragment.priorityNumbers.get(i)));
            }
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        //visualizzare il chart
        PieChart chart = view.findViewById(R.id.chart);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleRadius(0f);
        Description d = new Description();
        d.setText("Utilizzo in % di ciascuna priorità");
        d.setTextSize(10f);
        chart.setDescription(d);
        chart.setData(data);
        chart.animateY(500);
        chart.invalidate();
    }


    //utlizzo priorità in numero
    private void setupPieChartPriorityCount() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        int[] count = new int[HomeFragment.priorityNumbers.size()];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        //aggiungere elementi al PieChart
        for (int i = 0; i < HomeFragment.priorityNumbers.size(); i++) {
            for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                if(HomeFragment.listaEventi.get(j).priorita.equals(HomeFragment.priorityNumbers.get(i))) {
                    count[i]++;
                }
            }
            if(count[i] > 0) pieEntries.add(new PieEntry(count[i],HomeFragment.priorityNumbers.get(i)));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        //visualizzare il chart
        PieChart chart = view.findViewById(R.id.chart);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleRadius(0f);
        Description d = new Description();
        d.setText("Frequenza di utilizzo di ciascuna priorità");
        d.setTextSize(10f);
        chart.setDescription(d);
        chart.setData(data);
        chart.animateY(500);
        chart.invalidate();
    }

    //tipologie notifiche in percentuale
    private void setupPieChartNotifyPerc() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        if(HomeFragment.listaInAttesa.size() > 0) {
            float a = ((float)HomeFragment.listaInAttesa.size())/(HomeFragment.listaInAttesa.size() + HomeFragment.listaInCorso.size() + HomeFragment.listaCompletati.size());
            a = a*100;
            pieEntries.add(new PieEntry(a, "In attesa"));
        }
        if(HomeFragment.listaInCorso.size() > 0)  {
            float b = ((float) HomeFragment.listaInCorso.size())/(HomeFragment.listaInAttesa.size() + HomeFragment.listaInCorso.size() + HomeFragment.listaCompletati.size());
            b = b*100;
            pieEntries.add(new PieEntry(b, "In corso"));
        }
        if(HomeFragment.listaCompletati.size() > 0) {
            float c = ((float)HomeFragment.listaCompletati.size())/(HomeFragment.listaInAttesa.size() + HomeFragment.listaInCorso.size() + HomeFragment.listaCompletati.size());
            c = c*100;
            pieEntries.add(new PieEntry(c, "Completati"));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        //visualizzare il chart
        PieChart chart = view.findViewById(R.id.chart);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleRadius(0f);
        Description d = new Description();
        d.setText("Notifiche in % per ogni tipologia");
        d.setTextSize(10f);
        chart.setDescription(d);
        chart.setData(data);
        chart.animateY(500);
        chart.invalidate();
    }


    //Numero di notifiche per ogni tipologia
    private void setupPieChartNotifyCount() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        //aggiungere elementi al PieChart
        if(HomeFragment.listaInAttesa.size() > 0) pieEntries.add(new PieEntry(HomeFragment.listaInAttesa.size(), "In attesa"));
        if(HomeFragment.listaInCorso.size() > 0) pieEntries.add(new PieEntry(HomeFragment.listaInCorso.size(), "In corso"));
        if(HomeFragment.listaCompletati.size() > 0) pieEntries.add(new PieEntry(HomeFragment.listaCompletati.size(), "Completati"));
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        //visualizzare il chart
        PieChart chart = view.findViewById(R.id.chart);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleRadius(0f);
        Description d = new Description();
        d.setText("Numero di notifiche per ogni tipologia");
        d.setTextSize(10f);
        chart.setDescription(d);
        chart.setData(data);
        chart.animateY(500);
        chart.invalidate();
    }



}
