package pl.cielebakbozecka.mushrooms.game;

import java.util.Scanner;

import static pl.cielebakbozecka.mushrooms.game.Plansza.dobreGrzyby;
import static pl.cielebakbozecka.mushrooms.game.Plansza.złeGrzyby;

public class Gracz {
    int a;
    int b;
    int punkty;
    Plansza plansza;

    public Gracz(int a, int b, Plansza plansza){
        this.a = a;
        this.b = b;
        this.plansza = plansza;
        this.punkty = 0;
        a = 0;
        b = 0;
    }

    public void wylosowanePoleZegar(int oczka, int a, int b){
        this.a = a;
        this.b = b;

        if(a==0 && b<plansza.szerokośćPlanszy) { //pierwsza sciana
            if (b + oczka < plansza.szerokośćPlanszy) { //czy nie wyjdzie z pierwszej sciany
                b = b + oczka;
                a = 0;
            } else {
                oczka = oczka - ((plansza.szerokośćPlanszy-1) - b);
                b=plansza.szerokośćPlanszy-1;
                a=0;
                if (a + oczka < plansza.wysokośćPlanszy) { //czy nie wyjdzie z drugiej sciany
                    b = plansza.szerokośćPlanszy - 1;
                    a = a + oczka;
                }
                else {
                    oczka = oczka - ((plansza.wysokośćPlanszy-1) - a);
                    a = plansza.wysokośćPlanszy-1;
                    b = plansza.szerokośćPlanszy-1;
                    if (b - oczka > 0) {
                        a = plansza.wysokośćPlanszy - 1;
                        b = b - oczka;
                    }
                    else{
                        System.out.println("wow");
                    }
                }

            }
        }
        else {
            if (b == (plansza.szerokośćPlanszy - 1) && a > 0 && a < plansza.wysokośćPlanszy) { //druga ściana
                if (a + oczka < plansza.wysokośćPlanszy) { //czy wyjdzie z 2 ściany
                    b = plansza.szerokośćPlanszy - 1;
                    a = a + oczka;
                } else {
                    oczka = oczka - ((plansza.wysokośćPlanszy-1) - a);
                    a= plansza.wysokośćPlanszy-1;
                    b = plansza.szerokośćPlanszy -1;
                    if (b - oczka > 0) { //czy nie wyjdzie z 3 ściany
                        a = plansza.wysokośćPlanszy - 1;
                        b = b - oczka;
                    } else {
                        oczka = oczka - b;
                        a = plansza.wysokośćPlanszy-1;
                        b = 0;
                        if (a - oczka > 0) {
                            b = 0;
                            a = a - oczka;
                        }
                        else {
                            System.out.println("wow");
                        }
                    }
                }
            } else {
                if ((a == plansza.wysokośćPlanszy - 1) && b>0 && b < plansza.szerokośćPlanszy - 1) { //trzecia ściana
                    if (b -oczka > 0) { //czy nie wyjdzie z trzeciej ściany
                        a = plansza.wysokośćPlanszy - 1;
                        b = b - oczka;
                    } else {
                        oczka = oczka - b;
                        a= plansza.wysokośćPlanszy-1;
                        b= 0;
                        if (a - oczka > 0) { //czy nie wyjdzie z czwartej ściany
                            b = 0;
                            a = a - oczka;
                        } else {
                            oczka = oczka - a;
                            a= 0;
                            b= 0;
                            if (b + oczka < plansza.szerokośćPlanszy) {
                                b = b + oczka;
                                a = 0;
                            } else {
                                System.out.println("wow");
                            }
                        }
                    }
                }
                else{ //czwarta ściana bez pola startowego
                    if (a - oczka > 0) { //czy nie wyjdzie z czwartej ściany
                        b = 0;
                        a = a - oczka;
                    } else {
                        oczka = oczka - a;
                        a= 0;
                        b= 0;
                        if (b + oczka < plansza.szerokośćPlanszy) { //czy nie wyjdzie z pierwszej ściany
                            b = b + oczka;
                            a = 0;
                        } else {
                            oczka = oczka - ((plansza.szerokośćPlanszy-1) - b);
                            a= 0;
                            b= plansza.szerokośćPlanszy-1;
                            if (a + oczka < plansza.wysokośćPlanszy) {
                                b = plansza.szerokośćPlanszy-1;
                                a = a+oczka;
                            } else {
                                System.out.println("wow");
                            }
                        }
                    }
                }
            }
        }

        this.a = a;
        this.b = b;
    }

