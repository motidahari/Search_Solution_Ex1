import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class StateGame {
    private String _Move, _KeyState, _Mark;
    private StateGame _Parent;
    private int[][] _Mat;
    private int _ROW, _COL;
    private Point[] _EmptyPlaces;
    private int _Paid, _DepthDevelopment, _CostForThisPlace;
    private double _DistanceHeuristic;
    //for AStar
    private boolean isOut = false;

    public StateGame(int[][] mat, StateGame parent, String m, Point[] emptyPlaces, int cost) {
        this._ROW = mat.length;
        this._COL = mat[0].length;
        this._EmptyPlaces = new Point[0];
        this._Move = m;
        this._Mark = "";
//        this._Paid = (cost != 0 && parent != null) ? cost + parent._Paid : 0;
        this._CostForThisPlace = cost;
        setMatAndKey(mat);
        setDepthDevelopmentAndParent(parent);
        this._Paid = this._DepthDevelopment*5;
        setEmptyPlaces(emptyPlaces);
    }

    //    public StateGame(int[][] mat){
//        this._ROW = mat.length;
//        this._COL = mat[0].length;
////        this._Mat = mat;
//        _EmptyPlaces = new Point[0];
////        this._Move = m;
////        this._Paid = (cost != 0 && parent != null)? cost + parent._Paid : 0;
////        this._CostForThisPlace = cost;
//        setMatAndKey(mat);
////        setDepthDevelopmentAndParent(parent);
//        setEmptyPlaces();
//    }
    public void setDistance(StateGame goal) {
//        this._Distance = HeuristicFunction.HeuristicValue(this,goal);
    }
    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }
    private void setEmptyPlaces() {
        int index = 0;
        for (int i = 0; i < _ROW; i++) {
            for (int j = 0; j < _COL; j++) {
                if (_Mat[i][j] == -1) {
                    _EmptyPlaces = Arrays.copyOf(_EmptyPlaces, _EmptyPlaces.length + 1);
                    _EmptyPlaces[index++] = new Point(i, j);
                }
            }
        }
    }

    private void setDepthDevelopmentAndParent(StateGame parent) {
        this._Parent = parent;
        _DepthDevelopment = (parent != null) ? _DepthDevelopment = _Parent._DepthDevelopment + 1 : 0;
    }

    private void setMatAndKey(int[][] mat) {
        _Mat = new int[_ROW][_COL];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                _Mat[i][j] = mat[i][j];
            }
        }
        this._KeyState = Arrays.deepToString(mat);
    }

    private void setEmptyPlaces(Point[] emptyPlaces) {
        this._EmptyPlaces = new Point[emptyPlaces.length];
        for (int i = 0; i < emptyPlaces.length; i++) {
            this._EmptyPlaces[i] = emptyPlaces[i];
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        StateGame other = (StateGame) obj;
        for (int i = 0; i < get_Mat().length; i++) { //because arrays in Java are the work of the devil and I don't trust Arrays.deepEquals() to work.
            for (int j = 0; j < get_Mat()[0].length; j++) {
                if (this.get_Mat()[i][j] != other.get_Mat()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link Object#equals(Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     *
     * @return a hash code value for this object.
     * @implSpec As far as is reasonably practical, the {@code hashCode} method defined
     * by class {@code Object} returns distinct integers for distinct objects.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return 1;
    }

    public void set_DistanceHeuristic(double _DistanceHeuristic) {
        this._DistanceHeuristic = _DistanceHeuristic;
    }

    private String getEmptyPlacesAsString() {
        String str = "[";
        for (int i = 0; i < _EmptyPlaces.length; i++) {
            if (i > 0) {
                str += ",(" + _EmptyPlaces[i].x + "," + _EmptyPlaces[i].y + ")";
            } else {
                str += "(" + _EmptyPlaces[i].x + "," + _EmptyPlaces[i].y + ")";
            }
        }
        return str + "]";
    }

    @Override
    public String toString() {
        return "StateGame {\n" +
                "\t_Move = " + _Move + ",\n" +
                "\t_KeyState = " + _KeyState + ",\n" +
                "\t_Mark = " + _Mark + ",\n" +
                ((_Parent != null) ? "\t_ParentKey = " + _Parent.get_KeyState() + ",\n" : "\t_ParentKey = null,\n") +
                "\t_Mat = {\n" + getStringMat(_Mat) + "\t},\n" +
                "\t_EmptyPlaces = " + getEmptyPlacesAsString() + ",\n" +
                "\t_Paid = " + _Paid + ",\n" +
                "\t_DepthDevelopment = " + _DepthDevelopment + ",\n" +
                "\t_CostForThisPlace = " + _CostForThisPlace + ",\n" +
                "\t_DistanceHeuristic = " + _DistanceHeuristic + "\n" +
                "\n}";
    }

    public String getStringMat(int[][] mat) {
        String str = "";
        for (int i = 0; i < mat.length; i++) {
            str += "\t\t[";
            for (int j = 0; j < mat[i].length; j++) {
                if (j == 0) {
                    str += (mat[i][j] != -1)? mat[i][j] : "_";
                } else {
                    str += (mat[i][j] != -1)? ", " + mat[i][j] : ", " + "_";
                }
            }
            str += "]\n";

        }
        return str;
    }

    public String get_Move() {
        return _Move;
    }

    public void set_Move(String _Move) {
        this._Move = _Move;
    }

    public String get_KeyState() {
        return _KeyState;
    }

    public void set_KeyState(String _KeyState) {
        this._KeyState = _KeyState;
    }

    public String get_Mark() {
        return _Mark;
    }

    public void set_Mark(String _Mark) {
        this._Mark = _Mark;
    }

    public StateGame get_Parent() {
        return _Parent;
    }

    public void set_Parent(StateGame _Parent) {
        this._Parent = _Parent;
    }

    public int[][] get_Mat() {
        return _Mat;
    }

    public void set_Mat(int[][] _Mat) {
        this._Mat = _Mat;
    }

    public Point[] getEmptyPlaces() {
        return _EmptyPlaces;
    }

    public int get_Cost() {
        return _Paid;
    }

    public void set_Cost(int _Cost) {
        this._Paid = _Paid;
    }

    public int get_DepthDevelopment() {
        return _DepthDevelopment;
    }

    public void set_DepthDevelopment(int _DepthDevelopment) {
        this._DepthDevelopment = _DepthDevelopment;
    }

    public int get_CostForThisPlace() {
        return _CostForThisPlace;
    }

    public void set_CostForThisPlace(int _CostForThisPlace) {
        this._CostForThisPlace = _CostForThisPlace;
    }

    public double get_DistanceHeuristic() {
        return _DistanceHeuristic;
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

    public Point[] get_EmptyPlaces() {
        return _EmptyPlaces;
    }

    public void set_EmptyPlaces(Point[] _EmptyPlaces) {
        this._EmptyPlaces = _EmptyPlaces;
    }

    public int get_Paid() {
        return _Paid;
    }

    public void set_Paid(int _Paid) {
        this._Paid = _Paid;
    }
}
