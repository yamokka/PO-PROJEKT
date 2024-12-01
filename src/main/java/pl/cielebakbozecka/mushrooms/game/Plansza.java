package pl.cielebakbozecka.mushrooms.game;

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

    public int[][] pola;
    public int wysokośćPlanszy;
    public int szerokośćPlanszy;


    public Plansza(int wysokośćPlanszy, int szerokośćPlanszy) {
        this.pola = new int[wysokośćPlanszy][szerokośćPlanszy];
        this.wysokośćPlanszy = wysokośćPlanszy;
        this.szerokośćPlanszy = szerokośćPlanszy;

        if((2*wysokośćPlanszy)+(2*szerokośćPlanszy)<=6){
            System.out.println("Nie no ta plansza jest za mała, bez przesady");
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

        //Random generator = new Random();

        do {
            int y = ThreadLocalRandom.current().nextInt(0, wysokośćPlanszy);
            int x = ThreadLocalRandom.current().nextInt(0, szerokośćPlanszy);
            //int x = generator.nextInt(wysokośćPlanszy);
            //int y = generator.nextInt(szerokośćPlanszy);

            if (this.pola[y][x] == polaWolne) // pole używane w grze
            {
                this.pola[y][x] = dobreGrzyby; //stawiamy dobrego grzybka
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
