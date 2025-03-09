package othellorecode;

import javafx.scene.paint.Color;
public class Human implements Player {
    private Board board;
    private int n;
    private Game game;
    private Referee ref;
    private int color;
    public Human (int n, Game game, Board board, Referee ref, int color){
        this.board = board;
        this.n = n;
        this.game = game;
        this.ref = ref;
        this.color = color;
    }
    @Override
    public void place(int row, int col) {
        this.board.getPane().setOnMouseClicked((mouseEvent) -> {
            this.ref.onClick(mouseEvent);
        });
        if (this.color == Constants.MY_WHITE) {
            this.board.pieceOnBoard(row, col, Color.WHITE, Constants.MY_WHITE);
        }
        if (this.color == Constants.MY_BLACK) {
            this.board.pieceOnBoard(row, col, Color.BLACK, Constants.MY_BLACK);
        }
        this.ref.updateAndMove();
    }
    @Override
    public void move(){
        this.board.checkSquares( 3 - this.color);
    }

}
