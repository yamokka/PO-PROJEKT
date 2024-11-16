package pl.cielebakbozecka.mushrooms.game;

public class Main {
    public static void main(String[] args) {

        Plansza plansza = new Plansza(5);
        plansza.wypełnij_grzybkami(3, 2);

        for(int i=0; i< plansza.rozmiar_planszy; i++)
        {
            for(int j =0; j< plansza.rozmiar_planszy; j++)
            {
                System.out.print(plansza.pola[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }

    }
}
