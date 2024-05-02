import controller.GameController;
import io.cucumber.java.bs.A;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.board.GameBoard;

import model.board.Maps.Easy.Easy_3;
import model.board.Maps.MapsE;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.tile.*;
import view.menus.MenuContainer;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public Main()
    {
        super();
    }

    @Override
    public void start(Stage stage) {

        String style = this.getClass().getResource("/style.css").toExternalForm();
        Image icon = new Image("/Images/icon.png");

        stage.getIcons().add(icon);
        stage.setTitle("RoboRally");

        stage.setScene(new Scene(new AnchorPane()));

        GameController gameController = new GameController(stage);
        Scene scene = new Scene(new MenuContainer(stage, gameController));
        scene.getStylesheets().add(style);

        stage.setOnCloseRequest(e -> System.exit(0));

        stage.setFullScreen(true);
        stage.setResizable(true);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.setHeight(720);
        stage.setWidth(1280);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
