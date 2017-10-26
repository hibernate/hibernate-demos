package org.hibernate.demos.validation.javafx.sample4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hendrik Ebbers
 */
public class Sample extends Application {

    public void start(final Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(Sample.class.getResource("view.fxml"));
        loader.setController(new ViewController());
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
