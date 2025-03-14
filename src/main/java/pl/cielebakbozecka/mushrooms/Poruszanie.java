package pl.cielebakbozecka.mushrooms;

import java.util.Random;
import java.util.Scanner;

public class Poruszanie {

    static int szerokośćPlanszy;
    static int wysokośćPlanszy;
    //PlanszaBezInterfejsu plansza;
    //int wiersz;
    //int kolumna;


    public static void wykonajRuch(PlanszaBezInterfejsu plansza, NaszGracz gracz, int kroki, int kierunek){

        //this.plansza = plansza;
        wysokośćPlanszy = plansza.wysokośćPlanszy;
        szerokośćPlanszy = plansza.szerokośćPlanszy;


        int[] pozycja = szukajPozycjiGracza(plansza, gracz.getNumer());

        int a, b;

        if (pozycja != null) {
            a = pozycja[0];
            b = pozycja[1];

            plansza.getPola()[a][b] = plansza.polaWolne;

        } else {
            a = 0;
            b = 0;
        }

        //System.out.println(a);
        //System.out.println(b);

        if (kierunek == 1) {
            int[] liczba = wylosowanePoleZegar(kroki, a, b);
            a = liczba[0];
            b = liczba[1];
        } else {
            int[] liczba =wylosowanePoleNieZegar(kroki, a, b);
            a = liczba[0];
            b = liczba[1];
        }

        System.out.println("a po wylosowanym: " +a);
        System.out.println("b po wylosowanym: "+b);
        //a = this.wiersz;
        //b = this.kolumna;

        if(plansza.getPola()[a][b] == plansza.dobreGrzyby){
            gracz.punkty = gracz.punkty + 3;
        }

        if(plansza.getPola()[a][b] == plansza.złeGrzyby){
            gracz.punkty = gracz.punkty - 2;
        }

        if(gracz.getNumer() == 1){
            plansza.getPola()[a][b] = plansza.pionek1;
        }

        if(gracz.getNumer() == 2){
            plansza.getPola()[a][b] = plansza.pionek2;
        }

    }

    public static int[] wylosowanePoleZegar(int oczka, int a, int b) {
        //this.wiersz = a;
        //this.kolumna = b;
        //System.out.println(szerokośćPlanszy);
        //System.out.println(wysokośćPlanszy);
        //System.out.println(a);
        //System.out.println(b);

        if (a == 0 && b < szerokośćPlanszy) { //pierwsza sciana
            if (b + oczka < szerokośćPlanszy) { //czy nie wyjdzie z pierwszej sciany
                b = b + oczka;
                a = 0;
            } else {
                oczka = oczka - ((szerokośćPlanszy - 1) - b);
                b = szerokośćPlanszy - 1;
                a = 0;
                if (a + oczka < wysokośćPlanszy) { //czy nie wyjdzie z drugiej sciany
                    b = szerokośćPlanszy - 1;
                    a = a + oczka;
                } else {
                    oczka = oczka - ((wysokośćPlanszy - 1) - a);
                    a = wysokośćPlanszy - 1;
                    b = szerokośćPlanszy - 1;
                    if (b - oczka > 0) {
                        a = wysokośćPlanszy - 1;
                        b = b - oczka;
                    } else {
                        System.out.println("wow JAKIM CUDEM???");
                    }
                }

            }
        } else {
            if (b == (szerokośćPlanszy - 1) && a > 0 && a < wysokośćPlanszy) { //druga ściana
                if (a + oczka < wysokośćPlanszy) { //czy wyjdzie z 2 ściany
                    b = szerokośćPlanszy - 1;
                    a = a + oczka;
                } else {
                    oczka = oczka - ((wysokośćPlanszy - 1) - a);
                    a = wysokośćPlanszy - 1;
                    b = szerokośćPlanszy - 1;
                    if (b - oczka > 0) { //czy nie wyjdzie z 3 ściany
                        a = wysokośćPlanszy - 1;
                        b = b - oczka;
                    } else {
                        oczka = oczka - b;
                        a = wysokośćPlanszy - 1;
                        b = 0;
                        if (a - oczka > 0) {
                            b = 0;
                            a = a - oczka;
                        } else {
                            System.out.println("wow TUTAJ?");
                        }
                    }
                }
            } else {
                if ((a == wysokośćPlanszy - 1) && b > 0 && b < szerokośćPlanszy - 1) { //trzecia ściana
                    if (b - oczka > 0) { //czy nie wyjdzie z trzeciej ściany
                        a = wysokośćPlanszy - 1;
                        b = b - oczka;
                    } else {
                        oczka = oczka - b;
                        a = wysokośćPlanszy - 1;
                        b = 0;
                        if (a - oczka > 0) { //czy nie wyjdzie z czwartej ściany
                            b = 0;
                            a = a - oczka;
                        } else {
                            oczka = oczka - a;
                            a = 0;
                            b = 0;
                            if (b + oczka < szerokośćPlanszy) {
                                b = b + oczka;
                                a = 0;
                            } else {
                                System.out.println("wow CO?");
                            }
                        }
                    }
                } else { //czwarta ściana bez pola startowego
                    if (a - oczka > 0) { //czy nie wyjdzie z czwartej ściany
                        b = 0;
                        a = a - oczka;
                    } else {
                        oczka = oczka - a;
                        a = 0;
                        b = 0;
                        if (b + oczka < szerokośćPlanszy) { //czy nie wyjdzie z pierwszej ściany
                            b = b + oczka;
                            a = 0;
                        } else {
                            oczka = oczka - ((szerokośćPlanszy - 1) - b);
                            a = 0;
                            b = szerokośćPlanszy - 1;
                            if (a + oczka < wysokośćPlanszy) {
                                b = szerokośćPlanszy - 1;
                                a = a + oczka;
                            } else {
                                System.out.println("wow EXCUSE ME?");
                            }
                        }
                    }
                }
            }
        }
        System.out.println("a w funkcji: "+a);
        System.out.println("b w funkcji: "+b);
        return new int[]{a, b};
    }

