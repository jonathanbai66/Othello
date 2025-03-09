package othellorecode;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.scene.paint.Color;
public class Computer implements Player{
    private Board board;
    private int n;
    private Game game;
    private Referee ref;
    private int color;
    private Timeline timeline;
    public Computer (int n, Game game, Board board, Referee ref, int color){
        this.board = board;
        this.n = n;
        this.game = game;
        this.ref = ref;
        this.color = color;
        this.setupTimeline();
    }
    @Override
    public void place(int row, int col) {
    }
    public void setupTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.KEYFRAME_DURATION),
                (ActionEvent r) -> this.computerPlace());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }
    public void move(){
        this.timeline.play();
    }
    public void computerPlace(){
        this.timeline.stop();
        boolean isWhiteTurn = this.color == Constants.MY_WHITE;
        Move move = this.getBestMove(this.board, isWhiteTurn, this.n);
        if (move.returnRow() == Constants.MY_BLANK && move.returnCol() == Constants.MY_BLANK){
            return;
        }
        if (this.color == Constants.MY_WHITE) {
            this.board.pieceOnBoard(move.returnRow(), move.returnCol(), Color.WHITE,
                    Constants.MY_WHITE);
        }
        if (this.color == Constants.MY_BLACK) {
            this.board.pieceOnBoard(move.returnRow(), move.returnCol(), Color.BLACK,
                    Constants.MY_BLACK);
        }
        this.ref.updateAndMove();
    }
    public int evaluateBoard(Board board, boolean isWhiteTurn){
        int whiteAd = 0;
        int blackAd = 0;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (board.getMyArray(row,col).getNum() == Constants.MY_WHITE){
                    whiteAd += this.board.getMyWeightedArray(row,col);
                }
                if (board.getMyArray(row,col).getNum() == Constants.MY_BLACK){
                    blackAd += this.board.getMyWeightedArray(row,col);
                }
            }
        }
        if (isWhiteTurn){
            return whiteAd - blackAd;
        }
        if (!isWhiteTurn){
            return blackAd - whiteAd;
        }
        return whiteAd;
    }
    public Move getBestMove(Board board, boolean isWhiteTurn, int intelligience){
        if (isWhiteTurn){
            board.checkSquares(Constants.MY_BLACK);
        }
        if (!isWhiteTurn){
            board.checkSquares(Constants.MY_WHITE);
        }
        board.countScore();
        Move bestMove = null;
        if (board.returnFullGameOver()){
            if (board.returnScore()[Constants.WHITE_SCORE]
                    > board.returnScore()[Constants.BLACK_SCORE]) {
                if (isWhiteTurn) {
                    bestMove = new Move(Constants.DUMMY_COORD, Constants.DUMMY_COORD,
                            Constants.EXTREME_VALUE);
                    return bestMove;
                }
                else{
                    bestMove = new Move(Constants.DUMMY_COORD, Constants.DUMMY_COORD,
                            -Constants.EXTREME_VALUE);
                    return bestMove;
                }
            }
            if (board.returnScore()[Constants.WHITE_SCORE]
                    < board.returnScore()[Constants.BLACK_SCORE]) {
                if (isWhiteTurn) {
                    bestMove = new Move(Constants.DUMMY_COORD, Constants.DUMMY_COORD,
                            -Constants.EXTREME_VALUE);
                    return bestMove;
                }
                else{
                    bestMove = new Move(Constants.DUMMY_COORD, Constants.DUMMY_COORD,
                            Constants.EXTREME_VALUE);
                    return bestMove;
                }
            }
            if (board.returnScore()[Constants.WHITE_SCORE]
                    == board.returnScore()[Constants.BLACK_SCORE]) {
                bestMove = new Move(Constants.DUMMY_COORD, Constants.DUMMY_COORD,
                        Constants.NEUTRAL_VALUE);
                return bestMove;
            }
        }
        if (board.returnNoMoveOver()) {
            if (intelligience <= 0) {
                bestMove = new Move(Constants.DUMMY_COORD, Constants.DUMMY_COORD,
                        -Constants.EXTREME_VALUE);
                return bestMove;
            }
            else {
                bestMove = new Move(Constants.DUMMY_COORD, Constants.DUMMY_COORD,
                        -this.getBestMove(board, !isWhiteTurn,intelligience - 1).returnValue());
                return bestMove;
            }
        }

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                if (board.getMyArray(row, col).getColor() == Color.GREY) {
                    Board boardy = new Board(board);
                    if (isWhiteTurn) {
                        boardy.pieceOnBoard(row, col, Color.WHITE, Constants.MY_WHITE);
                    }
                    if (!isWhiteTurn) {
                        boardy.pieceOnBoard(row, col, Color.BLACK, Constants.MY_BLACK);
                    }
                    Move move;
                    if (intelligience <= 1) {
                        move = new Move (row, col, this.evaluateBoard(boardy, isWhiteTurn));
                    } else {
                        move = new Move (row, col, -this.getBestMove(boardy,
                                !isWhiteTurn,intelligience - 1).returnValue());
                    }
                    if (bestMove == null || move.returnValue() >= bestMove.returnValue()) {
                        bestMove = move;
                    }
                }
            }
        }
        return bestMove;
    }
}
