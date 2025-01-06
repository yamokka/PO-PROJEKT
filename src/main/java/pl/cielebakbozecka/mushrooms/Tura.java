package pl.cielebakbozecka.mushrooms;

import java.io.Serializable;

public class Tura implements Serializable {
    //public enum Typ {POWITANIE, RUCH};
    //Typ typ;
    public int[][] plansza;
    public int numerGracza;

    public Tura(int[][] plansza, int numerGracza){
        //this.typ = typ;
        this.plansza = plansza;
        this.numerGracza = numerGracza;
    }
}