    public void wylosowanePoleNieZegar(int oczka, int a, int b){
        this.a = a;
        this.b = b;

        if(b==0 && a<plansza.wysokośćPlanszy) { //czwarta sciana
            if (a + oczka < plansza.wysokośćPlanszy) { //czy nie wyjdzie z czwartej sciany
                a = a + oczka;
                b = 0;
            } else {
                oczka = oczka - ((plansza.wysokośćPlanszy-1) - a);
                a=plansza.wysokośćPlanszy-1;
                b=0;
                if (b + oczka < plansza.szerokośćPlanszy) { //czy nie wyjdzie z trzeciej sciany
                    a = plansza.wysokośćPlanszy - 1;
                    b = b + oczka;
                }
                else {
                    oczka = oczka - ((plansza.szerokośćPlanszy-1) - b);
                    a = plansza.wysokośćPlanszy-1;
                    b = plansza.szerokośćPlanszy-1;
                    if (a - oczka > 0) {
                        b = plansza.szerokośćPlanszy - 1;
                        a = a - oczka;
                    }
                    else{
                        System.out.println("wow");
                    }
                }
            }
        }
        else {
            if (a == (plansza.wysokośćPlanszy - 1) && b > 0 && b < plansza.szerokośćPlanszy) { //trzecia ściana
                if (b + oczka < plansza.szerokośćPlanszy) { //czy wyjdzie z trzeciej ściany
                    a = plansza.wysokośćPlanszy - 1;
                    b = b + oczka;
                } else {
                    oczka = oczka - ((plansza.szerokośćPlanszy-1) - b);
                    b= plansza.szerokośćPlanszy-1;
                    a = plansza.wysokośćPlanszy -1;
                    if (a - oczka > 0) { //czy nie wyjdzie z drugiej ściany
                        b = plansza.szerokośćPlanszy - 1;
                        a = a - oczka;
                    } else {
                        oczka = oczka - a;
                        b = plansza.szerokośćPlanszy-1;
                        a = 0;
                        if (b - oczka > 0) {
                            a = 0;
                            b = b - oczka;
                        }
                        else {
                            System.out.println("wow");
                        }
                    }
                }
            } else {
                if ((b == plansza.szerokośćPlanszy - 1) && a>0 && a < plansza.wysokośćPlanszy) { //druga ściana
                    if (a - oczka > 0) { //czy nie wyjdzie z drugiej ściany
                        b = plansza.szerokośćPlanszy - 1;
                        a = a - oczka;
                    } else {
                        oczka = oczka - a;
                        b= plansza.szerokośćPlanszy-1;
                        a= 0;
                        if (b - oczka > 0) { //czy nie wyjdzie z pierwszej ściany
                            a = 0;
                            b = b - oczka;
                        } else {
                            oczka = oczka - b;
                            a= 0;
                            b= 0;
                            if (a + oczka < plansza.wysokośćPlanszy) {
                                a = a + oczka;
                                b = 0;
                            } else {
                                System.out.println("wow");
                            }
                        }
                    }
                }
                else{ //pierwsza ściana bez pola startowego
                    if (b - oczka > 0) { //czy nie wyjdzie z pierwszej ściany
                        a = 0;
                        b = b - oczka;
                    } else {
                        oczka = oczka - b;
                        a= 0;
                        b= 0;
                        if (a + oczka < plansza.wysokośćPlanszy) { //czy nie wyjdzie z czwartej ściany
                            a = a + oczka;
                            b = 0;
                        } else {
                            oczka = oczka - ((plansza.wysokośćPlanszy-1) - a);
                            b= 0;
                            a= plansza.wysokośćPlanszy-1;
                            if (b + oczka < plansza.szerokośćPlanszy) {
                                a = plansza.wysokośćPlanszy-1;
                                b = b+oczka;
                            } else {
                                System.out.println("wow");
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
                System.out.println("W którą stronę iść?");
                System.out.println("0 -> zgodnie z ruchem wskazówek zegara");
                System.out.println("1 -> niezgodnie z ruchem wskazówek zegara");
                int kierunek = scan.nextInt();
                if(kierunek==0) {
                    wylosowanePoleZegar(oczka, a, b);
                }
                else{
                    wylosowanePoleNieZegar(oczka, a, b);
                }
                if (plansza.pola[a][b] == plansza.dobreGrzyby) {
                    licznikd = licznikd - 1;
                    this.punkty=punkty+3;
                }
                if (plansza.pola[a][b] == plansza.złeGrzyby) {
                    licznikz = licznikz - 1;
                    this.punkty=punkty-2;
                }
                plansza.pola[a][b] = plansza.Pionek;
                plansza.wyświetlBazęPlanszy();
                plansza.pola[a][b] = plansza.polaWolne;
                System.out.println("Ilość punktów: "+this.punkty);

            }


    }
}

