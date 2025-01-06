package pl.cielebakbozecka.mushrooms;

import java.util.Random;
import java.util.Scanner;

public class Poruszanie {
    int szerokośćPlanszy;
    int wysokośćPlanszy;
    int wiersz;
    int kolumna;

    public Poruszanie(int szerokośćPlanszy, int wysokośćPlanszy, int wiersz, int kolumna){
        this.wiersz = wiersz;
        this.kolumna= kolumna;
        this.wysokośćPlanszy=wysokośćPlanszy;
        this.szerokośćPlanszy=szerokośćPlanszy;
    }

    void wykonajRuch(int a, int b){
        Random rand = new Random();
        int oczka = rand.nextInt(6) + 1;
        System.out.println("Wylosowano "+oczka+" oczek. Wybierz stronę ruchu: 1-zgodnie z ruchem zegara/2-niezgodnie");
        Scanner scan = new Scanner(System.in);
        int kierunek = scan.nextInt();
        if (kierunek == 0) {
            wylosowanePoleZegar(oczka, a, b);
        } else {
            wylosowanePoleNieZegar(oczka, a, b);
        }
    }

    public void wylosowanePoleZegar(int oczka, int a, int b) {
        this.wiersz = a;
        this.kolumna = b;

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
                        System.out.println("wow");
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
                            System.out.println("wow");
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
                                System.out.println("wow");
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
                                System.out.println("wow");
                            }
                        }
                    }
                }
            }
        }

        this.wiersz = a;
        this.kolumna = b;
    }

    public void wylosowanePoleNieZegar(int oczka, int a, int b){

        this.wiersz = a;
        this.kolumna = b;

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

        this.wiersz = a;
        this.kolumna = b;
    }
}


