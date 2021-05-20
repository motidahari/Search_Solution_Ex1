import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Board {
    private String _AlgoName;
    private boolean _Time,_ToPrint;
    private int _ROW,_COL,_EmptyPlaces;
    private int [][] _Start,_Goal;
    private StateGame Start,Goal;
    private Point[] _EmptyPlace = new Point[0];

    public Board(String path){
        initGame(path);
    }
    private void initGame(String path) {
        int index = 0 , line = 0;
        boolean flagForReadStart = false;
        boolean flagForReadGoal = false;
        try {
            File file = new File(path);
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String data = read.nextLine();
                if(line == 0){
                    this._AlgoName = data;
                }else if(line == 1){
                    this._Time = (data.equals("with time"))? true : false;
                }else if(line == 2){
                    String dataaaaa = data;
                    _ToPrint = (!data.equals("no open"))? true : false;
                }else if(line == 3){
                    initSizeBoar(data);
                    flagForReadStart = true;
                }
                if(data.equals("Goal state:")){
                    index = 0;
                    flagForReadStart = false;
                    flagForReadGoal = true;
                }
                if(flagForReadStart && line > 3){
                    initStartState(line,data,index++);
                }
                if(flagForReadGoal && !data.equals("Goal state:")){
                    initGoalState(line,data,index++);
                }
                line++;
            }
            this.Start = new StateGame(this._Start,null, "", get_EmptyPlace(),0);
            this.Goal = new StateGame(this._Goal, null, "", getEmptyPlace(this._Goal),0);
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private Point[] getEmptyPlace(int[][] goal) {
        int index = 0;
        Point[] _EmptyPlace = new Point[0];
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal[i].length; j++) {
                if(goal[i][j] == -1){
                    _EmptyPlace = Arrays.copyOf(_EmptyPlace, _EmptyPlace.length + 1);
                    _EmptyPlace[index++] = new Point(i,j);
                }
            }
        }
        return _EmptyPlace;
    }

    private void initGoalState(int line, String data, int index) {
        String[] arr = data.split(",");
        for (int i = 0; i < arr.length; i++) {
            this._Goal[index][i] = (arr[i].equals("_")) ? this._Goal[index][i] = -1 : Integer.parseInt(arr[i]);
        }
    }

    private void initStartState(int line, String data, int indexRow) {
        int indexEmpty = 0;
        String[] arr = data.split(",");
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].equals("_")){
                this._Start[indexRow][i] = -1;
                _EmptyPlace = Arrays.copyOf(_EmptyPlace, _EmptyPlace.length + 1);
                _EmptyPlace[indexEmpty++] = new Point(indexRow,i);
                _EmptyPlaces++;
            }else{
                this._Start[indexRow][i] = Integer.parseInt(arr[i]);
            }
        }
    }

    private void initSizeBoar(String data) {
        String[] arr = data.split("x");
        _ROW = Integer.parseInt(arr[0]);
        _COL = Integer.parseInt(arr[1]);
        this._Start = new int[_ROW][_COL];
        this._Goal = new int[_ROW][_COL];
    }

    @Override
    public String toString() {
        String str = "Input{";
        str += "\n\t_AlgoName = " + _AlgoName;
        str += "\n\t_Time = " + _Time;
        str += "\n\t_EmptyPlace = {";
        for (Point p : _EmptyPlace){
            str += "("+ p.x +","+ p.y +")";
        }
        str += "}\n\t_ROW = " + _ROW;
        str += "\n\t_COL = " + _COL;
        str += "\n\n\tStart State = {\n";
        for(int[] x : _Start){
            str += "\t\t" + Arrays.toString(x) + "\n";
        }
        str += "\t}";
        str += "\n\tGoal State = {\n";
        for(int[] x : _Goal){
            str += "\t\t" + Arrays.toString(x) + "\n";
        }
        str += "\t}";
        str += "\n}";
        return  str;
    }

    public String get_AlgoName() {
        return _AlgoName;
    }

    public void set_AlgoName(String _AlgoName) {
        this._AlgoName = _AlgoName;
    }

    public boolean is_Time() {
        return _Time;
    }

    public void set_Time(boolean _Time) {
        this._Time = _Time;
    }

    public boolean is_ToPrint() {
        return _ToPrint;
    }

    public void set_ToPrint(boolean _ToPrint) {
        this._ToPrint = _ToPrint;
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

    public int get_EmptyPlaces() {
        return _EmptyPlaces;
    }

    public void set_EmptyPlaces(int _EmptyPlaces) {
        this._EmptyPlaces = _EmptyPlaces;
    }

    public int[][] get_Start() {
        return _Start;
    }

    public void set_Start(int[][] _Start) {
        this._Start = _Start;
    }

    public int[][] get_Goal() {
        return _Goal;
    }

    public void set_Goal(int[][] _Goal) {
        this._Goal = _Goal;
    }

    public Point[] get_EmptyPlace() {
        return _EmptyPlace;
    }

    public void set_EmptyPlace(Point[] _EmptyPlace) {
        this._EmptyPlace = _EmptyPlace;
    }

    public StateGame getStart() {
        return Start;
    }

    public void setStart(StateGame start) {
        Start = start;
    }

    public StateGame getGoal() {
        return Goal;
    }

    public void setGoal(StateGame goal) {
        Goal = goal;
    }
}
