//package pl.cielebakbozecka.mushrooms;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import pl.cielebakbozecka.mushrooms.game.Gracz;
//import pl.cielebakbozecka.mushrooms.game.Plansza;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.Arrays;
//
//public class MushroomsApplication extends Application {
//
//    private static final String ADRES_SERWERA = "localhost";
//    private static final int PORT_SERWERA = 12345;
//    public static Pane pane;
//
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        pane = new Pane();
//        Scene scene = new Scene(pane, 2000 / 3, 1500 / 3);
//
//        Plansza plansza = new Plansza(6, 8);
//        Gracz gracz2 = new Gracz(0,0, 2,plansza);
//         plansza.wypełnijGrzybkami();
//
//        // plansza.zapiszStanPlanszy();
//        // plansza.wyświetlBazęPlanszy();
//        //gracz.wykonajRuch(plansza);
//
//        stage.setTitle("Grzybki");
//        stage.setScene(scene);
//        stage.show();
//
//
//        try (Socket socket = new Socket(ADRES_SERWERA, PORT_SERWERA);
//             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
//            System.out.println("Połączono z serwerem");
//
//            gracz2.out = out;
//
//            try {
//                int mojeId = (int) in.readObject();
//
//                System.out.println("Se odczytałem id gracza: " + mojeId);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
//
//            System.out.println("Se odpalam wąteczek i słucham");
//
//            try {
//                while (true) {
//                    // zapisanie do Plansza
//                    int[][] planszaLiczbowa = (int[][]) in.readObject();
//
//                    if (plansza != null) {
//                        wyświetlTablicę(planszaLiczbowa);
//                        // plansza.wyświetlBazęPlanszy();
//                    }
//
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (EOFException e) {
//                // nothing to do
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        launch();
//        /*
//        try (ServerSocket server = new ServerSocket(54331);
//             Socket socket = server.accept();
//             InputStream is = socket.getInputStream()) {
//            System.out.println(server);
//            System.out.println(socket);
//            System.out.println(">>> " + is.read());
//            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//                System.out.println(">>> " + ois.readObject());
//                //System.out.println(">>> " + ois.readObject());
//                //System.out.println(">>> " + ois.readObject());
//            }
//        }
//        */
////        int mojeId;
////        //Plansza plansza;
////        //Gracz gracz1;
////
////
////        try (Socket socket = new Socket(ADRES_SERWERA, PORT_SERWERA);
////             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
////             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
////            System.out.println("Połączono z serwerem");
////
////
////
////
////            Plansza planszaDwa = new Plansza(6, 8);
////            Gracz gracz2 = new Gracz(0,0, 2,planszaDwa, out);
////
////            try {
////                mojeId = (int) in.readObject();
////
////                System.out.println("Se odczytałem id gracza: " + mojeId);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////
////
////            System.out.println("Se odpalam wąteczek i słucham");
////            try {
////                while (true) {
////                    int[][] plansza = (int[][]) in.readObject();
////
////                    if (plansza != null) {
////                         wyświetlTablicę(plansza);
////                       // plansza.wyświetlBazęPlanszy();
////                    }
////
////                }
////            } catch (ClassNotFoundException e) {
////                e.printStackTrace();
////            } catch (EOFException e) {
////                // nothing to do
////            }
////
//////            Thread słuchacz = new Thread(() -> {
//////                System.out.println("Se odpalam wąteczek i słucham");
//////                try {
//////                    while (true) {
//////                        int[][] plansza = (int[][]) in.readObject();
//////                        wyświetlTablicę(plansza);
//////                        //plansza.wyświetlBazęPlanszy();
//////                    }
//////                } catch (IOException | ClassNotFoundException e) {
//////                    e.printStackTrace();
//////                }
//////            });
////
////
//////            słuchacz.start();
////
//////            KomendaRuchu komendaRuchu = new KomendaRuchu();
//////            out.writeObject(komendaRuchu);
//////            out.flush();
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//    /*
//            System.out.println(socket);
//            os.write(123);
//            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
//                //oos.writeObject(plansza);
//                //oos.writeObject(new Move(1, 2));
//                //oos.writeObject(Long.valueOf(123456789));
//                //oos.writeObject("EXIT");
//            }
//        }
//
//        launch();
//        //Plansza plansza = new Plansza(5, 7);
//        //Gracz gracz = new Gracz(0,0,plansza);
//        //plansza.wypełnijGrzybkami();
//        //plansza.zapiszStanPlanszy();
//        //plansza.wyświetlBazęPlanszy();
//        //gracz.wykonajRuch(plansza);
//
//    } */
//
//    private static void wyświetlTablicę(int[][] plansza) {
//        for (int[] wiersz : plansza) {
//            for (int komórka : wiersz) {
//                System.out.print(komórka + " ");
//            }
//            System.out.println();
//        }
//    }
//
//}