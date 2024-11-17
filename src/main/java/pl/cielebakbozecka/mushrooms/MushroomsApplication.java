package pl.cielebakbozecka.mushrooms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.cielebakbozecka.mushrooms.game.Plansza;

import java.io.IOException;

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

        Plansza plansza = new Plansza(5);
        plansza.zapiszStanPlanszy();
        plansza.wypełnijGrzybkami(3, 2);
        plansza.zapiszStanPlanszy();


        for (int i = 0; i < plansza.rozmiar_planszy; i++) {
            for (int j = 0; j < plansza.rozmiar_planszy; j++) {
                System.out.print(plansza.pola[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }

        launch();
    }
}