    public static int[] wylosowanePoleNieZegar(int oczka, int a, int b){

        //this.wiersz = a;
        //this.kolumna = b;

        if (b == 0 && a < wysokośćPlanszy) { //czwarta sciana
            if (a + oczka < wysokośćPlanszy) { //czy nie wyjdzie z czwartej sciany
                a = a + oczka;
                b = 0;
            } else {
                oczka = oczka - ((wysokośćPlanszy - 1) - a);
                a = wysokośćPlanszy - 1;
                b = 0;
                if (b + oczka < szerokośćPlanszy) { //czy nie wyjdzie z trzeciej sciany
                    a = wysokośćPlanszy - 1;
                    b = b + oczka;
                } else {
                    oczka = oczka - ((szerokośćPlanszy - 1) - b);
                    a = wysokośćPlanszy - 1;
                    b = szerokośćPlanszy - 1;
                    if (a - oczka > 0) {
                        b = szerokośćPlanszy - 1;
                        a = a - oczka;
                    } else {
                        System.out.println("wow");
                    }
                }
            }
        } else {
            if (a == (wysokośćPlanszy - 1) && b > 0 && b < szerokośćPlanszy) { //trzecia ściana
                if (b + oczka < szerokośćPlanszy) { //czy wyjdzie z trzeciej ściany
                    a = wysokośćPlanszy - 1;
                    b = b + oczka;
                } else {
                    oczka = oczka - ((szerokośćPlanszy - 1) - b);
                    b = szerokośćPlanszy - 1;
                    a = wysokośćPlanszy - 1;
                    if (a - oczka > 0) { //czy nie wyjdzie z drugiej ściany
                        b = szerokośćPlanszy - 1;
                        a = a - oczka;
                    } else {
                        oczka = oczka - a;
                        b = szerokośćPlanszy - 1;
                        a = 0;
                        if (b - oczka > 0) {
                            a = 0;
                            b = b - oczka;
                        } else {
                            System.out.println("wow");
                        }
                    }
                }
            } else {
                if ((b == szerokośćPlanszy - 1) && a > 0 && a < wysokośćPlanszy) { //druga ściana
                    if (a - oczka > 0) { //czy nie wyjdzie z drugiej ściany
                        b = szerokośćPlanszy - 1;
                        a = a - oczka;
                    } else {
                        oczka = oczka - a;
                        b = szerokośćPlanszy - 1;
                        a = 0;
                        if (b - oczka > 0) { //czy nie wyjdzie z pierwszej ściany
                            a = 0;
                            b = b - oczka;
                        } else {
                            oczka = oczka - b;
                            a = 0;
                            b = 0;
                            if (a + oczka < wysokośćPlanszy) {
                                a = a + oczka;
                                b = 0;
                            } else {
                                System.out.println("wow");
                            }
                        }
                    }
                } else { //pierwsza ściana bez pola startowego
                    if (b - oczka > 0) { //czy nie wyjdzie z pierwszej ściany
                        a = 0;
                        b = b - oczka;
                    } else {
                        oczka = oczka - b;
                        a = 0;
                        b = 0;
                        if (a + oczka < wysokośćPlanszy) { //czy nie wyjdzie z czwartej ściany
                            a = a + oczka;
                            b = 0;
                        } else {
                            oczka = oczka - ((wysokośćPlanszy - 1) - a);
                            b = 0;
                            a = wysokośćPlanszy - 1;
                            if (b + oczka < szerokośćPlanszy) {
                                a = wysokośćPlanszy - 1;
                                b = b + oczka;
                            } else {
                                System.out.println("wow");
                            }
                        }
                    }
                }
            }
        }
        System.out.println("a w funkcji: "+a);
        System.out.println("b w funkcji: "+b);
        return new int[]{a, b};
}

    private static int[] szukajPozycjiGracza(PlanszaBezInterfejsu plansza, int numerGracza){

        int symbolGracza;

        if(numerGracza==1){
            symbolGracza=6;
        }
        else{
            symbolGracza=9;
        }


        for(int i = 0; i<plansza.getWysokość(); i++){
            for(int j = 0; j <plansza.getSzerokość(); j++){
                if(plansza.getPola()[i][j]==symbolGracza){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

}


