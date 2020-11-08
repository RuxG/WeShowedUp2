package com.example.weshowedup2;


public class Event {
    public String titlu;
    public String tip;
    public String data;
    public String ora;
    public String locatie;
    public String organizator;
    public String descriere;
    public String img;

    public Event(String titlu, String tip, String data, String ora, String locatie, String organizator, String descriere, String img) {
        this.titlu = titlu;
        this.tip = tip;
        this.data = data;
        this.ora = ora;
        this.locatie = locatie;
        this.organizator = organizator;
        this.descriere = descriere;
        this.img = img;
    }

    public Event () {

    }
}
