package othellorecode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
public class Shape {
    private Rectangle rect;
    private Circle circle;
    private int row;
    private int col;
    private Pane gamePane;
    private int num;
    public Shape(Pane gamePane, int row, int col) {
        this.gamePane = gamePane;
        this.createSquare(row, col);
        this.circle = new Circle();
        this.num = Constants.MY_BLANK;
    }
    public void createSquare(int row, int col){
        this.row = row;
        this.col = col;
        this.rect = new Rectangle(col * Constants.SQUARE_DIMENSION,
                row * Constants.SQUARE_DIMENSION, Constants.SQUARE_DIMENSION,
                Constants.SQUARE_DIMENSION);
        this.rect.setStroke(Color.BLACK);
        this.gamePane.getChildren().add(this.rect);
    }
    public void setRectColor(Paint color){
        this.rect.setFill(color);
    }
    public void setCircleColor(Paint color){
        this.circle.setFill(color);
    }
    public void createColoredCircle(int row, int col, Paint color, int num){
        this.row = row;
        this.col = col;
        if (num == Constants.MY_WHITE) {
            this.circle = new Circle(col * Constants.SQUARE_DIMENSION
                    + Constants.CIRCLE_OFFSET, row * Constants.SQUARE_DIMENSION
                    + Constants.CIRCLE_OFFSET, Constants.CIRCLE_RADIUS, Color.WHITE);
        }
        if (num == Constants.MY_BLACK) {
            this.circle = new Circle(col * Constants.SQUARE_DIMENSION
                    + Constants.CIRCLE_OFFSET, row * Constants.SQUARE_DIMENSION
                    + Constants.CIRCLE_OFFSET, Constants.CIRCLE_RADIUS, Color.BLACK);
        }
        this.gamePane.getChildren().add(this.circle);
        this.rect.setFill(Constants.BOARD_COLOR);
        this.num = num;
    }
    public void removeCircle(){
        this.num = Constants.MY_BLANK;
        this.gamePane.getChildren().removeAll(this.circle);
    }
    public Paint getColor(){
        return this.rect.getFill();
    }
    public int getNum(){
        return this.num;
    }
    public int setNum(int opp){
        return this.num = opp;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
}