import java.awt.*;
import java.util.Arrays;

public class StateGame {
    private String _Move, _KeyState, _Mark;
    private StateGame _Parent;
    private int [][] _Mat;
    private int _ROW,_COL;
    private Point[] _EmptyPlaces;
    private int _Paid,_DepthDevelopment, _CostForThisPlace;
    private double _DistanceHeuristic;

    public StateGame(int[][] mat, StateGame parent, String m, Point[] emptyPlaces, int cost){
        _ROW = mat.length;
        _COL = mat[0].length;
        _EmptyPlaces = new Point[0];
        this._Move = m;
        this._Paid = (cost != 0 && parent != null)? cost + parent._Paid : 0;
        this._CostForThisPlace = cost;
        setMatAndKey(mat);
        setDepthDevelopmentAndParent(parent);
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
    public void setDistance(StateGame goal){
//        this._Distance = HeuristicFunction.HeuristicValue(this,goal);
    }
    private void setEmptyPlaces() {
        int index = 0;
        for (int i = 0; i < _ROW; i++) {
            for (int j = 0; j < _COL; j++) {
                if(_Mat[i][j] == -1){
                    _EmptyPlaces = Arrays.copyOf(_EmptyPlaces, _EmptyPlaces.length + 1);
                    _EmptyPlaces[index++] = new Point(i,j);
                }
            } 
        }
    }

    private void setDepthDevelopmentAndParent(StateGame parent) {
        this._Parent = parent;
        _DepthDevelopment = (parent != null)? _DepthDevelopment = _Parent._DepthDevelopment + 1 : 0;
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
    public boolean equals(Object object) {
        return (this == object)? true : (getClass() != object.getClass() || object == null)? false: _KeyState.equals(((StateGame) object)._KeyState);
    }


    public void set_DistanceHeuristic(double _DistanceHeuristic) {
        this._DistanceHeuristic = _DistanceHeuristic;
    }
    private String getEmptyPlacesAsString() {
        String str = "[";
        for (int i = 0; i < _EmptyPlaces.length; i++) {
            if(i > 0){
                str += ",("+ _EmptyPlaces[i].x +","+ _EmptyPlaces[i].y +")";
            }else{
                str += "("+ _EmptyPlaces[i].x +","+ _EmptyPlaces[i].y +")";
            }
        }
        return str + "]";
    }
    @Override
    public String toString() {
        return "StateGame {\n" +
                "\t_Move = " + _Move + ",\n" +
                "\t_KeyState = " + _KeyState +",\n" +
                "\t_Mark = " + _Mark +",\n" +
                ((_Parent != null)? "\t_ParentKey = " + _Parent.get_KeyState() +",\n"  : "\t_ParentKey = null,\n") +
                "\t_Mat = {\n" + getStringMat(_Mat) +"\t},\n" +
                "\t_EmptyPlaces = " + getEmptyPlacesAsString() +",\n" +
                "\t_Paid = " + _Paid +",\n" +
                "\t_DepthDevelopment = " + _DepthDevelopment +",\n" +
                "\t_CostForThisPlace = " + _CostForThisPlace +",\n" +
                "\t_DistanceHeuristic = " + _DistanceHeuristic +"\n" +
                "\n}";
    }

    public String getStringMat(int[][] mat) {
        String str = "";
        for (int i = 0; i < mat.length; i++) {
            str += "\t\t[";
            for (int j = 0; j < mat[i].length; j++) {
                if(j == 0){
                    str += mat[i][j];
                }else{
                    str += ", " + mat[i][j];
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
