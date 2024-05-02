package view.menus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StandardMenu extends BorderPane {
    protected ProgressIndicator progressIndicator = new ProgressIndicator();
    protected Label titleLabel = new Label("title");
    protected Label loadingLabel = new Label("Loading...");
    protected VBox buttonBox = new VBox(30);
    protected BorderPane bottomPane = new BorderPane();
    protected MenuContainer menuContainer;
    protected StandardMenu parent;
    protected Stage stage;


    public StandardMenu(Stage stage, MenuContainer menuContainer, StandardMenu parent) {

        this.menuContainer = menuContainer;
        this.parent = parent;
        this.stage = stage;

        progressIndicator.indeterminateProperty();
        progressIndicator.setVisible(false);

        HBox hBox = new HBox(10);

        loadingLabel.getStyleClass().add("loading-label");
        loadingLabel.setVisible(false);
        hBox.getChildren().addAll(loadingLabel, progressIndicator);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomPane.setRight(hBox);
        setBottom(bottomPane);

        BorderPane.setAlignment(titleLabel, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(bottomPane, Pos.BOTTOM_RIGHT);

        // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(titleLabel);
        setCenter(buttonBox);

    }

    public StandardMenu(Stage stage, StandardMenu parent){
        this(stage, parent.getMenuContainer(), parent);
    }

    public void showLoading(boolean isVisible){
        loadingLabel.setVisible(isVisible);
        progressIndicator.setVisible(isVisible);
    }
    protected void goToMenu(StandardMenu menu){
        this.setVisible(false);
        if (menuContainer.getChildren().contains(menu)){
            menu.setVisible(true);
            menu.showLoading(false);
            return;
        }
        menuContainer.getChildren().add(menu);
        menu.setVisible(true);
        menu.showLoading(false);
    }

    protected MenuContainer getMenuContainer(){
        return menuContainer;
    }
}
