package com.example.schedulerforpersonaltasks;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class VisualizzaEventiPerData extends AppCompatActivity {

    static String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_eventi_per_data);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_eventi_perdata_container, new PerDataEventiFragment()).commit();

        final int day = getIntent().getIntExtra("day",0);
        final int month = getIntent().getIntExtra("month",0);
        final int year = getIntent().getIntExtra("year",0);
        data = "";
        if(day < 10 && month < 10) {
            data = "0" + day + "/0" + month + "/" + year;
        } else if(day < 10) {
            data = "0" + day + "/" + month + "/" + year;
        } else if(month < 10) {
            data = day + "/0" + month + "/" + year;
        } else {
            data = day + "/" + month + "/" + year;
        }
        TextView dataSelezionata = findViewById(R.id.textViewDataCal);
        dataSelezionata.setText(data);

        //quando si clicca indietro, si torna alla schermata precedente
        ImageView back = findViewById(R.id.imageViewIndietro);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VisualizzaEventiPerData.this, MainActivity.class);
                startActivity(i);
            }
        });

        //quando si clicca su aggiungi evento, si apre TaskCreatorActivity con la data già impostata
        Button aggiungi = findViewById(R.id.buttonAggiungiInData);
        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH)+1;
        int dayNow = calendar.get(Calendar.DAY_OF_MONTH);
        //impossibile aggiungere eventi in date antecedenti quella attuale
        if(year < yearNow) {
            aggiungi.setEnabled(false);
            aggiungi.setBackgroundColor(Color.TRANSPARENT);
        } else if(year == yearNow && month < monthNow) {
            aggiungi.setEnabled(false);
            aggiungi.setBackgroundColor(Color.TRANSPARENT);
        } else if(year == yearNow && month == monthNow && day < dayNow) {
            aggiungi.setEnabled(false);
            aggiungi.setBackgroundColor(Color.TRANSPARENT);
        } else {
            aggiungi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.eventoPerData = true;
                    Intent intent = new Intent(VisualizzaEventiPerData.this, TaskCreatorActivity.class);
                    intent.putExtra("day", day);
                    intent.putExtra("month", month - 1);
                    intent.putExtra("year", year);
                    startActivity(intent);
                }
            });
        }

    }
}
