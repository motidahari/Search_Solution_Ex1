import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class HeuristicFunction implements Comparator<StateGame> {

    private int _ROW, _COL;
    private StateGame _Start, _Goal;
    private double _Cost;
    private double _NormalCost = 5, _VerticalCost = 7, _HorizontalCost = 6;
    private Point[] _EmptyPlaces;

    public HeuristicFunction(StateGame _Start, StateGame _Goal) {
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
        if (_Start.getEmptyPlaces().length == 2) {
            if (_Start.get_Parent() != null) {
                _Cost = 5;
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
                if (mat[i][j] == -1) {
                    result += getValueFromPoint(compareCurrWithGoal(mat[i][j], goal), false, i, j);
                } else {
                    result += _Cost * getValueFromPoint(compareCurrWithGoal(mat[i][j], goal), true, i, j);
                }
            }
        }
        return result / 5;
    }

    private double getValueFromPoint(Point compareCurrWithGoal, boolean flag, int i, int j) {
        if (flag) {
            return _Cost * (Math.abs(i - compareCurrWithGoal.getX()) + Math.abs(j - compareCurrWithGoal.getY()));
        } else {
            return (Math.abs(i - compareCurrWithGoal.getX()) + Math.abs(j - compareCurrWithGoal.getY()));
        }
    }

    private Point compareCurrWithGoal(int val, int[][] goal) {
        Point p = new Point(-1, -1);
        for (int i = 0; i < _ROW; i++) {
            for (int j = 0; j < _COL; j++) {
                if (val == goal[i][j]) {
                    p.setLocation(new Point(i, j));
                    break;
                }
            }
        }
        return p;
    }

    private double getCost() {
        if (Operator.checkForEmptyVertical(this._Start.get_EmptyPlaces()[0].x, this._Start.get_EmptyPlaces()[0].y, this._Start.get_EmptyPlaces()[1].x, this._Start.get_EmptyPlaces()[1].y, _ROW)) {
            return 7;
        } else if (Operator.checkForEmptyHorizontal(this._Start.get_EmptyPlaces()[0].x, this._Start.get_EmptyPlaces()[0].y, this._Start.get_EmptyPlaces()[1].x, this._Start.get_EmptyPlaces()[1].y, _COL)) {
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

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     * The implementor must ensure that {@code sgn(compare(x, y)) ==
     * -sgn(compare(y, x))} for all {@code x} and {@code y}.  (This
     * implies that {@code compare(x, y)} must throw an exception if and only
     * if {@code compare(y, x)} throws an exception.)<p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
     * {@code compare(x, z)>0}.<p>
     * <p>
     * Finally, the implementor must ensure that {@code compare(x, y)==0}
     * implies that {@code sgn(compare(x, z))==sgn(compare(y, z))} for all
     * {@code z}.<p>
     * <p>
     * It is generally the case, but <i>not</i> strictly required that
     * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."<p>
     * <p>
     * In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(StateGame o1, StateGame o2) {
        //1. f(o1) > f(o2)
        //else f(o2)>f(o1)
        int o1F = getFCost(o1);
        int o2F = getFCost(o2);
        if (o1F > o2F) {
            return 1;
        } else if (o1F < o2F) {
            return -1;
        }else{
            return o1.get_DepthDevelopment() - o2.get_DepthDevelopment();
        }


    }

    private int getFCost(StateGame state) {
        Manhattan heu = new Manhattan(_Goal);
        return state.get_Cost() + heu.getH(state);
    }
}
