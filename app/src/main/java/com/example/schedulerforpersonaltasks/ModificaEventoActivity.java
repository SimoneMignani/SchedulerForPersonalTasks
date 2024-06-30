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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ModificaEventoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    private EditText textTitolo;
    private EditText textAggRimClasse;
    private TextView textDate;
    private TextView textTime;
    private ImageView imageDate;
    private ImageView imageTime;

    Spinner spinnerPriority;
    Spinner spinnerClass;

    ArrayAdapter<String> adapter2;

    private int minuteSelected, hourSelected, daySelected, monthSelected, yearSelected; //valgono in tutta la pagina
    private boolean timeIsAvailable = true;
    private boolean dateIsAvailable = true;

    String titoloEvento, dataEvento, orarioEvento, prioritaEvento, classeEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creator);

        textTitolo = (EditText) findViewById(R.id.editTextTitolo);
        textDate = (TextView) findViewById(R.id.textViewData);
        textTime = (TextView) findViewById(R.id.textViewOrario);

        titoloEvento = getIntent().getStringExtra("titolo");
        dataEvento = getIntent().getStringExtra("data");
        orarioEvento = getIntent().getStringExtra("orario");
        prioritaEvento = getIntent().getStringExtra("priorita");
        classeEvento = getIntent().getStringExtra("classe");

        //imposto il titolo, la data e l'ora come nell'evento modificato
        textTitolo.setText(titoloEvento);
        textDate.setText(dataEvento);
        textTime.setText(orarioEvento);

        //faccio lo split della data per impostare la data nel DatePickerDialog uguale a quella dell'evento da modificare
        String[] data = dataEvento.split("/");
        String dataEventoInvertita = data[2]+"/"+data[1]+"/"+data[0]; //anno/mese/giorno
        String giorno = data[0];
        int g = -1;
        if(giorno.compareTo("10") < 0) { //converto i giorni in interi
            for (int i = 1; i < 10; i++) {
                if(giorno.equals("0"+i)) {
                    g = i;
                }
            }
        } else {
            g = Integer.parseInt(giorno);
        }
        final int giornoDaModif = g; //per DatePicker
        daySelected = giornoDaModif;

        String mese = data[1];
        int m = -1;
        if(mese.compareTo("10") < 0) { //converto i mesi in interi
            for (int i = 1; i < 10; i++) {
                if(mese.equals("0"+i)) {
                    m = i-1;
                }
            }
        } else {
            m = Integer.parseInt(mese)-1;
        }
        final int meseDaModif = m; //per DatePicker
        monthSelected = meseDaModif;

        String anno = data[2];
        final int annoDaModif = Integer.parseInt(anno);
        yearSelected = annoDaModif;

        String[] ora = orarioEvento.split(":");
        hourSelected = Integer.parseInt(ora[0]);
        minuteSelected = Integer.parseInt(ora[1]);

        //se la data dell'evento è successiva a quella attuale, impostare la data vuota
        //se l'orario dell'evento è uguale o maggiore a quello attuale (nella giornata odierna), impostare l'orario vuoto
        Calendar rightNow = Calendar.getInstance();
        int currentYear = rightNow.get(Calendar.YEAR);
        int currentMonth = rightNow.get(Calendar.MONTH);
        int currentDay = rightNow.get(Calendar.DAY_OF_MONTH);
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute = rightNow.get(Calendar.MINUTE);
        String giornataOdierna = "";
        if(currentDay < 10 && currentMonth < 9) {
            giornataOdierna = currentYear + "/0" + (currentMonth+1) + "/0" + currentDay;
        } else if(currentDay < 10) {
            giornataOdierna = currentYear + "/" + (currentMonth+1) + "/0" + currentDay;
        } else if(currentMonth < 9) {
            giornataOdierna = currentYear + "/0" + (currentMonth+1) + "/" + currentDay;
        } else {
            giornataOdierna = currentYear + "/" + (currentMonth+1) + "/" + currentDay;
        }
        String oraAttuale = "";
        if (currentHour < 10 && currentMinute < 10) {
            oraAttuale = "0" + currentHour + ":" + "0" + currentMinute;
        } else if (currentHour < 10) {
            oraAttuale = "0" + currentHour + ":" + currentMinute;
        } else if (currentMinute < 10) {
            oraAttuale = currentHour + ":" + "0" + currentMinute;
        } else {
            oraAttuale = currentHour + ":" + currentMinute;
        }
        if(giornataOdierna.compareTo(dataEventoInvertita) > 0) {
            textDate.setText("");
            textTime.setText("");
            dateIsAvailable = false;
            timeIsAvailable = false;
        }
        if(giornataOdierna.equals(dataEventoInvertita) && oraAttuale.compareTo(orarioEvento) >= 0) {
            textTime.setText("");
            timeIsAvailable = false;
        }

        //cambio della lingua del DatePickerDialog in italiano
        Locale loc = Locale.ITALIAN; //questa riga cambia l'intestazione in alto in italiano del DatePicker
        Configuration configuration = this.getResources().getConfiguration();
        configuration.setLocale(loc);//queste ultime righe cambiano il calendario in italiano

        //imposta lo spinner delle priorità con il valore iniziale uguale a quello dell'evento modificato
        spinnerPriority = findViewById(R.id.spinnerPriorita);
        if(HomeFragment.priorityNumbers.isEmpty()) {
            HomeFragment.priorityNumbers.add("1");
            HomeFragment.priorityNumbers.add("2");
            HomeFragment.priorityNumbers.add("3");
            HomeFragment.priorityNumbers.add("4");
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, HomeFragment.priorityNumbers);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter1);
        if (prioritaEvento != null) {
            int spiPosition = adapter1.getPosition(prioritaEvento);
            spinnerPriority.setSelection(spiPosition);
        }

        //imposta lo spinner delle classi con il valore iniziale uguale a quello dell'evento modificato
        spinnerClass = findViewById(R.id.spinnerClasse);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, HomeFragment.classes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter2);
        if (classeEvento != null) {
            int spinnerPosition = adapter2.getPosition(classeEvento);
            spinnerClass.setSelection(spinnerPosition);
        }

        //scelta della data (compare il DataPickerDialog cliccando sulla relativa immagine)
        imageDate = findViewById(R.id.imageViewData);
        imageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(ModificaEventoActivity.this, ModificaEventoActivity.this, annoDaModif, meseDaModif, giornoDaModif);
                dpd.getDatePicker().setMinDate(c.getTimeInMillis()); //non si possono selezionare date antecedenti quella attuale
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Annulla", dpd); //cambia il bottone negativo in "Annulla"
                dpd.show();
            }
        });
        //scelta dell'orario (compare il TimePickerDialog cliccando sulla relativa immagine)
        imageTime = findViewById(R.id.imageViewOrario);
        imageTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(ModificaEventoActivity.this, ModificaEventoActivity.this, hour, minute, true);
                tpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Annulla", tpd);
                tpd.show();
            }
        });

        //quando si clicca il bottome aggiungi classe, viene aggiunta alle classi la stringa nel editText
        Button buttonAddClass = findViewById(R.id.buttonAddClass);
        buttonAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClass();
            }
        });

        //quando si clicca il bottome rimuovi classe, viene rimossa la classe corrispondente alla stringa nel editText
        Button buttonRemoveClass = findViewById(R.id.buttonRemoveClass);
        buttonRemoveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeClass();
            }
        });
    }

    public void addClass() {
        textAggRimClasse = (EditText) findViewById(R.id.editTextAddClass);
        boolean truth = true;  //per vedere se la stringa nel edittext è corretta
        if(textAggRimClasse.getText().toString().equals("")) {
            Toast.makeText(this, "Nessuna classe inserita", Toast.LENGTH_SHORT).show();
            truth = false;
        } else {
            for (int i = 0; i < HomeFragment.classes.size(); i++) {
                if(HomeFragment.classes.get(i).toLowerCase().equals(textAggRimClasse.getText().toString().toLowerCase())) {  //se la stringa nell'edittext è uguale a una classe
                    Toast.makeText(this,"Classe già esistente",Toast.LENGTH_SHORT).show();
                    truth = false;
                    break;
                }
            }
        }
        if(truth) {
            HomeFragment.classes.add(textAggRimClasse.getText().toString());
            Toast.makeText(this,"AGGIUNTO!",Toast.LENGTH_SHORT).show();
            saveData();
            loadData();
            textAggRimClasse.setText("");
        }
    }


    public void removeClass() {
        textAggRimClasse = (EditText) findViewById(R.id.editTextAddClass);
        boolean error = true;  //per vedere se la stringa nel edittext è corretta
        boolean utilizzata = false; //per vedere se la classe è utilizzata da un evento
        if(textAggRimClasse.getText().toString().equals("")) {
            Toast.makeText(this, "Nessuna classe inserita", Toast.LENGTH_SHORT).show();
            error = false;
        } else {
            for (int i = 0; i < HomeFragment.classes.size(); i++) {
                if(HomeFragment.classes.get(i).toLowerCase().equals(textAggRimClasse.getText().toString().toLowerCase())) { //se la stringa nell'edittext è uguale a una classe
                    //impossibile rimuovere lavoro, scuola, famiglia e hobby
                    if(textAggRimClasse.getText().toString().toLowerCase().equals("lavoro")) {
                        Toast.makeText(this, "Impossibile eliminare una classe predefinita", Toast.LENGTH_SHORT).show();
                        error = false;
                        break;
                    } else if(textAggRimClasse.getText().toString().toLowerCase().equals("scuola")) {
                        Toast.makeText(this, "Impossibile eliminare una classe predefinita", Toast.LENGTH_SHORT).show();
                        error = false;
                        break;
                    } else if(textAggRimClasse.getText().toString().toLowerCase().equals("famiglia")) {
                        Toast.makeText(this, "Impossibile eliminare una classe predefinita", Toast.LENGTH_SHORT).show();
                        error = false;
                        break;
                    } else if(textAggRimClasse.getText().toString().toLowerCase().equals("hobby")) {
                        Toast.makeText(this, "Impossibile eliminare una classe predefinita", Toast.LENGTH_SHORT).show();
                        error = false;
                        break;
                    } else if(adapter2.getPosition(HomeFragment.classes.get(i)) == spinnerClass.getSelectedItemPosition()) { //se la classe da eliminare è attualmente selezionata nello spinner
                        Toast.makeText(this, "Deselezionare la classe dallo spinner prima di eliminarla", Toast.LENGTH_SHORT).show();
                        error = false;
                        break;
                    } else {
                        //controllo se la classe è utilizzata
                        for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                            if(textAggRimClasse.getText().toString().toLowerCase().equals(HomeFragment.listaEventi.get(j).classe.toLowerCase())){
                                utilizzata = true;
                                error = false;
                                break;
                            }
                        }
                        for (int j = 0; j < HomeFragment.listaInAttesa.size(); j++) {
                            if(textAggRimClasse.getText().toString().toLowerCase().equals(HomeFragment.listaInAttesa.get(j).classe.toLowerCase())){
                                utilizzata = true;
                                error = false;
                                break;
                            }
                        }
                        for (int j = 0; j < HomeFragment.listaInCorso.size(); j++) {
                            if(textAggRimClasse.getText().toString().toLowerCase().equals(HomeFragment.listaInCorso.get(j).classe.toLowerCase())){
                                utilizzata = true;
                                error = false;
                                break;
                            }
                        }
                        for (int j = 0; j < HomeFragment.listaCompletati.size(); j++) {
                            if(textAggRimClasse.getText().toString().toLowerCase().equals(HomeFragment.listaCompletati.get(j).classe.toLowerCase())){
                                utilizzata = true;
                                error = false;
                                break;
                            }
                        }
                        if(utilizzata) {
                            Toast.makeText(this,"Questa classe è utilizzata da un evento",Toast.LENGTH_SHORT).show();
                        } else if(!utilizzata) {
                            HomeFragment.classes.remove(i);
                            Toast.makeText(this, "RIMOSSO!", Toast.LENGTH_SHORT).show();
                            saveData();
                            loadData();
                            textAggRimClasse.setText("");
                            error = false;
                            break;
                        }
                    }
                }
            }
        }
        if(error) {
            Toast.makeText(this,"Nessuna classe corrispondente",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    //mostra la data nel textView relativo
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        dateIsAvailable = true;
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
        textDate.setText(date);
        //per evitare qualsiasi problema legato all'orario, ogni volta che si inserisce la data, l'orario diventa vuoto
        textTime = findViewById(R.id.textViewOrario);
        textTime.setText("");
        timeIsAvailable = false;

        yearSelected = year;
        monthSelected = month;
        daySelected = day;
    }


    //mostra l'orario nel textView relativo
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


    //quando si preme annulla, si ritorna alla main activity (returnMainActivity è definito nel onClick del buttonAnnulla nel XML)
    public void returnMainActivity(View v) {
        super.onBackPressed();  //ritorna all'activity precedente
    }


    //quando si clicca sul bottone SALVA
    public void createEvent(View view) {
        textTitolo = (EditText) findViewById(R.id.editTextTitolo);
        if (textTitolo.getText().toString().matches("")) {
            Toast.makeText(this, "IMPOSSIBILE CONTINUARE: non è stato inserito il titolo", Toast.LENGTH_SHORT).show();
        } else if (!dateIsAvailable) {
            Toast.makeText(this, "IMPOSSIBILE CONTINUARE: non è stata inserita la data", Toast.LENGTH_SHORT).show();
        } else if (!timeIsAvailable) {
            Toast.makeText(this, "IMPOSSIBILE CONTINUARE: non è stato inserito l'orario", Toast.LENGTH_SHORT).show();
        } else {
            //***** per notifiche ********
            Intent intent = new Intent(ModificaEventoActivity.this, AlarmReceiver.class);
            intent.putExtra("titolo", textTitolo.getText().toString());
            intent.putExtra("data", textDate.getText().toString());
            intent.putExtra("ora", textTime.getText().toString());
            PendingIntent alarmIntent = PendingIntent.getBroadcast(ModificaEventoActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
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

            //rimuove da lista in attesa l'evento prima della modifica (per evitare di avere in lista in attesa l'evento modificato e l'evento prima della modifica)
            for (int j = 0; j < HomeFragment.listaInAttesa.size(); j++) {
                if(titoloEvento.equals(HomeFragment.listaInAttesa.get(j).titolo)) {
                    if(dataEvento.equals(HomeFragment.listaInAttesa.get(j).data)) {
                        if(orarioEvento.equals(HomeFragment.listaInAttesa.get(j).orario)) {
                            HomeFragment.listaInAttesa.remove(j);
                        }
                    }
                }
            }

            Intent i = new Intent(this, MainActivity.class); //intent che avvia l'activity MainActivity
            //cerca in listaEventi l'evento corrispondente e lo modifica
            for (int j = 0; j < HomeFragment.listaEventi.size(); j++) {
                if(titoloEvento.equals(HomeFragment.listaEventi.get(j).titolo)) {
                    if(dataEvento.equals(HomeFragment.listaEventi.get(j).data)) {
                        if(orarioEvento.equals(HomeFragment.listaEventi.get(j).orario)) {
                            if(prioritaEvento.equals(HomeFragment.listaEventi.get(j).priorita)) {
                                if(classeEvento.equals(HomeFragment.listaEventi.get(j).classe)) {
                                    HomeFragment.listaEventi.get(j).setTitolo(textTitolo.getText().toString());
                                    HomeFragment.listaEventi.get(j).setData(textDate.getText().toString());
                                    HomeFragment.listaEventi.get(j).setOrario(textTime.getText().toString());
                                    HomeFragment.listaEventi.get(j).setPriorita(spinnerPriority.getSelectedItem().toString());
                                    HomeFragment.listaEventi.get(j).setClasse(spinnerClass.getSelectedItem().toString());
                                }
                            }
                        }
                    }
                }
            }
            MainActivity.eventoModificato = true; //per far si che venga visualizzato in MainActivity il fragment EventiFragment
            saveData();
            loadData();
            startActivity(i);
        }
    }



    private void loadData() { //per caricare i dati che sono stati salvati localmente
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
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
