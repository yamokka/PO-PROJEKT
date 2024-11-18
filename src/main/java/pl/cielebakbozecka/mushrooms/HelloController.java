package pl.cielebakbozecka.mushrooms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.cielebakbozecka.mushrooms.game.Gracz;
import pl.cielebakbozecka.mushrooms.game.Plansza;

public class HelloController {

    private Plansza plansza;
    private Gracz gracz = new Gracz();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        plansza = new Plansza(5, 7);

        gracz.wykonajRuch(plansza);
    }
}