package pl.cielebakbozecka.mushrooms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.cielebakbozecka.mushrooms.game.Gracz;
import pl.cielebakbozecka.mushrooms.game.Plansza;

import java.io.IOException;

import static pl.cielebakbozecka.mushrooms.game.Plansza.*;

public class MushroomsApplication extends Application {

    public static Pane pane;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MushroomsApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        pane = new Pane();
        Scene scene = new Scene(pane, 2000/3, 1500/3);


        Plansza plansza = new Plansza(6, 8);
        Gracz gracz1 = new Gracz(0,0, 1,plansza);
        Gracz gracz2 = new Gracz(0,0, 2,plansza);
        plansza.wypełnijGrzybkami();
        plansza.zapiszStanPlanszy();
        plansza.wyświetlBazęPlanszy();
        //gracz.wykonajRuch(plansza);

        stage.setTitle("Grzybki");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        // /
        launch();
        //Plansza plansza = new Plansza(5, 7);
        //Gracz gracz = new Gracz(0,0,plansza);
        //plansza.wypełnijGrzybkami();
        //plansza.zapiszStanPlanszy();
        //plansza.wyświetlBazęPlanszy();
        //gracz.wykonajRuch(plansza);
        // */
    }
}