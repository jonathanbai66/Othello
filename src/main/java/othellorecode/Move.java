package othellorecode;

public class Move {
    private int row;
    private int col;
    private int value;
    public Move(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
    }
    public int returnRow(){
        return this.row;
    }
    public int returnCol(){
        return this.col;
    }
    public int returnValue(){
        return this.value;
    }
}