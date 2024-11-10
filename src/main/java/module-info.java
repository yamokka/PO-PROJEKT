module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens pl.cielebakbozecka.mushrooms to javafx.fxml;
    exports pl.cielebakbozecka.mushrooms;
}