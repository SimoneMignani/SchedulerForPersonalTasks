package com.example.schedulerforpersonaltasks;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PosticipaDaNotifica extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private boolean timeIsAvailable = false, dateIsAvailable = false;
    private int minuteSelected, hourSelected, daySelected, monthSelected, yearSelected; //valgono in tutta la pagina

    String titolo, data, ora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posticipa_da_notifica);

        loadData();

        titolo = getIntent().getStringExtra("titoloPosticipa");
        data = getIntent().getStringExtra("dataPosticipa");
        ora = getIntent().getStringExtra("oraPosticipa");

        TextView textTitolo = findViewById(R.id.textViewPostTitolo);
        textTitolo.setText(titolo);

        //cambio della lingua del DatePickerDialog in italiano
        Locale loc = Locale.ITALIAN; //questa riga cambia l'intestazione in alto in italiano del DatePicker
        Configuration configuration = this.getResources().getConfiguration();
        configuration.setLocale(loc);//queste ultime righe cambiano il calendario in italiano

        //scelta della data (compare il DataPickerDialog cliccando sulla relativa immagine)
        ImageView imageDate = findViewById(R.id.imageViewD);
        imageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(PosticipaDaNotifica.this, PosticipaDaNotifica.this, year, month, day);
                dpd.getDatePicker().setMinDate(c.getTimeInMillis()); //non si possono selezionare date antecedenti quella attuale
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Annulla", dpd); //cambia il bottone negativo in "Annulla"
                dpd.show();
            }
        });
        //scelta dell'orario (compare il TimePickerDialog cliccando sulla relativa immagine)
        ImageView imageTime = findViewById(R.id.imageViewO);
        imageTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(PosticipaDaNotifica.this, PosticipaDaNotifica.this, hour, minute, true);
                tpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Annulla", tpd);
                tpd.show();
            }
        });
    }

    //mostra la data nel textView relativo
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = "";
        if(day < 10 && month < 9) {
            date = "0" + day + "/0" + (month+1) + "/" + year;
        } else if(day < 10) {
            date = "0" + day + "/" + (month+1) + "/" + year;
        } else if(month < 9) {
            date = day + "/0" + (month+1) + "/" + year;
        } else {
            date = day + "/" + (month+1) + "/" + year;
        }
        TextView textDate = findViewById(R.id.textViewPostData);
        textDate.setText(date);
        dateIsAvailable = true;
        //per evitare qualsiasi problema legato all'orario, ogni volta che si inserisce la data, l'orario diventa vuoto
        TextView textTime = findViewById(R.id.textViewPostOra);
        textTime.setText("");
        timeIsAvailable = false;

        yearSelected = year;
        monthSelected = month;
        daySelected = day;
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        timeIsAvailable = true;
        String time = "";
        if (hour < 10 && minute < 10) {
            time = "0" + hour + ":" + "0" + minute;
        } else if (hour < 10) {
            time = "0" + hour + ":" + minute;
        } else if (minute < 10) {
            time = hour + ":" + "0" + minute;
        } else {
            time = hour + ":" + minute;
        }
        TextView textTime = findViewById(R.id.textViewPostOra);
        textTime.setText(time);

        hourSelected = hour;
        minuteSelected = minute;

        //per controllare che l'orario non sia antecedente quello attuale (nella giornata odierna)
        Calendar rightNow = Calendar.getInstance();
        int currentYear = rightNow.get(Calendar.YEAR);
        int currentMonth = rightNow.get(Calendar.MONTH);
        int currentDay = rightNow.get(Calendar.DAY_OF_MONTH);
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute = rightNow.get(Calendar.MINUTE);
        if(yearSelected == currentYear && monthSelected == currentMonth && daySelected == currentDay) {
            if (hour < currentHour) {
                Toast.makeText(this, "L'orario non può essere antecedente a quello attuale", Toast.LENGTH_SHORT).show();
                textTime.setText("");
                timeIsAvailable = false;
            } else if (hour == currentHour && minute < currentMinute) {
                Toast.makeText(this, "L'orario non può essere antecedente a quello attuale", Toast.LENGTH_SHORT).show();
                textTime.setText("");
                timeIsAvailable = false;
            }
        }
    }


    //quando si clicca sul bottone SALVA
    public void posticipaEvento(View view) {
        TextView textTitolo = findViewById(R.id.textViewPostTitolo);
        TextView textDate = findViewById(R.id.textViewPostData);
        TextView textTime = findViewById(R.id.textViewPostOra);
        if (!dateIsAvailable) {
            Toast.makeText(this, "IMPOSSIBILE CONTINUARE: non è stata inserita la data", Toast.LENGTH_SHORT).show();
        } else if (!timeIsAvailable) {
            Toast.makeText(this, "IMPOSSIBILE CONTINUARE: non è stato inserito l'orario", Toast.LENGTH_SHORT).show();
        } else {
            //***** per notifiche ********
            Intent intent = new Intent(PosticipaDaNotifica.this, AlarmReceiver.class);
            intent.putExtra("titolo", textTitolo.getText().toString());
            intent.putExtra("data", textDate.getText().toString());
            intent.putExtra("ora", textTime.getText().toString());
            PendingIntent alarmIntent = PendingIntent.getBroadcast(PosticipaDaNotifica.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

            //create time
            Calendar start = Calendar.getInstance();
            start.set(Calendar.YEAR, yearSelected);
            start.set(Calendar.MONTH, monthSelected);
            start.set(Calendar.DAY_OF_MONTH, daySelected);
            start.set(Calendar.HOUR_OF_DAY, hourSelected);
            start.set(Calendar.MINUTE, minuteSelected);
            start.set(Calendar.SECOND, 0);
            long alarmStarTime = start.getTimeInMillis();

            alarm.set(AlarmManager.RTC_WAKEUP, alarmStarTime, alarmIntent);
            //***********************

            Intent i = new Intent(this, MainActivity.class); //intent che avvia l'activity MainActivity
            for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                if(titolo.equals(HomeFragment.listaEventi.get(j).titolo)) {
                    if(data.equals(HomeFragment.listaEventi.get(j).data)) {
                        if(ora.equals(HomeFragment.listaEventi.get(j).orario)) {
                            HomeFragment.listaEventi.get(j).setData(textDate.getText().toString());
                            HomeFragment.listaEventi.get(j).setOrario(textTime.getText().toString());
                        }
                    }

                }
            }
            for (int j = 0; j < HomeFragment.listaInAttesa.size(); j++) {
                if(titolo.equals(HomeFragment.listaInAttesa.get(j).titolo)) {
                    if(data.equals(HomeFragment.listaInAttesa.get(j).data)) {
                        if(ora.equals(HomeFragment.listaInAttesa.get(j).orario)) {
                            HomeFragment.listaInAttesa.remove(j);
                        }
                    }

                }
            }
            MainActivity.posticipato = true;
            saveData();
            startActivity(i);
        }
    }


    //quando si clicca sul bottone ANNULLA
    public void annullaPosticipa(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    private void loadData() { //per caricare i dati che sono stati salvati localmente
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
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
    }

    public void saveData() { //per salvare i dati localmente
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json_listaEventi = gson.toJson(HomeFragment.listaEventi);
        String json_listaInAttesa = gson.toJson(HomeFragment.listaInAttesa);
        editor.putString("eventi", json_listaEventi);
        editor.putString("inAttesa", json_listaInAttesa);
        editor.apply();
    }
}
