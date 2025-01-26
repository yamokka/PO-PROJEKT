package pl.cielebakbozecka.mushrooms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import pl.cielebakbozecka.mushrooms.game.Gracz;
import pl.cielebakbozecka.mushrooms.game.Plansza;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class KlientApka extends Application {

    private static final String ADRES_SERWERA = "localhost";
    private static final int PORT_SERWERA = 12345;

    public ObjectOutputStream out;
    public int idGracza;
    private boolean mojaKolej = false;

    private GridPane planszaGrid;
    private GridPane topGrid;

    private Label graczLabel;
    private Label turaLabel;

    private Label punktyGracza1Label;
    private Label punktyGracza2Label;
    private Label zwyciezcaLabel;

    private Button ruchButton;

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {

        przygotujInterfejs(stage);
        new Thread(this::pętelkaKlienta).start();

        /*
        //pane = new Pane();
        //Scene scene = new Scene(pane, 2000 / 3, 1500 / 3);

        //stage.setTitle("Grzybki");
        //stage.setScene(scene);
        //stage.show();

        //ŁączenieZSerwerem łącze = new ŁączenieZSerwerem();


        //łącze.czytomasens();

        //int id = łącze.odbierzNumerGracza();

        //if (!łącze.socket.isConnected()) {
            //System.out.println("Połączenie zostało zamknięte.");
        //}

        //System.out.println("Odebrałam id: "+id);


        //int[][] pl = łącze.odbierzBazęPlanszy();




        //Plansza plansza = new Plansza(pane, 6, 8);

        //Gracz gracz = new Gracz(pane, łącze, 0, 0, plansza);

        */
    }

    private void przygotujInterfejs(Stage stage){
        VBox root = new VBox();

        BackgroundImage tło = new BackgroundImage(new Image("file:tlo.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        root.setBackground(new Background(tło));

        topGrid = new GridPane();
        topGrid.setPadding(new Insets(8));
        topGrid.setHgap(16D);

        topGrid.setStyle("-fx-background-color: lightblue;");

        graczLabel = new Label("Gracz: ");
        graczLabel.setStyle("-fx-font-weight: bold;");

        turaLabel = new Label("Czekam...");

        punktyGracza1Label = new Label("Punkty Gracza1: 0");
        punktyGracza2Label = new Label("Punkty Gracza2: 0");

        zwyciezcaLabel = new Label("");
        zwyciezcaLabel.setStyle("-fx-font-weight: bold;");
        zwyciezcaLabel.setStyle("-fx-font-size: 20px;");

        ruchButton = new Button("Rzuć kostką");
        ruchButton.setDisable(true);
        ruchButton.setOnAction(e -> rzutIRuch());

        topGrid.addColumn(0, graczLabel);
        topGrid.addColumn(1, turaLabel);
        topGrid.addColumn(2, new Separator());
        topGrid.addColumn(3, ruchButton);
        topGrid.addColumn(4, new Separator());
        topGrid.addColumn(5, punktyGracza1Label);
        topGrid.addColumn(6, punktyGracza2Label);
        topGrid.addColumn(7, new Separator());
        topGrid.addColumn(8, zwyciezcaLabel);

        planszaGrid = new GridPane();

        root.getChildren().addAll(topGrid, planszaGrid);

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Czarci krąg");
        stage.show();










    }

    private void pętelkaKlienta() {
        try (Socket socket = new Socket(ADRES_SERWERA, PORT_SERWERA)) {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Wiadomosci wiadomosc = (Wiadomosci) in.readObject();

                switch (wiadomosc.getTyp()) {
                    case POWITANIE -> {
                        idGracza = (int) wiadomosc.getDane();
                        Platform.runLater(() -> graczLabel.setText("Gracz: " + idGracza));
                    }
                    case PLANSZA -> {
                        int[][] plansza = (int[][]) wiadomosc.getDane();
                        Platform.runLater(() -> drawPlansza(plansza));
                    }
                    case PUNKTY -> {
                        int[] punkty = (int[]) wiadomosc.getDane();
                        Platform.runLater(() -> {
                            punktyGracza1Label.setText("Punkty Gracza1: " + punkty[0]);
                            punktyGracza2Label.setText("Punkty Gracza2: " + punkty[1]);
                        });
                    }
                    case TURA -> {
                        int obecnyGracz = (int) wiadomosc.getDane();
                        mojaKolej = (obecnyGracz == idGracza);

                        Platform.runLater(() ->{
                            turaLabel.setText("Tura " + obecnyGracz);
                            ruchButton.setDisable(!mojaKolej);
                        });
                    }
                    case KONIEC -> {
                        int zwycięzca = (int) wiadomosc.getDane();
                        Platform.runLater(() -> zwyciezcaLabel.setText("GRACZ " + zwycięzca + " WYGRAŁ"));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Coś poszło nie tak... " + e.getMessage());
        }
    }

    private void drawPlansza(int[][] plansza){
        planszaGrid.getChildren().clear();

        for (int i = 0; i < plansza.length; i++){
            for (int j = 0; j <plansza[i].length; j++){
                StackPane stackPane = new StackPane();

                Rectangle komórka = new Rectangle();
                komórka.setWidth(88);
                komórka.setHeight(88);
                komórka.setFill(new ImagePattern(new Image("file:sciezka.png")));

                ImageView overlayImage = null;

                if (plansza[i][j] == 6){
                    overlayImage = new ImageView(new Image("file:pionek1.png"));
                } else if (plansza[i][j] == 9){
                    overlayImage = new ImageView(new Image("file:pionek2.png"));
                } else if (plansza[i][j] == 0){
                    komórka.setStyle("-fx-opacity: 0.5;");
                } else if (plansza[i][j] == 2) {
                    overlayImage = new ImageView(new Image("file:dobryGrzyb.png"));
                } else if (plansza[i][j] == 3) {
                    overlayImage = new ImageView(new Image("file:złyGrzyb.png"));
                }

                if (overlayImage != null){
                    overlayImage.setFitWidth(88);
                    overlayImage.setFitHeight(88);
                }

                stackPane.getChildren().add(komórka);
                if (overlayImage != null){
                    stackPane.getChildren().add(overlayImage);
                }

                planszaGrid.add(stackPane, j, i);
            }
        }
    }

    private void rzutIRuch(){

        if(!mojaKolej) return;

        int kroki = new Random().nextInt(6) +1;
        wyślijWiadomość(new Wiadomosci(TypWiadomosci.RUCH, kroki));

        mojaKolej = false;
        ruchButton.setDisable(true);

    }

    private void wykonajRuch(int a, int b){
        Random rand = new Random();
        int oczka = rand.nextInt(6) + 1;
        System.out.println("Wylosowano "+oczka+" oczek. Wybierz stronę ruchu: 1-zgodnie z ruchem zegara/2-niezgodnie");
        Scanner scan = new Scanner(System.in);
        int kierunek = scan.nextInt();
        wyślijWiadomość(new Wiadomosci(TypWiadomosci.RUCH, oczka));

        mojaKolej = false;
        /*

        if (kierunek == 1) {
            wylosowanePoleZegar(oczka, a, b);
        } else {
            wylosowanePoleNieZegar(oczka, a, b);
        }

         */
    }

    private void wyślijWiadomość(Wiadomosci wiadomosc){
        try{
            out.writeObject(wiadomosc);
            out.flush();
        } catch (IOException e){
            System.out.println("Coś poszło nie tak... " +e.getMessage());
        }
    }






}
