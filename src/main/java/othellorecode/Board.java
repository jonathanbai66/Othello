package othellorecode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
public class Board {
    private Shape[][] squares;
    private int[][] boardWeights;
    private Pane gamePane;
    private int[] score;
    private boolean noMoveOver;
    private boolean fullGameOver;
    public Board(Pane gamePane){
        this.gamePane = gamePane;
        this.squares = new Shape[Constants.BOARD_DIMENSION][Constants.BOARD_DIMENSION];
        this.createWeightedBoard();
        this.score = new int[]{Constants.ORIGINAL_SCORE,Constants.ORIGINAL_SCORE};
        this.noMoveOver = false;
        this.fullGameOver = false;
        this.createBoard();
        this.initialPieces();
    }
    public Board(Board boardy){
        this.squares = new Shape[Constants.BOARD_DIMENSION][Constants.BOARD_DIMENSION];
        this.fullGameOver = boardy.fullGameOver;
        this.noMoveOver = boardy.noMoveOver;
        this.score = new int[]{Constants.ORIGINAL_SCORE,Constants.ORIGINAL_SCORE};
        this.score[Constants.WHITE_SCORE] = boardy.score[Constants.WHITE_SCORE];
        this.score[Constants.BLACK_SCORE] = boardy.score[Constants.BLACK_SCORE];
        this.gamePane = new Pane();
        for (int row1 = 0; row1 < 10; row1++) {
            for (int col1 = 0; col1 < 10; col1++) {
                this.squares[row1][col1] = new Shape(this.gamePane, row1, col1);
                if (boardy.getMyArray(row1, col1).getNum() == Constants.MY_WHITE){
                    this.squares[row1][col1].createColoredCircle(row1, col1,
                            Color.WHITE, Constants.MY_WHITE);
                }
                if (boardy.getMyArray(row1, col1).getNum() == Constants.MY_BLACK){
                    this.squares[row1][col1].createColoredCircle(row1, col1,
                            Color.BLACK, Constants.MY_BLACK);
                }
                if (boardy.getMyArray(row1, col1).getColor() == Color.GREY){
                    this.squares[row1][col1].setRectColor(Color.GREY);
                }
            }
        }
    }
    public Pane getPane(){
        return(this.gamePane);
    }
    public void createBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (row == 0 || row == 9 || col == 0 || col == 9){
                    this.squares[row][col] = new Shape(this.gamePane, row, col);
                    this.squares[row][col].setRectColor(Constants.BORDER_COLOR);
                }
                else {
                    this.squares[row][col] = new Shape(this.gamePane, row, col);
                    this.squares[row][col].setRectColor(Constants.BOARD_COLOR);
                }
            }
        }
    }
    public void greenBoard() {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                this.squares[row][col].setRectColor(Constants.BOARD_COLOR);
            }
        }
    }
    public void createWeightedBoard() {
        this.boardWeights = new int[][]{
                {0,0,0,0,0,0,0,0,0,0},
                {0, 200, -760, 20, 25, 25, 30, -70, 200,0},
                {0,-70,-100,-10,-10,-10,-10,-100,-70,0},
                {0,30,-10,2,2,2,2,-10,30,0},
                {0,25,-10,2,2,2,2,-10,25,0},
                {0,25,-10,2,2,2,2,-10,25,0},
                {0,30,-10,2,2,2,2,-10,30,0},
                {0,-70,-100,-10,-10,-10,-10,-100,-70,0},
                {0, 200, -760, 20, 25, 25, 30, -70, 200,0},
                {0,0,0,0,0,0,0,0,0,0}
        };
    }
    public void clearBoard(){
        this.score[Constants.WHITE_SCORE] = 2;
        this.score[Constants.BLACK_SCORE] = 2;
        this.noMoveOver = false;
        this.fullGameOver = false;
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                this.squares[row][col].setRectColor(Constants.BOARD_COLOR);
                this.squares[row][col].removeCircle();
            }
        }
        this.initialPieces();
    }
    public void addPiece(int row, int col, Paint color, int num){
        this.squares[row][col].createColoredCircle(row, col, color, num);
    }
    public void initialPieces(){
        this.squares[4][4].createColoredCircle(4,4, Color.BLACK, Constants.MY_BLACK);
        this.squares[4][5].createColoredCircle(4,5, Color.WHITE, Constants.MY_WHITE);
        this.squares[5][4].createColoredCircle(5,4, Color.WHITE, Constants.MY_WHITE);
        this.squares[5][5].createColoredCircle(5,5, Color.BLACK, Constants.MY_BLACK);
    }
    public Shape getMyArray(int row, int col){
        return this.squares[row][col];
    }
    public int getMyWeightedArray(int row, int col){
        return this.boardWeights[row][col];
    }
    public boolean checkBoardSquare(int row, int col) {
        if (this.squares[row][col].getNum() == Constants.MY_BLANK) {
            return true;
        }
        return false;
    }
    public void countScore(){
        int whiteScore = 0;
        int blackScore = 0;
        int greyCheck = 0;
        int boardCheck = 0;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (this.squares[row][col].getNum() != Constants.MY_BLANK)
                    boardCheck++;
                if (this.squares[row][col].getColor() == Color.GREY){
                    greyCheck++;
                }
                if (this.squares[row][col].getNum() == Constants.MY_WHITE){
                    whiteScore++;
                    this.score[Constants.WHITE_SCORE] = whiteScore;
                }
                if (this.squares[row][col].getNum() == Constants.MY_BLACK){
                    blackScore++;
                    this.score[Constants.BLACK_SCORE] = blackScore;
                }
            }
        }
        if (greyCheck == 0){
            // if there are no grey squares, the player cannot make a move
            this.noMoveOver = true;
        } else {
            this.noMoveOver = false;
        }
        if (boardCheck == Constants.PLAYABLE_BOARD_AREA){
            // if the playable part of the board is full, the game is over
            this.fullGameOver = true;
        }
    }
    public int[] returnScore(){
        return this.score;
    }
    public boolean returnNoMoveOver(){
        return this.noMoveOver;
    }
    public boolean returnFullGameOver(){
        return this.fullGameOver;
    }
    public void pieceOnBoard(int row, int col, Paint color, int num){
        for (int row1 = -1; row1 < 2; row1++) {
            for (int col1 = -1; col1 < 2; col1++) {
                if (row1 == 0 && col1 == 0) {
                    // nothing happens here, as there is no direction at 0,0
                }
                else if (this.checkDirection(row + row1, col + col1,
                        row1, col1, 3 - num, false)) {
                    /* 3 - num determines the opponent player. This is because
                    if the player is white, with a number of 1, then the opponent
                    will be 3 - 1, or 2, indicating the black player. The same is
                    true for black, as the opponent is 3 - 2 is 1, or white.
                     */
                    this.flipPiece(row + row1, col + col1, row1, col1,
                            3 - num, false);
                    // flipping sandwiched pieces in the direction of the move
                }
            }
        }
        this.addPiece(row, col, color, num);
    }
    public void checkSquares(int opp) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                if (this.checkBoardSquare(row, col)) {
                    boolean isValid = false;
                    for (int row1 = -1; row1 < 2; row1++) {
                        for (int col1 = -1; col1 < 2; col1++) {
                            if (row1 == 0 && col1 == 0) {
                            } else if (this.checkDirection(row + row1,
                                    col + col1, row1, col1, opp, false)) {
                                isValid = true;
                                break;
                            }
                        }
                        if (isValid){
                            break;
                        }
                    }
                    if (isValid){
                        this.getMyArray(row, col).setRectColor(Color.GREY);
                    } else {
                        this.getMyArray(row, col).setRectColor(Constants.BOARD_COLOR);
                    }
                }
                else {
                    this.getMyArray(row, col).setRectColor(Constants.BOARD_COLOR);
                }
            }
        }
    }
    public boolean checkDirection(int row, int col, int rowDir, int colDir, int opp,
                                  boolean seenOpp){
        if (row < 1 || col < 1 || row > 9 || col > 9 || this.checkBoardSquare(row, col)){
            return false;
        }
        if (this.getMyArray(row, col).getNum() == opp){
            return this.checkDirection(row + rowDir, col + colDir, rowDir,
                    colDir, opp, true);
        }
        if (seenOpp){
            return true;
        }
        return false;
    }
    public void flipPiece(int row, int col, int rowDir, int colDir, int opp,
                          boolean seenOpp){
        Paint color = Color.HOTPINK;
        if (opp == Constants.MY_WHITE){
            color = Color.BLACK;
        }
        if (opp == Constants.MY_BLACK){
            color = Color.WHITE;
        }
        /*setting the color of the piece after the flip to be the opposite
        of the color that is flipped
         */
        if (this.getMyArray(row, col).getNum() == opp){
            this.flipPiece(row + rowDir, col + colDir, rowDir, colDir, opp,
                    true);
            this.getMyArray(row, col).setCircleColor(color);
            this.getMyArray(row, col).setNum(3 - opp);
            // setting the shape num to be the corresponding new color num
        }
    }
    public boolean canMakeMove(int color){
        this.checkSquares(3 - color);
        this.countScore();
        if (this.noMoveOver){
            return false;
        }
        return true;
    }
}