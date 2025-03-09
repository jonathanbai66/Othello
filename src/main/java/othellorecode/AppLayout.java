package othellorecode;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AppLayout {
    private BorderPane root;
    public AppLayout() {
        this.root = new BorderPane();
        Pane gamePane = new Pane();
        Game game = new Game(gamePane);
        HBox bigPane = new HBox(gamePane, game.getControlPane());
        this.root.setCenter(bigPane);
    }
    public BorderPane getRoot() {
        return this.root;
    }
}