import java.awt.*;
import java.util.Arrays;

public class HeuristicFunction {

    private int _ROW,_COL;
    private StateGame _Start,_Goal;
    private double _Cost;
    private double _NormalCost = 5, _VerticalCost = 7, _HorizontalCost = 6;
    private Point[] _EmptyPlaces;

    public HeuristicFunction(StateGame _Start, StateGame _Goal){
        this._ROW = _Start.get_ROW();
        this._COL = _Start.get_COL();
        this._Start = _Start;
        this._Goal = _Goal;
        setEmptyPlaces(_Start.get_EmptyPlaces());
    }

    private void setEmptyPlaces(Point[] emptyPlaces) {
        this._EmptyPlaces = new Point[emptyPlaces.length];
        for (int i = 0; i < emptyPlaces.length; i++) {
            this._EmptyPlaces[i] = emptyPlaces[i];
        }
    }

    public double HeuristicVal(Point[] emptyPlaces) {
        if(_Start.getEmptyPlaces().length == 2){
            if(_Start.get_Parent() != null){
                _Cost = 4.5;
            }
        }
        this._Cost = getCost();
        return getHeuristic();
    }

    private double getHeuristic() {
        int[][] mat = _Start.get_Mat();
        int[][] goal = _Goal.get_Mat();
        double result = 0;
        for (int i = 0; i < _ROW; i++) {
            for (int j = 0; j < _COL; j++) {
                if(mat[i][j] == -1){
                    result += getValueFromPoint(compareCurrWithGoal(mat[i][j], goal), false, i, j);
                }else{
                    result += _Cost * getValueFromPoint(compareCurrWithGoal(mat[i][j], goal), true, i, j);
                }
            }
        }
        return result/5;
    }

    private double getValueFromPoint(Point compareCurrWithGoal, boolean flag, int i, int j) {
        if(flag){
            return _Cost * (Math.abs(i - compareCurrWithGoal.getX()) + Math.abs(j - compareCurrWithGoal.getY()));
        }else{
            return (Math.abs(i - compareCurrWithGoal.getX()) + Math.abs(j - compareCurrWithGoal.getY()));
        }
    }

    private Point compareCurrWithGoal(int val, int[][] goal) {
        Point p = new Point(-1,-1);
        for (int i = 0; i < _ROW; i++) {
            for (int j = 0; j < _COL; j++) {
                if(val == goal[i][j]){
                    p.setLocation(new Point(i,j));
                    break;
                }
            }
        }
        return p;
    }

    private double getCost() {
        if(Operator.checkForEmptyVertical(this._Start.get_EmptyPlaces()[0].x,this._Start.get_EmptyPlaces()[0].y,this._Start.get_EmptyPlaces()[1].x,this._Start.get_EmptyPlaces()[1].y,_ROW)) {
            return 7;
        }else if(Operator.checkForEmptyHorizontal(this._Start.get_EmptyPlaces()[0].x,this._Start.get_EmptyPlaces()[0].y,this._Start.get_EmptyPlaces()[1].x,this._Start.get_EmptyPlaces()[1].y,_COL)){
            return 6;
        }
        return 5;
    }

    public int get_ROW() {
        return _ROW;
    }

    public void set_ROW(int _ROW) {
        this._ROW = _ROW;
    }

    public int get_COL() {
        return _COL;
    }

    public void set_COL(int _COL) {
        this._COL = _COL;
    }

    public StateGame get_Start() {
        return _Start;
    }

    public void set_Start(StateGame _Start) {
        this._Start = _Start;
    }

    public StateGame get_Goal() {
        return _Goal;
    }

    public void set_Goal(StateGame _Goal) {
        this._Goal = _Goal;
    }

    public double get_Cost() {
        return _Cost;
    }

    public void set_Cost(double _Cost) {
        this._Cost = _Cost;
    }

    public double get_NormalCost() {
        return _NormalCost;
    }

    public void set_NormalCost(double _NormalCost) {
        this._NormalCost = _NormalCost;
    }

    public double get_VerticalCost() {
        return _VerticalCost;
    }

    public void set_VerticalCost(double _VerticalCost) {
        this._VerticalCost = _VerticalCost;
    }

    public double get_HorizontalCost() {
        return _HorizontalCost;
    }

    public void set_HorizontalCost(double _HorizontalCost) {
        this._HorizontalCost = _HorizontalCost;
    }

    public Point[] get_EmptyPlaces() {
        return _EmptyPlaces;
    }

    public void set_EmptyPlaces(Point[] _EmptyPlaces) {
        this._EmptyPlaces = _EmptyPlaces;
    }

    @Override
    public String toString() {
        return "HeuristicFunction{" +
                "_ROW=" + _ROW +
                ", _COL=" + _COL +
                ", _Start=" + _Start +
                ", _Goal=" + _Goal +
                ", _Cost=" + _Cost +
                ", _NormalCost=" + _NormalCost +
                ", _VerticalCost=" + _VerticalCost +
                ", _HorizontalCost=" + _HorizontalCost +
                ", _EmptyPlaces=" + Arrays.toString(_EmptyPlaces) +
                '}';
    }
}
