package pl.cielebakbozecka.mushrooms.game;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import pl.cielebakbozecka.mushrooms.MushroomsApplication;

import java.util.Random;

public class Gracz {
    int a;
    int b;
    int punkty;
    Plansza plansza;

    Image tekstura;
    ImagePattern teksturaPattern;
    Rectangle graczProstokat;
    Button guzikGracza;
    Button guzikZmianyKierunku;
    Text kierunekText;
    Text iloscPunktow;
    //Text wylosowanaLiczba;
    int kierunek = 0;
    private int szerokoscGracza = 25;
    private int wysokoscGracza = 25;
    private int offsetY;
    static int aktualnyGracz = 0;
    private int gracz;

    public Gracz(int a, int b, int gracz, Plansza plansza){
        this.a = a;
        this.b = b;
        this.plansza = plansza;
        this.punkty = 0;
        this.gracz = gracz;
        if(aktualnyGracz == 0)
            aktualnyGracz = gracz;


        guzikGracza = new Button();
        guzikGracza.setLayoutY(350);
        guzikGracza.setMinWidth(100);
        guzikGracza.setMaxWidth(100);
        guzikGracza.setMinHeight(30);
        guzikGracza.setMaxHeight(30);
        guzikGracza.setOnAction(event -> {
            wykonajRuch(plansza);
        });
        MushroomsApplication.pane.getChildren().add(guzikGracza);

        guzikZmianyKierunku = new Button();
        guzikZmianyKierunku.setLayoutY(400);
        guzikZmianyKierunku.setMinWidth(100);
        guzikZmianyKierunku.setMaxWidth(100);
        guzikZmianyKierunku.setMinHeight(30);
        guzikZmianyKierunku.setMaxHeight(30);
        guzikZmianyKierunku.setOnAction(event ->{
            zmienKierunek();
        });
        MushroomsApplication.pane.getChildren().add(guzikZmianyKierunku);

        kierunekText = new Text();
        kierunekText.setLayoutY(450);
        MushroomsApplication.pane.getChildren().add(kierunekText);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        graczProstokat = new Rectangle();
        a = 0;
        b = 0;
        graczProstokat.setX(b * plansza.szerokoscPola + szerokoscGracza/2 + plansza.offsetX);
        //gra dziala w zalozeniu ze jest tylko dwoch graczy
        if(gracz == 1)
        {
            tekstura = new Image("file:pionek1.png");
            graczProstokat.setY(a * plansza.wysokoscPola + plansza.offsetY);
            guzikGracza.setText("Losuj Gracz 1");
            guzikGracza.setLayoutX(150);
            guzikZmianyKierunku.setText("Zmien kierunek");
            guzikZmianyKierunku.setLayoutX(150);
            kierunekText.setX(150);
            kierunekText.setText("Zgodnie z ruchem zegara");
            iloscPunktow = new Text();
            iloscPunktow.setLayoutX(290);
            iloscPunktow.setLayoutY(340);
            MushroomsApplication.pane.getChildren().add(iloscPunktow);
        }
        if(gracz == 2)
        {
            tekstura = new Image("file:pionek2.png");
            graczProstokat.setY(a * plansza.wysokoscPola + wysokoscGracza + plansza.offsetY);
            guzikGracza.setText("Losuj Gracz 2");
            guzikGracza.setLayoutX(400);
            guzikZmianyKierunku.setText("Zmien kierunek");
            guzikZmianyKierunku.setLayoutX(400);
            kierunekText.setX(400);
            kierunekText.setText("Zgodnie z ruchem zegara");
            iloscPunktow = new Text();
            iloscPunktow.setLayoutX(350);
            iloscPunktow.setLayoutY(340);
            MushroomsApplication.pane.getChildren().add(iloscPunktow);
        }
        teksturaPattern = new ImagePattern(tekstura);
        graczProstokat.setFill(teksturaPattern);
        graczProstokat.setWidth(szerokoscGracza);
        graczProstokat.setHeight(wysokoscGracza);
        MushroomsApplication.pane.getChildren().add(graczProstokat);
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

    public void zmienKierunek()
    {
        if(kierunek == 0)
        {
            kierunekText.setText("Przeciwnie z ruchem zegara");
            kierunek = 1;
        }
        else
        {
            kierunekText.setText("Zgodnie z ruchem zegara");
            kierunek = 0;
        }
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

        Rectangle kula;
        Image kulaTekstura;
        ImagePattern kulaTeksturaPattern;
        kula = new Rectangle();
        kula.setX(300);
        kula.setY(370);
        kula.setWidth(50);
        kula.setHeight(50);

        if(gracz == 1)
        {
            if(aktualnyGracz == gracz)
                aktualnyGracz = 2;
            else
                return;
        }
        if(gracz == 2)
        {
            if(aktualnyGracz == gracz)
                aktualnyGracz = 1;
            else
                return;
        }

        int licznikd = plansza.ilośćDobrych;
        int licznikz = plansza.ilośćZłych;

        plansza.pola[a][b] = plansza.Pionek;
        plansza.wyświetlBazęPlanszy();
        plansza.pola[a][b] = plansza.polaWolne;

        if(plansza.tablicagrzybow[a][b]!=null){
            MushroomsApplication.pane.getChildren().remove(plansza.tablicagrzybow[a][b]);
        }

        Random rand = new Random();
        int oczka = rand.nextInt(6) + 1;
        switch(oczka){
            case 1: {
                kulaTekstura = new Image("file:k1.png");
                kulaTeksturaPattern = new ImagePattern(kulaTekstura);
                kula.setFill(kulaTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(kula);
                break;
            }
            case 2: {
                kulaTekstura = new Image("file:k2.png");
                kulaTeksturaPattern = new ImagePattern(kulaTekstura);
                kula.setFill(kulaTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(kula);
                break;
            }
            case 3: {
                kulaTekstura = new Image("file:k3.png");
                kulaTeksturaPattern = new ImagePattern(kulaTekstura);
                kula.setFill(kulaTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(kula);
                break;
            }
            case 4: {
                kulaTekstura = new Image("file:k4.png");
                kulaTeksturaPattern = new ImagePattern(kulaTekstura);
                kula.setFill(kulaTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(kula);
                break;
            }
            case 5: {
                kulaTekstura = new Image("file:k5.png");
                kulaTeksturaPattern = new ImagePattern(kulaTekstura);
                kula.setFill(kulaTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(kula);
                break;
            }
            case 6: {
                kulaTekstura = new Image("file:k6.png");
                kulaTeksturaPattern = new ImagePattern(kulaTekstura);
                kula.setFill(kulaTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(kula);
                break;
            }
            default:
                System.out.println("Oj");
        }

        //wylosowanaLiczba = new Text();
        //wylosowanaLiczba.setLayoutY(650);
        //wylosowanaLiczba.setText(String.valueOf(oczka));
        //MushroomsApplication.pane.getChildren().add(wylosowanaLiczba);

        if(kierunek ==0) {
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
        //MushroomsApplication.pane.getChildren().remove(złyGrzybek);


        iloscPunktow.setText(String.valueOf(punkty));
        System.out.println("Ilość punktów: "+this.punkty);

        graczProstokat.setX(b * plansza.szerokoscPola + szerokoscGracza/2 + plansza.offsetX);
        if(gracz == 1)
        {
            graczProstokat.setY(a * plansza.wysokoscPola + plansza.offsetY);
        }
        if(gracz == 2)
        {
            graczProstokat.setY(a * plansza.wysokoscPola + wysokoscGracza + plansza.offsetY);
        }

            /*
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
            */
    }
}

