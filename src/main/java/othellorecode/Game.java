package othellorecode;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Game {
    private Board board;
    private Pane gamePane;
    private VBox vPane;
    private Controls controls;
    private Referee ref;
    private Player player1;
    private Player player2;
    public Game(Pane gamePane){
        this.gamePane = gamePane;
        this.board = new Board (this.gamePane);
        this.ref = new Referee(this, gamePane, this.board);
        this.controls = new Controls(this, this.ref);
        this.createControlPane();
        this.ref.setControls(this.controls);
    }
    public void setGame(){
        if (this.ref.getWhiteTurn()) {
            this.player1.move();
        } else {
            this.player2.move();
        }
    }
    private void createControlPane() {
        this.vPane = new VBox();
        this.vPane.getChildren().add(this.controls.getPane());
        this.vPane.setFocusTraversable(false);
    }
    public Pane getControlPane() {
        return this.vPane;
    }
    public int[] countScoreBoard(){
        return this.board.returnScore();
    }
    public void clearBoardFromGame(){
        this.board.clearBoard();
    }
    public boolean determineGameOver(){
        if (this.ref != null){
            return this.ref.returnGameGamOver();
        }
        return false;
    }
    public void setPlayer1(int n){
        if (n == Constants.HUMAN_N){
            this.player1 = new Human(Constants.HUMAN_N, this, this.board, this.ref,
                    Constants.MY_WHITE);
        } else {
            this.player1 = new Computer(n, this, this.board, this.ref,
                    Constants.MY_WHITE);
        }
        this.ref.setPlayers(this.player1, this.player2);
    }
    public void setPlayer2(int n){
        if (n == Constants.HUMAN_N){
            this.player2 = new Human(Constants.HUMAN_N, this, this.board, this.ref,
                    Constants.MY_BLACK);
        } else {
            this.player2 = new Computer(n, this, this.board, this.ref,
                    Constants.MY_BLACK);
        }
        this.ref.setPlayers(this.player1, this.player2);
    }
}