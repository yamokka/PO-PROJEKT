package pl.cielebakbozecka.mushrooms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cielebakbozecka.mushrooms.game.Gracz;
import pl.cielebakbozecka.mushrooms.game.Plansza;

import java.io.IOException;

import static pl.cielebakbozecka.mushrooms.game.Plansza.*;

public class MushroomsApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MushroomsApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        Plansza plansza = new Plansza(5, 7);
        Gracz gracz = new Gracz(0,0,plansza);
        plansza.wypełnijGrzybkami();
        plansza.zapiszStanPlanszy();
        plansza.wyświetlBazęPlanszy();
        gracz.wykonajRuch(plansza);

        launch();
    }
}
