package pl.cielebakbozecka.mushrooms;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class SerwerTCP {

    public static final int PORT = 12345;

    public PlanszaBezInterfejsu plansza;

    private List<HandlerKlienta> klienci;
    private List<NaszGracz> gracze;
    private NaszGracz obecnyGracz;


    public int czyjaTura;

    private void petelkaSerwera(int wiersze, int kolumny){

        plansza = new PlanszaBezInterfejsu(6, 8);
        klienci = new ArrayList<>();
        gracze = new ArrayList<>();

        try (ServerSocket serwerSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer uruchomiony w porcie " + PORT);

            while (true) {
                Socket socket = serwerSocket.accept();

                NaszGracz gracz = new NaszGracz(klienci.size() + 1);
                gracze.add(gracz);

                HandlerKlienta handlerKlienta = new HandlerKlienta(socket, this, gracz);
                klienci.add(handlerKlienta);

                /*
                if (klienci.isEmpty()) {
                    handlerKlienta.numerGracza = 1;
                } else {
                    handlerKlienta.numerGracza = klienci.size() +1;
                }

                 */

                new Thread(handlerKlienta).start();

                if( klienci.size() == 2){
                    obecnyGracz = gracze.getLast();

                    przedstawStatusGry();
                    wyslijWiadomoscTury();
                }
            }
        } catch (IOException e) {
            System.out.println("Coś poszło nie tak... " + e.getMessage());
        }
    }

    private void przedstawStatusGry(){
       System.out.println("Przedstawiam stan gry...");

       for (HandlerKlienta klient : klienci){
           klient.wyslijWiadomosc(new Wiadomosci(TypWiadomosci.PLANSZA, plansza.skopiujPlanszę()));
           klient.wyslijWiadomosc(new Wiadomosci(TypWiadomosci.PUNKTY, gracze.stream().map(NaszGracz::getPunkty).mapToInt(Integer::intValue).toArray()));
       }
    }

    private void wyslijWiadomoscTury(){
        System.out.println("Wysyłanie wiadomości o turze - obecny gracz to " + obecnyGracz.getNumer());

        obecnyGracz = gracze.get((gracze.indexOf(obecnyGracz) + 1) % gracze.size());

        for (HandlerKlienta klient : klienci) {
            klient.wyslijWiadomosc(new Wiadomosci(TypWiadomosci.TURA, obecnyGracz.getNumer()));
        }
    }


    private void wyslijwiadomoscKonca(NaszGracz zwyciezca){
        System.out.println("Przekazuję informację o zakończeniu gry...");

        for (HandlerKlienta klient : klienci) {
            klient.wyslijWiadomosc(new Wiadomosci(TypWiadomosci.KONIEC, zwyciezca.getNumer()));
        }
    }

/*
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

 */


    public synchronized void wykonajRuch(NaszGracz gracz, int kroki, int kierunek) {
       System.out.println("Zajmuję się przetwarzaniem ruchu gracza "+ gracz.getNumer() +"...");

        Poruszanie.wykonajRuch(plansza, gracz, kroki, kierunek);
        przedstawStatusGry();

        if (plansza.czyPusta()){
            gracze.sort(Comparator.comparingInt(NaszGracz::getPunkty).reversed());
            wyslijwiadomoscKonca(gracze.getFirst());
        }
        else{
            wyslijWiadomoscTury();
        }


        //plansza[komendaRuchu.wiersz][komendaRuchu.kolumna] = komendaRuchu.numerGracza;
        //wyślijTablice();
    }

    public static void main(String[] args) {

        new SerwerTCP().petelkaSerwera(6, 8);

        /*
        SerwerTCP serwer = new SerwerTCP(6, 8);
        serwer.start();

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



    private class HandlerKlienta implements Runnable {

        private final Socket socket;
        private final NaszGracz gracz;
        private ObjectOutputStream out;
        //private SerwerTCP serwer;
        //public ObjectInputStream in;
        //public int numerGracza;
        //public int tura;

        public HandlerKlienta(Socket socket, SerwerTCP serwer, NaszGracz gracz) {
            this.socket = socket;
            //this.serwer = serwer;
            this.gracz = gracz;
 /*
            try {
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Nie udało się zainicjalizować strumieni", e);
            }

  */
        }

        public void wyslijWiadomosc(Wiadomosci wiadomosc){
            try{
                out.writeObject(wiadomosc);
                out.flush();
            } catch (IOException e){
                System.out.println("Coś poszło nie tak :c Oto co takiego: " + e.getMessage());
            }
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

        /*

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

         */

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                out = new ObjectOutputStream(socket.getOutputStream());
                wyslijWiadomosc(new Wiadomosci(TypWiadomosci.POWITANIE, gracz.getNumer()));

                /*

                //this.in = in;
                //out.writeObject("Testowa wiadomość");
                //out.flush();
                //System.out.println("Wysłałem testową wiadomość.");


                //przekażNumer();
               // wyślijPlanszę(serwer.plansza);

                 */


                while (true) {
/*
                    serwer.czyjaTura=1;
                    if(numerGracza==1){
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(serwer.czyjaTura);
                        System.out.println(numerGracza+" 2");
                        odbierzKomendę();
                        System.out.println(numerGracza+" 3");
                        wyślijPlanszę(serwer.plansza);
                    }
                    else{
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(serwer.czyjaTura);
                        System.out.println(numerGracza+" 2");
                        odbierzKomendę();
                        System.out.println(numerGracza+" 3");
                        wyślijPlanszę(serwer.plansza);
                    }
                    serwer.czyjaTura=2;
                    if(numerGracza!=1 ){
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(serwer.czyjaTura);
                        System.out.println(numerGracza+" 2");
                        odbierzKomendę();
                        System.out.println(numerGracza+" 3");
                        wyślijPlanszę(serwer.plansza);
                    }
                    else{
                        System.out.println(numerGracza+" 1");
                        przekażCzyjaKolej(serwer.czyjaTura);
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

 */
                    Wiadomosci wiadomosc = (Wiadomosci) in.readObject();

                    if(wiadomosc.getTyp() == TypWiadomosci.RUCH){
                        int kroki = (int) wiadomosc.getDane();
                        int kierunek = 1;
                        wykonajRuch(gracz, kroki, kierunek);
                    }
                }

            } catch (EOFException e) {
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
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
        /*
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
        */
    }

}