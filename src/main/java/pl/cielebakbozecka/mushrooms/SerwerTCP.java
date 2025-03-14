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
import java.util.concurrent.TimeUnit;

public class SerwerTCP {

    public static final int PORT = 12345;

    public PlanszaBezInterfejsu plansza;

    private List<HandlerKlienta> klienci = new ArrayList<>();
    private List<NaszGracz> gracze;
    private NaszGracz obecnyGracz;


    //public int czyjaTura;

    private void petelkaSerwera(){

        plansza = new PlanszaBezInterfejsu(6, 8);
        gracze = new ArrayList<>();

        try (ServerSocket serwerSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer uruchomiony w porcie " + PORT);

            while (true) {
                Socket socket = serwerSocket.accept();

                NaszGracz gracz = new NaszGracz(klienci.size() + 1);
                gracze.add(gracz);

                HandlerKlienta handlerKlienta = new HandlerKlienta(socket, gracz);
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
                    zapiszPoczątek();

                    break;
                }
            }
            TimeUnit.SECONDS.sleep(1);
            przedstawStatusGry();
            wyslijWiadomoscTury();

        } catch (IOException e) {
            System.out.println("Coś poszło nie tak... " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void przedstawStatusGry(){
       System.out.println("Przedstawiam stan gry...");

       for (HandlerKlienta klient : klienci){
           klient.wyslijWiadomosc(new Wiadomosci(TypWiadomosci.PLANSZA, plansza.skopiujPlanszę()));
           klient.wyslijWiadomosc(new Wiadomosci(TypWiadomosci.PUNKTY, gracze.stream().map(NaszGracz::getPunkty).mapToInt(Integer::intValue).toArray()));
       }

        try {
            zapiszdoPliku(plansza);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        try {
            zapiszKoniec();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void zapiszPoczątek()throws IOException{
        try (FileWriter filewriter = new FileWriter("pliczek.txt", true)) {
            filewriter.write("\n");
            filewriter.write("\nNOWA GRA\n");
        }
    }

    public void zapiszdoPliku(PlanszaBezInterfejsu plansza) throws IOException {
        try (FileWriter filewriter = new FileWriter("pliczek.txt", true)) {
            filewriter.write("\n");
            filewriter.write("Stan gry: \n");

            for (int i = 0; i < plansza.wysokośćPlanszy; i++) {
                for (int j = 0; j < plansza.szerokośćPlanszy; j++) {
                    if (plansza.pola[i][j] == plansza.polaNiedostępne) {
                        //filewriter.write(Integer.toString(this.pola[i][j]));
                        filewriter.write("  ");
                    }
                    if (plansza.pola[i][j]== plansza.polaWolne) {
                        filewriter.write("# ");
                        //System.out.print("# ");
                    }
                    if (plansza.pola[i][j]== plansza.dobreGrzyby) {
                        filewriter.write("d ");
                        //System.out.print("d ");
                    }
                    if (plansza.pola[i][j]==plansza.złeGrzyby) {
                        filewriter.write("z ");
                        //System.out.print("z ");
                    }

                    if (plansza.pola[i][j]==plansza.pionek1) {
                        filewriter.write("8 ");
                        //System.out.print("z ");
                    }

                    if (plansza.pola[i][j]==plansza.pionek2) {
                        filewriter.write("& ");
                        //System.out.print("z ");
                    }

                    //filewriter.write(Integer.toString(this.pola[i][j]));
                    //filewriter.write(" ");
                }
                filewriter.write("\n");
            }

            for (NaszGracz gracz : gracze){
                filewriter.write("Punkty gracza");
                filewriter.write(Integer.toString(gracz.getNumer()));
                filewriter.write(":\n");
                filewriter.write(Integer.toString(gracz.getPunkty()));
                filewriter.write("\n");
            }
        }
    }

    public void zapiszKoniec() throws IOException {
        try (FileWriter filewriter = new FileWriter("pliczek.txt", true)) {
            filewriter.write("\n");
            filewriter.write("Ostateczny stan gry: \n");
            int zwyciezca=0;
            int punktyZwyciezcy = 0;

            for (NaszGracz gracz : gracze) {
                filewriter.write("Punkty gracza");
                filewriter.write(Integer.toString(gracz.getNumer()));
                filewriter.write(":\n");
                filewriter.write(Integer.toString(gracz.getPunkty()));
                filewriter.write("\n");
                if (punktyZwyciezcy <= gracz.getPunkty()) {
                    punktyZwyciezcy = gracz.getPunkty();
                    zwyciezca = gracz.getNumer();
                }
            }
            filewriter.write("Wygral gracz");
            filewriter.write(Integer.toString(zwyciezca));
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


    private void wykonajRuch(NaszGracz gracz, int kroki, int kierunek) {
       System.out.println("Zajmuję się przetwarzaniem ruchu gracza "+ gracz.getNumer() +"...");

        Poruszanie.wykonajRuch(plansza, gracz, kroki, kierunek);
        System.out.println(kroki);
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

        new SerwerTCP().petelkaSerwera();

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

        public HandlerKlienta(Socket socket, NaszGracz gracz) {
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
/*
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

 */

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

            } catch (IOException | ClassNotFoundException e) {
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

    }

}