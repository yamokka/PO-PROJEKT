package pl.cielebakbozecka.mushrooms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.cielebakbozecka.mushrooms.game.Gracz;
import pl.cielebakbozecka.mushrooms.game.Plansza;

public class KlientApka extends Application {

    public static Pane pane;
    //public int idgracza;

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //pane = new Pane();
        //Scene scene = new Scene(pane, 2000 / 3, 1500 / 3);

        //stage.setTitle("Grzybki");
        //stage.setScene(scene);
        //stage.show();

        ŁączenieZSerwerem łącze = new ŁączenieZSerwerem();

        /*
        //łącze.czytomasens();

        //int id = łącze.odbierzNumerGracza();

        //if (!łącze.socket.isConnected()) {
            //System.out.println("Połączenie zostało zamknięte.");
        //}

        //System.out.println("Odebrałam id: "+id);


        //int[][] pl = łącze.odbierzBazęPlanszy();


         */

        //Plansza plansza = new Plansza(pane, 6, 8);

        //Gracz gracz = new Gracz(pane, łącze, 0, 0, plansza);

    }
}
