package pl.cielebakbozecka.mushrooms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.cielebakbozecka.mushrooms.game.Gracz;
import pl.cielebakbozecka.mushrooms.game.Plansza;

import java.io.IOException;

public class HelloController {

    private Plansza plansza;
    private Gracz gracz = new Gracz(0, 0, plansza);

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");

        plansza = new Plansza(5, 7);

    }
}