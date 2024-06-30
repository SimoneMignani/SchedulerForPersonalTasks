package com.example.schedulerforpersonaltasks;

import java.io.Serializable;
import java.util.Comparator;

public class Evento implements Serializable {

    String titolo, data, orario , priorita, classe, stato;

    public Evento(String titolo, String data, String orario, String priorita, String classe, String stato) {
        this.titolo = titolo;
        this.data = data;
        this.orario = orario;
        this.priorita = priorita;
        this.classe = classe;
        this.stato = stato;
    }


    public String getTitolo() {
        return titolo;
    }

    public String getData() {
        return data;
    }

    public String getOrario() {
        return orario;
    }

    public String getPriorita() {
        return priorita;
    }

    public String getClasse() {
        return classe;
    }

    public String getStato() {
        return stato;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public void setPriorita(String priorita) {
        this.priorita = priorita;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    //ordina per data crescente
    public static Comparator<Evento> DataComparatorAsc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String data1 = e1.getData().toUpperCase();
            String data2 = e2.getData().toUpperCase();
            String ora1 = e1.getOrario().toUpperCase();
            String ora2 = e2.getOrario().toUpperCase();

            String[] d1 = data1.split("/");

            String[] d2 = data2.split("/");

            String dFin1 = d1[2]+d1[1]+d1[0]+ora1;
            String dFin2 = d2[2]+d2[1]+d2[0]+ora2;
            //ascending order
            return dFin1.compareTo(dFin2);
        }};

    //ordina per data decrescente
    public static Comparator<Evento> DataComparatorDesc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String data1 = e1.getData().toUpperCase();
            String data2 = e2.getData().toUpperCase();
            String ora1 = e1.getOrario().toUpperCase();
            String ora2 = e2.getOrario().toUpperCase();

            String[] d1 = data1.split("/");

            String[] d2 = data2.split("/");

            String dFin1 = d1[2]+d1[1]+d1[0]+ora1;
            String dFin2 = d2[2]+d2[1]+d2[0]+ora2;
            //descending order
            return dFin2.compareTo(dFin1);
        }};

    //ordina per titolo crescente
    public static Comparator<Evento> TitoloComparatorAsc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String tit1 = e1.getTitolo().toUpperCase();
            String tit2 = e2.getTitolo().toUpperCase();
            //ascending order
            return tit1.compareTo(tit2);
        }};

    //ordina per titolo decrescente
    public static Comparator<Evento> TitoloComparatorDesc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String tit1 = e1.getTitolo().toUpperCase();
            String tit2 = e2.getTitolo().toUpperCase();
            //descending order
            return tit2.compareTo(tit1);
        }};

    //ordina per priorità crescente
    public static Comparator<Evento> PrioComparatorAsc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String p1 = e1.getPriorita().toUpperCase();
            String p2 = e2.getPriorita().toUpperCase();
            //ascending order
            return p1.compareTo(p2);
        }};

    //ordina per priorità decrescente
    public static Comparator<Evento> PrioComparatorDesc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String p1 = e1.getPriorita().toUpperCase();
            String p2 = e2.getPriorita().toUpperCase();
            //descending order
            return p2.compareTo(p1);
        }};

    //ordina per classe crescente
    public static Comparator<Evento> ClasseComparatorAsc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String c1 = e1.getClasse().toUpperCase();
            String c2 = e2.getClasse().toUpperCase();
            //ascending order
            return c1.compareTo(c2);
        }};

    //ordina per classe crescente
    public static Comparator<Evento> ClasseComparatorDesc = new Comparator<Evento>() {
        public int compare(Evento e1, Evento e2) {
            String c1 = e1.getClasse().toUpperCase();
            String c2 = e2.getClasse().toUpperCase();
            //ascending order
            return c2.compareTo(c1);
        }};




}
