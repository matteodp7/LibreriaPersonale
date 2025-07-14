package it.libreriaPersonale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;




public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/libro_view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        Image icon = new Image(getClass().getResourceAsStream("/img/icon.png"));
        stage.getIcons().add(icon);

        stage.setTitle("Libreria Personale");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
