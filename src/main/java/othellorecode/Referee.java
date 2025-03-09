package othellorecode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Referee {
        private Pane gamePane;
        private Board board;
        private Game game;
        private boolean whiteTurn;
        private Controls controls;
        private boolean gameOver;
        private Player player1;
        private Player player2;
        public Referee(Game game, Pane gamePane, Board board){
                this.game = game;
                this.gamePane = gamePane;
                this.board = board;
                this.gameOver = false;
                this.gamePane.setOnMouseClicked((mouseEvent) -> {this.onClick(mouseEvent);});
        }
        public void reset() {
                this.gameOver = false;
                this.whiteTurn = false;
        }
        public void setControls(Controls controls){
                this.controls = controls;
        }
        public void onClick(MouseEvent e) {
                int row = (int) e.getY() / Constants.SQUARE_DIMENSION;
                int col = (int) e.getX() / Constants.SQUARE_DIMENSION;
                if (this.board.getMyArray(row, col).getColor() == Color.GREY) {
                        if (this.whiteTurn) {
                                this.player1.place(row, col);
                        } else {
                                this.player2.place(row, col);
                        }
                }
        }
        public void updateAndMove(){
                if ((this.whiteTurn && this.board.canMakeMove(Constants.MY_BLACK))
                        || (!this.whiteTurn && this.board.canMakeMove(Constants.MY_WHITE))){
                        this.whiteTurn = !this.whiteTurn;
                }
                this.board.countScore();
                if (this.board.returnScore()[Constants.WHITE_SCORE]
                        + this.board.returnScore()[Constants.BLACK_SCORE]
                        == Constants.PLAYABLE_BOARD_AREA){
                        this.gameOver = true;
                }
                this.controls.updateTurnsAndGameOver();
                this.controls.updateScore();
                this.board.greenBoard();
                if (!this.gameOver) {
                        if (this.whiteTurn) {
                                this.player1.move();
                        }
                        if (!this.whiteTurn) {
                                this.player2.move();
                        }
                }
        }
        public boolean returnGameGamOver(){
                return this.gameOver;
        }
        public void setPlayers(Player player1, Player player2){
                this.player1 = player1;
                this.player2 = player2;
        }
        public boolean getWhiteTurn(){
                return this.whiteTurn;
        }
}