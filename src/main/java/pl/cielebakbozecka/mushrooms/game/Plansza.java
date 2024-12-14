package pl.cielebakbozecka.mushrooms.game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import pl.cielebakbozecka.mushrooms.MushroomsApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Plansza {

    final static int polaNiedostępne = 0;
    final static int polaWolne = 1;
    static int dobreGrzyby = 2;
    static int złeGrzyby = 3;
    static int Pionek = 4;

    int ilośćDobrych = 3;
    int ilośćZłych = 2;

    public Rectangle tablicagrzybow[][];

    public int[][] pola;
    public int wysokośćPlanszy;
    public int szerokośćPlanszy;

    private Image tloTekstura;
    private ImagePattern tloTeksturaPattern;
    private Image sciezka;
    private ImagePattern sciezkaPattern;
    private Rectangle tlo;
    public Rectangle mapa[][];
    public int szerokoscPola = 50;
    public int wysokoscPola = 50;

    public int offsetX = 130;
    public int offsetY = 20;

    public Plansza(int wysokośćPlanszy, int szerokośćPlanszy) {
        this.pola = new int[wysokośćPlanszy][szerokośćPlanszy];
        this.wysokośćPlanszy = wysokośćPlanszy;
        this.szerokośćPlanszy = szerokośćPlanszy;
        this.mapa = new Rectangle[wysokośćPlanszy][szerokośćPlanszy];
        this.tablicagrzybow = new Rectangle[wysokośćPlanszy][szerokośćPlanszy];
        for(int g=0; g<wysokośćPlanszy; g++){
            for(int r=0; r<szerokośćPlanszy; r++){
                this.tablicagrzybow[g][r]=null;
            }
        }

        if(wysokośćPlanszy+szerokośćPlanszy<=12){
            System.out.println("Nie no ta plansza jest za mała, bez przesady");
        }

        tlo = new Rectangle();
        tlo.setX(0);
        tlo.setY(0);
        tlo.setWidth(2000/3);
        tlo.setHeight(1500/3);
        tloTekstura = new Image("file:tlo.png");
        tloTeksturaPattern = new ImagePattern(tloTekstura);
        tlo.setFill(tloTeksturaPattern);
        MushroomsApplication.pane.getChildren().add(tlo);
        for(int y = 0; y < wysokośćPlanszy; y++)
        {
            for(int x = 0; x < szerokośćPlanszy; x++)
            {
                if(x == 0 || x == szerokośćPlanszy - 1 || y == 0 || y == wysokośćPlanszy - 1)
                {
                    mapa[y][x] = new Rectangle();
                    mapa[y][x].setX(x * szerokoscPola + offsetX + 2);
                    mapa[y][x].setY(y * wysokoscPola + offsetY + 2);
                    mapa[y][x].setWidth(szerokoscPola);
                    mapa[y][x].setHeight(wysokoscPola);
                    sciezka = new Image("file:sciezka.png");
                    sciezkaPattern = new ImagePattern(sciezka);
                    mapa[y][x].setFill(sciezkaPattern);
                    //mapa[y][x].setFill(Color.BLUE); //wyplenienie srodka
                    //mapa[y][x].setStroke(Color.BLACK); //wypelnienie krawedzi
                    //mapa[y][x].setStrokeWidth(2); //wypelnienie krawedzi
                    MushroomsApplication.pane.getChildren().add(mapa[y][x]);

                }
            }
        }



        for (int i = 0; i < wysokośćPlanszy; i++) {
            for (int j = 0; j < szerokośćPlanszy; j++) {
                this.pola[i][j] = polaNiedostępne; //uzupełnienie każdego pola wartością 0
            }
        }

        for (int i = 0; i < wysokośćPlanszy; i++) {
            for (int j = 0; j < szerokośćPlanszy; j++) {
                if (i == 0 || i == wysokośćPlanszy - 1 || j == 0 || j == szerokośćPlanszy - 1) {
                    this.pola[i][j] = polaWolne; //pola na których można stawać przyjmują wartość 1
                }
            }
        }

    }

    public void wypełnijGrzybkami() {
        int licznikd = this.ilośćDobrych;
        int licznikz = this.ilośćZłych;

        Image grzybcioTekstura;
        ImagePattern grzybcioTeksturaPattern;


        //Random generator = new Random();

        do {
            int y = ThreadLocalRandom.current().nextInt(0, wysokośćPlanszy);
            int x = ThreadLocalRandom.current().nextInt(0, szerokośćPlanszy);
            //int x = generator.nextInt(wysokośćPlanszy);
            //int y = generator.nextInt(szerokośćPlanszy);

            if (this.pola[y][x] == polaWolne) // pole używane w grze
            {
                this.pola[y][x] = dobreGrzyby;//stawiamy dobrego grzybka
                this.tablicagrzybow[y][x] = new Rectangle();
                this.tablicagrzybow[y][x].setX(x * szerokoscPola+5+offsetX);
                this.tablicagrzybow[y][x].setY(y* wysokoscPola+5+offsetY);
                this.tablicagrzybow[y][x].setWidth(25);
                this.tablicagrzybow[y][x].setHeight(25);
                grzybcioTekstura = new Image("file:dobryGrzyb.png");
                grzybcioTeksturaPattern = new ImagePattern(grzybcioTekstura);
                this.tablicagrzybow[y][x].setFill(grzybcioTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(this.tablicagrzybow[y][x]);
                licznikd = licznikd - 1; //zmniejszamy ilość grzybków do postawienia
            }
        }
        while (licznikd > 0);

        do //analogicznie do poprzedniej pętli ale dla złych grzybków
        {
            int y = ThreadLocalRandom.current().nextInt(0, wysokośćPlanszy);
            int x = ThreadLocalRandom.current().nextInt(0, szerokośćPlanszy);

            if (this.pola[y][x] == polaWolne) {
                this.pola[y][x] = złeGrzyby; //stawiamy złego grzybka
                this.tablicagrzybow[y][x] = new Rectangle();
                this.tablicagrzybow[y][x].setX(x * szerokoscPola+5+offsetX);
                this.tablicagrzybow[y][x].setY(y* wysokoscPola+5+offsetY);
                this.tablicagrzybow[y][x].setWidth(25);
                this.tablicagrzybow[y][x].setHeight(25);
                grzybcioTekstura = new Image("file:zlyGrzyb.png");
                grzybcioTeksturaPattern = new ImagePattern(grzybcioTekstura);
                this.tablicagrzybow[y][x].setFill(grzybcioTeksturaPattern);
                MushroomsApplication.pane.getChildren().add(this.tablicagrzybow[y][x]);
                licznikz = licznikz - 1;
            }
        }
        while (licznikz > 0);

    }

// znaczenia pól:
    // 0 - pole na którym nie można stawać (centralne)
    // 1- pole na którym nic nie ma
    // 2 - pole z dobrym grzybkiem
    // 3 - pole ze złym grzybkiem


    public void zapiszStanPlanszy() throws IOException {
        try (FileWriter filewriter = new FileWriter("pliczek.txt", true)) {
            filewriter.write("Stan gry: \n");

            for (int i = 0; i < this.wysokośćPlanszy; i++) {
                for (int j = 0; j < this.szerokośćPlanszy; j++) {
                    if (pola[i][j] == polaNiedostępne) {
                        //filewriter.write(Integer.toString(this.pola[i][j]));
                        filewriter.write("  ");
                    }
                    if (pola[i][j]== polaWolne) {
                        filewriter.write("# ");
                        //System.out.print("# ");
                    }
                    if (pola[i][j]==dobreGrzyby) {
                        filewriter.write("d ");
                        //System.out.print("d ");
                    }
                    if (pola[i][j]==złeGrzyby) {
                        filewriter.write("z ");
                        //System.out.print("z ");
                    }

                    if (pola[i][j]==Pionek) {
                        filewriter.write("& ");
                        //System.out.print("z ");
                    }

                    //filewriter.write(Integer.toString(this.pola[i][j]));
                    //filewriter.write(" ");
                }
                filewriter.write("\n");
            }
        }
    }

    public void wyświetlBazęPlanszy(){
        for (int i = 0; i < wysokośćPlanszy; i++) {
            for (int j = 0; j <szerokośćPlanszy; j++) {
                if (pola[i][j] == polaNiedostępne) {
                    System.out.print("  ");
                }
                if (pola[i][j]== polaWolne) {
                    System.out.print("# ");
                }
                if (pola[i][j]==dobreGrzyby) {
                    System.out.print("d ");
                }
                if (pola[i][j]==złeGrzyby) {
                    System.out.print("z ");
                }
                if (pola[i][j]==Pionek) {
                    System.out.print("& ");
                }

            }
            System.out.print("\n");
        }
    }

}
