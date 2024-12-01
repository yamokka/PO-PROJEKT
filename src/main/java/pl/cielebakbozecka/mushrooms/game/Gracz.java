package pl.cielebakbozecka.mushrooms.game;

import java.util.Scanner;

import static pl.cielebakbozecka.mushrooms.game.Plansza.dobreGrzyby;
import static pl.cielebakbozecka.mushrooms.game.Plansza.złeGrzyby;

public class Gracz {
    int a;
    int b;
    Plansza plansza;

    public Gracz(int a, int b, Plansza plansza){
        this.a = a;
        this.b = b;
        this.plansza = plansza;
        a = 0;
        b = 0;
    }

    public void wylosowanePole(int oczka, int a, int b){
        this.a = a;
        this.b = b;

        if(a==0 && b<plansza.szerokośćPlanszy) {
            if (b + oczka < plansza.szerokośćPlanszy) {
                b = b + oczka;
                a = 0;
            } else {
                oczka = oczka - ((plansza.szerokośćPlanszy-1) - b);
                if (a + oczka < plansza.wysokośćPlanszy) {
                    b = plansza.szerokośćPlanszy-1;
                    a = a + oczka;
                } else {
                    oczka = oczka - ((plansza.wysokośćPlanszy-1) - a);
                    if (b - oczka > 0) {
                        a = plansza.wysokośćPlanszy-1;
                        b = b - oczka;
                    } else {
                        oczka = oczka - (oczka - b);
                        if (a - oczka > 0) {
                            b = plansza.szerokośćPlanszy-1;
                            a = a - oczka;
                        } else {
                            oczka = oczka - (oczka - a);
                            b = b + oczka;
                            a = plansza.wysokośćPlanszy-1;
                        }
                    }
                }
            }
        }
        else {
            if (b == (plansza.szerokośćPlanszy - 1) && a > 0 && a < plansza.wysokośćPlanszy) {
                if (a + oczka < plansza.wysokośćPlanszy) {
                    b = plansza.szerokośćPlanszy - 1;
                    a = a + oczka;
                } else {
                    oczka = oczka - ((plansza.wysokośćPlanszy-1) - a);
                    if (b + oczka > 0) {
                        a = plansza.wysokośćPlanszy - 1;
                        b = b - oczka;
                    } else {
                        oczka = oczka - (oczka - b);
                        if (a - oczka > 0) {
                            b = plansza.szerokośćPlanszy - 1;
                            a = a - oczka;
                        } else {
                            oczka = oczka - (oczka - a);
                            if (b + oczka < plansza.szerokośćPlanszy) {
                                b = b + oczka;
                                ;
                                a = 0;
                            } else {
                                oczka = oczka - (plansza.szerokośćPlanszy - b);
                                b = plansza.szerokośćPlanszy - 1;
                                a = a + oczka;
                            }
                        }
                    }
                }
            } else {
                if ((a == plansza.wysokośćPlanszy - 1) && b>0 && b < plansza.szerokośćPlanszy - 1) {
                    if (b + oczka > 0) {
                        a = plansza.wysokośćPlanszy - 1;
                        b = b - oczka;
                    } else {
                        oczka = oczka - (oczka - b);
                        if (a - oczka > 0) {
                            b = plansza.szerokośćPlanszy - 1;
                            a = a - oczka;
                        } else {
                            oczka = oczka - (oczka - a);
                            if (b + oczka < plansza.szerokośćPlanszy) {
                                b = b + oczka;
                                ;
                                a = 0;
                            } else {
                                oczka = oczka - (plansza.szerokośćPlanszy - b);
                                if (a + oczka < plansza.wysokośćPlanszy) {
                                    b = b + oczka;
                                    ;
                                    a = 0;
                                } else {
                                    oczka = oczka - (oczka - b);
                                    b = plansza.szerokośćPlanszy - 1;
                                    a = a - oczka;
                                }
                            }
                        }
                    }
                }
                else{
                    if (a - oczka >= 0) {
                        b = 0;
                        a = a - oczka;
                    } else {
                        oczka = oczka - (oczka - a);
                        if (b + oczka < plansza.szerokośćPlanszy) {
                            b = b + oczka;
                            ;
                            a = 0;
                        } else {
                            oczka = oczka - (plansza.szerokośćPlanszy - b);
                            if (a + oczka < plansza.wysokośćPlanszy) {
                                b = b + oczka;
                                ;
                                a = 0;
                            } else {
                                oczka = oczka - (oczka - b);
                                if (b + oczka > 0) {
                                    a = plansza.wysokośćPlanszy - 1;
                                    b = b - oczka;
                                } else {
                                    oczka = oczka - (oczka - a);
                                    b = b + oczka;
                                    ;
                                    a = 0;
                                }
                            }
                        }
                    }
                }
            }
        }

        this.a = a;
        this.b = b;
    }

    public void wykonajRuch(Plansza plansza){

            Scanner scan = new Scanner(System.in);
            int licznikd = plansza.ilośćDobrych;
            int licznikz = plansza.ilośćZłych;

            plansza.pola[a][b] = plansza.Pionek;
            plansza.wyświetlBazęPlanszy();
            plansza.pola[a][b] = plansza.polaWolne;

            while (licznikd != 0 && licznikz != 0) {

                System.out.println("Podaj liczbę oczek"); //zmienimy to ale ramy brońskie to trzeba sprawdzić
                int oczka = scan.nextInt();
                wylosowanePole(oczka, a, b);
                if (plansza.pola[a][b] == plansza.dobreGrzyby) {
                    licznikd = licznikd - 1;
                }
                if (plansza.pola[a][b] == plansza.złeGrzyby) {
                    licznikz = licznikz - 1;
                }
                plansza.pola[a][b] = plansza.Pionek;
                plansza.wyświetlBazęPlanszy();
                plansza.pola[a][b] = plansza.polaWolne;

            }


    }
}

