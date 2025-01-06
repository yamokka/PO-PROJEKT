package pl.cielebakbozecka.mushrooms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ŁączenieZSerwerem {

    private static final String ADRES_SERWERA = "localhost";
    private static final int PORT_SERWERA = 12345;



    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;

    public int idGracza;
    public int[][] plansza;
    public int wiersz;
    public int kolumna;



    public ŁączenieZSerwerem(){
        try (
                Socket socket = new Socket(ADRES_SERWERA, PORT_SERWERA);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            this.socket = socket;

            this.out = out;
            this.in = in;
            //czytomasens();
            this.idGracza = odbierzNumerGracza();
            this.plansza = odbierzBazęPlanszy();
            this.wiersz = 0;
            this.kolumna = 0;
            while(true){
                if(Tura()!=0){
                System.out.println("Moja kolej");
                }
                else{
                    Thread.sleep(50000);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int Tura(){
        String message = null;
        try {
            message = (String) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(idGracza==1){
            if(message.equals("Tura gracza1")){
                Poruszanie ruch = new Poruszanie(6, 8,wiersz,kolumna);
                ruch.wykonajRuch(wiersz,kolumna);
                wiersz=ruch.wiersz;
                kolumna=ruch.kolumna;
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            if(message.equals("Tura gracza2")){
                Poruszanie ruch = new Poruszanie(6, 8,wiersz,kolumna);
                ruch.wykonajRuch(wiersz,kolumna);
                wiersz=ruch.wiersz;
                kolumna=ruch.kolumna;
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    public void wyślijKomendęRuchu(){
        try{
            KomendaRuchu komenda = new KomendaRuchu(wiersz,kolumna,idGracza);
            out.writeObject(komenda);
            out.flush();
            System.out.println("Ruch wysłany");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int odbierzNumerGracza() {

        int idgracza = 0;
        try {
            System.out.println("Oczekiwanie na dane...");
            idgracza = (int) in.readObject();
            System.out.println("Odczytałem id gracza: " + idgracza);
        }catch (IOException e) {
            System.err.println("Błąd odczytu ID gracza: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Nieprawidłowy typ danych podczas odczytu ID gracza.");
            e.printStackTrace();
        }
        //System.out.println("Dojde do konca!");
        //return 1;
        if (socket.isConnected()) {
            System.out.println("Połączenie z serwerem aktywne.");
        } else {
            System.err.println("Połączenie z serwerem zostało zerwane.");
        }
        return idgracza;
    }

    public int[][] odbierzBazęPlanszy(){
        //System.out.println("Ej???");
        int[][] plansza = null;
        if (in == null) {
            System.out.println("Strumień wejściowy jest null.");
        }
        try {
            System.out.println("Oczekiwanie na dane...");
            plansza = (int[][]) in.readObject();
            System.out.println("Odczytałem planszę");
            for(int i=0; i<plansza.length; i++){
                for(int j=0; j<plansza[i].length; j++){
                    System.out.print(plansza[i][j]);
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas odbierania planszy: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Nieprawidłowy typ danych podczas odbierania planszy.");
            e.printStackTrace();
        }

        return plansza;
    }
}

