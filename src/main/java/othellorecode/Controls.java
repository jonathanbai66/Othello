package othellorecode;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
public class Controls {
    private Game game;
    private VBox controlsPane;
    private Label scoreLabel;
    private Label turnsAndGameOverLabel;
    private Referee ref;
    private RadioButton[][] playerButtons;
    public Controls(Game othello, Referee ref) {
        this.game = othello;
        this.ref = ref;
        this.controlsPane = new VBox();
        this.controlsPane.setPadding(new Insets(10));
        this.scoreLabel = new Label();
        this.turnsAndGameOverLabel = new Label();
        this.controlsPane.setSpacing(25);
        this.controlsPane.setAlignment(Pos.CENTER);
        this.setupInstructions();
        this.setupScoreLabel();
        this.setupTurnsAndGameOverLabel();
        this.setupMenu();
        this.setupGameButtons();
    }
    public void setupScoreLabel(){
        this.game.countScoreBoard();
        this.controlsPane.getChildren().add(this.scoreLabel);
        this.updateScore();
    }
    public void updateScore(){
        this.scoreLabel.setText("White: " + this.game.countScoreBoard()[Constants.WHITE_SCORE] +
                "     Black: " + this.game.countScoreBoard()[Constants.BLACK_SCORE]);
    }
    public void setupTurnsAndGameOverLabel(){
        this.controlsPane.getChildren().add(this.turnsAndGameOverLabel);
        this.updateTurnsAndGameOver();
    }
    public void updateTurnsAndGameOver(){
        if (this.game.determineGameOver()) {
            if (this.game.countScoreBoard()[Constants.WHITE_SCORE] >
                    this.game.countScoreBoard()[Constants.BLACK_SCORE]) {
                this.turnsAndGameOverLabel.setText("GAME OVER! White wins!");
            }
            if (this.game.countScoreBoard()[Constants.WHITE_SCORE] <
                    this.game.countScoreBoard()[Constants.BLACK_SCORE]) {
                this.turnsAndGameOverLabel.setText("GAME OVER! Black wins!");
            }
        }
        else {
            if(this.ref.getWhiteTurn()) {
                this.turnsAndGameOverLabel.setText("White to move");
            }
            if (!this.ref.getWhiteTurn()) {
                this.turnsAndGameOverLabel.setText("Black to move");
            }
        }
    }
    public Pane getPane() {
        return this.controlsPane;
    }
    private void setupInstructions() {
        Label instructionsLabel = new Label(
                "Select options, then press Apply Settings");
        this.controlsPane.getChildren().add(instructionsLabel);
    }
    private void setupMenu() {
        this.playerButtons = new RadioButton[2][4];
        HBox playersMenu = new HBox();
        playersMenu.setSpacing(10);
        playersMenu.setAlignment(Pos.CENTER);
        playersMenu.getChildren().addAll(this.playerMenu(Constants.STENCIL_WHITE),
                this.playerMenu(Constants.STENCIL_BLACK));
        this.controlsPane.getChildren().add(playersMenu);
    }
    private VBox playerMenu(int player) {
        VBox playerMenu = new VBox();
        playerMenu.setPrefWidth(Constants.CONTROLS_PANE_WIDTH / 2);
        playerMenu.setSpacing(10);
        playerMenu.setAlignment(Pos.CENTER);
        String playerColor = "White";
        if (player == Constants.STENCIL_BLACK) {
            playerColor = "Black";
        }
        Label playerName = new Label(playerColor);
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton humanButton = new RadioButton("Human         ");
        humanButton.setToggleGroup(toggleGroup);
        humanButton.setSelected(true);
        this.playerButtons[player][0] = humanButton;
        for (int i = 1; i < 4; i++) {
            RadioButton computerButton = new RadioButton("Computer " + i + "  ");
            computerButton.setToggleGroup(toggleGroup);
            this.playerButtons[player][i] = computerButton;
        }
        playerMenu.getChildren().add(playerName);
        for (RadioButton rb : this.playerButtons[player]) {
            playerMenu.getChildren().add(rb);
        }
        return playerMenu;
    }
    private void setupGameButtons() {
        Button applySettingsButton = new Button("Apply Settings");
        applySettingsButton.setOnAction((ActionEvent e)->this.applySettings(e));
        applySettingsButton.setFocusTraversable(false);
        Button resetButton = new Button("Reset");
        resetButton.setOnAction((ActionEvent e)-> this.resetHandler(e));
        resetButton.setFocusTraversable(false);
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e)->Platform.exit());
        quitButton.setFocusTraversable(false);
        this.controlsPane.getChildren().addAll(applySettingsButton, resetButton,
                quitButton);
    }
    public void applySettings(ActionEvent e) {
        int whitePlayerMode = 0;
        int blackPlayerMode = 0;
        for (int player = 0; player < 2; player++) {
            for (int mode = 0; mode < 4; mode++) {
                if (this.playerButtons[player][mode].isSelected()) {
                    if (player == Constants.STENCIL_WHITE) {
                        whitePlayerMode = mode;
                    } else {
                        blackPlayerMode = mode;
                    }
                }
            }
        }
        this.game.setPlayer1(whitePlayerMode);
        this.game.setPlayer2(blackPlayerMode);
        this.game.setGame();
    }
    public void resetHandler(ActionEvent e) {
        this.game.clearBoardFromGame();
        this.ref.reset();
        this.scoreLabel.setText("White: 2     Black: 2");
        this.turnsAndGameOverLabel.setText("Black to move");
    }

}