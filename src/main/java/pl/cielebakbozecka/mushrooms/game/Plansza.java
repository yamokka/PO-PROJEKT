package pl.cielebakbozecka.mushrooms.game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Plansza {

    public int[][] pola;
    public int rozmiar_planszy;

    public Plansza(int rozmiar_planszy) {
        this.pola = new int[rozmiar_planszy][rozmiar_planszy];
        this.rozmiar_planszy = rozmiar_planszy;

        for (int i = 0; i < rozmiar_planszy; i++) {
            for (int j = 0; j < rozmiar_planszy; j++) {
                this.pola[i][j] = 0; //uzupełnienie każdego pola wartością 0
            }
        }

        for (int i = 0; i < rozmiar_planszy; i++) {
            for (int j = 0; j < rozmiar_planszy; j++) {
                if (i == 0 || i == rozmiar_planszy - 1 || j == 0 || j == rozmiar_planszy - 1) {
                    this.pola[i][j] = 1; //pola na których można stawać przyjmują wartość 1
                }
            }
        }

    }

    public void wypełnijGrzybkami(int ilość_dobrych, int ilość_złych) {
        int licznikd = ilość_dobrych;
        int licznikz = ilość_złych;

        Random generator = new Random();

        do {
            int x = generator.nextInt(rozmiar_planszy);
            int y = generator.nextInt(rozmiar_planszy);

            if (this.pola[x][y] == 1) // pole używane w grze
            {
                this.pola[x][y] = 2; //stawiamy dobrego grzybka
                licznikd = licznikd - 1; //zmniejszamy ilość grzybków do postawienia
            }
        }
        while (licznikd > 0);

        do //analogicznie do poprzedniej pętli ale dla złych grzybków
        {
            int x = generator.nextInt(rozmiar_planszy);
            int y = generator.nextInt(rozmiar_planszy);

            if (this.pola[x][y] == 1) {
                this.pola[x][y] = 3; //stawiamy złego grzybka
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

            for (int i = 0; i < this.rozmiar_planszy; i++) {
                for (int j = 0; j < this.rozmiar_planszy; j++) {
                    filewriter.write(Integer.toString(this.pola[i][j]));
                    filewriter.write(" ");
                }
                filewriter.write("\n");
            }
        }
    }

}
