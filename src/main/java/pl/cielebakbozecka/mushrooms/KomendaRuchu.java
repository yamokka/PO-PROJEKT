package pl.cielebakbozecka.mushrooms;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

public class KomendaRuchu implements Serializable {
public int wiersz;
public int kolumna;
public int numerGracza;


    public KomendaRuchu(int wiersz, int kolumna, int numerGracza) {
        this.wiersz = wiersz;
        this.kolumna = kolumna;
        this.numerGracza = numerGracza;
    }
}

