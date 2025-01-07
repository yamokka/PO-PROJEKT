package pl.cielebakbozecka.mushrooms;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class SerwerTCP {

    private static final String WIADOMOŚĆ = "Jesteś graczem numer: ";
    public static final int PORT = 12345;
    //private Plansza plansza;

    public int[][] plansza;

    final static int polaNiedostępne = 0;
    final static int polaWolne = 1;
    static int dobreGrzyby = 2;
    static int złeGrzyby = 3;
    static int Pionek = 4;

    int ilośćDobrych = 3;
    int ilośćZłych = 2;

    private List<HandlerKlienta> klienci;

    public int czyjaTura;


    private void zainicjalizujPlansze(int[][]plansza) {

        for (int i = 0; i < plansza.length; i++) {
            for (int j = 0; j < plansza[i].length; j++) {
                plansza[i][j] = polaNiedostępne; //uzupełnienie każdego pola wartością 0
            }
        }

        for (int i = 0; i < plansza.length; i++) {
            for (int j = 0; j < plansza[i].length; j++) {
                if (i == 0 || i == plansza.length - 1 || j == 0 || j == plansza[i].length - 1) {
                    plansza[i][j] = polaWolne; //pola na których można stawać przyjmują wartość 1
                }
            }
        }

        /*
        for (int i = 0; i < plansza.length; i++) {
            for (int j = 0; j < plansza[i].length; j++) {
                plansza[i][j] = 0;
            }
        }

         */
    }

    private void wypełnijPlanszę(int[][] plansza){
        int licznikd = this.ilośćDobrych;
        int licznikz = this.ilośćZłych;

        do {
            int y = ThreadLocalRandom.current().nextInt(0, plansza.length);
            int x = ThreadLocalRandom.current().nextInt(0, plansza[0].length);


            if (plansza[y][x] == polaWolne) // pole używane w grze
            {
                plansza[y][x] = dobreGrzyby;//stawiamy dobrego grzybka
                licznikd = licznikd - 1; //zmniejszamy ilość grzybków do postawienia
            }
        }
        while (licznikd > 0);

        do {
            int y = ThreadLocalRandom.current().nextInt(0, plansza.length);
            int x = ThreadLocalRandom.current().nextInt(0, plansza[0].length);


            if (plansza[y][x] == polaWolne) // pole używane w grze
            {
                plansza[y][x] = złeGrzyby;//stawiamy złego grzybka
                licznikz = licznikz - 1; //zmniejszamy ilość grzybków do postawienia
            }
        }
        while (licznikz > 0);
    }


    public SerwerTCP(int wiersze, int kolumny) {
        plansza = new int[wiersze][kolumny];
        //plansza.pola = new int[wiersze][kolumny];
        klienci = new ArrayList<>();
        zainicjalizujPlansze(plansza);
        wypełnijPlanszę(plansza);
    }

    public void start() {
        try (ServerSocket serwerSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer uruchomiony w porcie " + PORT);

            while (true) {
                Socket socket = serwerSocket.accept();
                HandlerKlienta handlerKlienta = new HandlerKlienta(socket, this);
                if (klienci.isEmpty()) {
                    handlerKlienta.numerGracza = 1;
                } else {
                    handlerKlienta.numerGracza = klienci.size() +1;
                }
                klienci.add(handlerKlienta);
                new Thread(handlerKlienta).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void wyślijTablice() {
        for (HandlerKlienta klient : klienci) {
            klient.wyślijPlanszę(plansza);
        }
    }

    public synchronized void wykonajRuch(KomendaRuchu komendaRuchu) {
        plansza[komendaRuchu.wiersz][komendaRuchu.kolumna] = komendaRuchu.numerGracza;
        wyślijTablice();
    }


    public static void main(String[] args) {

        SerwerTCP serwer = new SerwerTCP(6, 8);
        serwer.start();

       /*
        Plansza plansza= new Plansza(6, 8);

        plansza.wypełnijGrzybkami();
        plansza.zapiszStanPlanszy();
        plansza.wyświetlBazęPlanszy();

        try (ServerSocket server = new ServerSocket(54331);
             Socket socket = server.accept();
             InputStream is = socket.getInputStream()) {
            System.out.println(server);
            System.out.println(socket);
            System.out.println(">>> " + is.read());


            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                System.out.println(">>> " + ois.readObject());

                //System.out.println(">>> " + ois.readObject());
                //System.out.println(">>> " + ois.readObject());
            }
        }
         */
    }

    private static class HandlerKlienta implements Runnable {
        private Socket socket;
        private SerwerTCP serwer;
        private ObjectOutputStream out;
        public ObjectInputStream in;
        public int numerGracza;
        public int tura;

        public HandlerKlienta(Socket socket, SerwerTCP serwer) {
            this.socket = socket;
            this.serwer = serwer;
 /*
            try {
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Nie udało się zainicjalizować strumieni", e);
            }

  */
        }

        public void przekażCzyjaKolej(int tura){
            if(tura==1){
                try{
                    String ops = "Tura gracza1";
                    System.out.println("Write numero tres- czyja tura czyli string");
                    out.writeObject(ops);
                    out.flush();
                    System.out.println("Tura Gracza numer 1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            else{
                try{
                    System.out.println("Write numero quatro- czyja tura2 czyli string");
                    String ops = "Tura gracza2";
                    out.writeObject(ops);
                    out.flush();
                    System.out.println("Tura Gracza numer 2");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        public void przekażNumer() {
            try {
                System.out.println("Write numero uno- numer id");
                out.writeObject(this.numerGracza);
                out.flush();

                System.out.println("Przekazałem graczowi numer: " + this.numerGracza);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //int idGracza = (int) in.readObject();
            //System.out.println("Odebrano ID gracza: " + idGracza);
        }

        public void odbierzKomendę(){
            Object tmp;
            KomendaRuchu komenda = null;
            try {
                System.out.println("Oczekiwanie na dane...");
                tmp =  in.readObject();
                System.out.println(tmp.getClass().getName());
                komenda = (KomendaRuchu) tmp;

                System.out.println("Odczytałem komendę ruchu");
            }catch (IOException e) {
                System.err.println("Błąd odczytu: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("Nieprawidłowy typ danych podczas odczytu.");
                e.printStackTrace();
            }
            if (socket.isConnected()) {
                System.out.println("Połączenie z serwerem aktywne.");
            } else {
                System.err.println("Połączenie z serwerem zostało zerwane.");
            }
            if(komenda.numerGracza==1){
                serwer.plansza[komenda.kolumna][komenda.wiersz]=6;
            }
            else{
                serwer.plansza[komenda.kolumna][komenda.wiersz]=8;
            }
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out = new ObjectOutputStream(socket.getOutputStream());
                this.in = in;
                //out.writeObject("Testowa wiadomość");
                //out.flush();
                //System.out.println("Wysłałem testową wiadomość.");


                przekażNumer();
                wyślijPlanszę(serwer.plansza);


                while (true) {

                    serwer.czyjaTura=1;
                    if(numerGracza==1){
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(tura);
                        System.out.println(numerGracza+" 2");
                        odbierzKomendę();
                        System.out.println(numerGracza+" 3");
                        wyślijPlanszę(serwer.plansza);
                    }
                    else{
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(tura);
                        System.out.println(numerGracza+" 2");
                        odbierzKomendę();
                        System.out.println(numerGracza+" 3");
                        wyślijPlanszę(serwer.plansza);
                    }
                    serwer.czyjaTura=2;
                    if(numerGracza!=1 ){
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(tura);
                        System.out.println(numerGracza+" 2");
                        odbierzKomendę();
                        System.out.println(numerGracza+" 3");
                        wyślijPlanszę(serwer.plansza);
                    }
                    else{
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(tura);
                        System.out.println(numerGracza+" 2");
                        odbierzKomendę();
                        System.out.println(numerGracza+" 3");
                        wyślijPlanszę(serwer.plansza);
                    }
                    //System.out.println(numerGracza+" 1");
                    //przekażCzyjaKolej(tura);
                    //System.out.println(numerGracza+" 2");
                    //odbierzKomendę();
                    //System.out.println(numerGracza+" 3");
                    //wyślijPlanszę(serwer.plansza);

                    //tura=2;
                    //System.out.println(numerGracza+" 4");
                    //przekażCzyjaKolej(tura);
                    //System.out.println(numerGracza+" 5");
                    //odbierzKomendę();
                    //System.out.println(numerGracza+" 6");
                    //wyślijPlanszę(serwer.plansza);


                }

            } catch (EOFException e) {
                // nothing to do here
           // } catch (ClassNotFoundException e) {
                //throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            /*
            try{
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

             */
        }

        public void wyślijPlanszę(int[][] plansza) {
            try {
                if (!socket.isConnected()) {
                    System.out.println("Socket nie jest połączony.");
                    return;
                }

                System.out.println("Write numero dos- plansza");
                out.writeObject(plansza);
                out.flush();
                System.out.println("Przekazałem graczowi" + this.numerGracza + " plansze");

                Thread.sleep(500);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}