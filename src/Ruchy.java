import java.io.*;
import java.util.Scanner;

public class Ruchy {
    File plik = new File("punkciki.txt");

    static int punkty=0;
    static int los=0;
    static int pole=0;

    static void wyzerujDane() throws IOException {
        FileWriter zapis = new FileWriter("punkciki.txt");
        zapis.write("Gracz 1");
        //zapis.println("Gracz 1: " + los + " Ilość punktów: " + punkty + " Pole: "+pole);
        zapis.close();

    }

    void zmieńPunkty(int pkt) throws FileNotFoundException {
        Scanner odczyt = new Scanner(new File("punkciki.txt"));
        int text = odczyt.nextInt();
        //if(text==null)


    }




}
