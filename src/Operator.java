import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Operator {
    private static Queue<StateGame> q;
    private static int _ROW, _COL;
    private static Point[] _EmptyPlaces;
    private static StateGame _Parent;
    private static int[][] _Start;
    private static String parentMove = "!";

    public static Queue<StateGame> StateOperator(StateGame curr) {
        _ROW = curr.get_ROW();
        _COL = curr.get_COL();
        q = new LinkedList<>();
//        setEmptyPlaces(curr.getEmptyPlaces());
        _EmptyPlaces = setEmptyPlaces(curr);
        _Parent = curr;
        parentMove = _Parent.get_Move();
        _Start = _Parent.get_Mat();
        move();

        return q;
    }

    private static Point[] setEmptyPlaces(StateGame curr) {
        int[][] mat = curr.get_Mat();
        int index = 0;
        _EmptyPlaces = new Point[0];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == -1) {
                    _EmptyPlaces = Arrays.copyOf(_EmptyPlaces, _EmptyPlaces.length + 1);
                    _EmptyPlaces[index++] = new Point(i, j);
                }
            }
        }
        return _EmptyPlaces;
    }

    private static void setEmptyPlaces(Point[] emptyPlaces) {
        int index = 0;
        for (int j = 0; j < emptyPlaces.length; j++) {
            _EmptyPlaces = Arrays.copyOf(_EmptyPlaces, _EmptyPlaces.length + 1);
            _EmptyPlaces[index] = new Point(emptyPlaces[index].x, emptyPlaces[index].y);
            index++;
        }
    }

    public static Point[] setEmptyPlaces(int[][] arr) {
        int index = 0;
        Point[] newEmptyPlace = new Point[0];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == -1) {
                    newEmptyPlace = Arrays.copyOf(newEmptyPlace, newEmptyPlace.length + 1);
                    newEmptyPlace[index++] = new Point(i, j);
                }
            }
        }
        return newEmptyPlace;
    }

    private static int[][] copyArr() {
        int[][] mat = _Start;
        int[][] newArr = new int[_ROW][_COL];
        for (int i = 0; i < _ROW; i++) {
            for (int j = 0; j < _COL; j++) {
                newArr[i][j] = mat[i][j];
            }
        }
        return newArr;
    }

    public static void move() {
        if (_EmptyPlaces.length > 1) {
            checkPossibleSituations2();
        }
        checkPossibleSituations();
    }

    public static void checkPossibleSituations222() {
        int[] from = new int[2];
        int[] to = new int[2];
        for (int i = 0; i < _Start.length; i++) {
            for (int j = 0; j < _Start[i].length; j++) {
                if (_Start[i][j] == -1) {
                    if (j <= _Start[i].length - 1 && j > 0) {//right
                        if (_Start[i][j - 1] != -1 && !(parentMove.length() > 1 && parentMove.contains("L"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i;
                            from[1] = j - 1;
                            moveOneBlock(from, to, copyArr(), "R");
                        }
                    }
                    if (i <= _Start.length - 1 && i > 0) {//down
                        if (_Start[i - 1][j] != -1 && !(parentMove.length() > 1 && parentMove.contains("U"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i - 1;
                            from[1] = j;
                            moveOneBlock(from, to, copyArr(), "D");
                        }
                    }
                    if (j >= 0 && j < _Start[i].length - 1) {//left
                        if (_Start[i][j + 1] != -1 && !(parentMove.length() > 1 && parentMove.contains("R"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i;
                            from[1] = j + 1;
                            moveOneBlock(from, to, copyArr(), "L");
                        }
                    }
                    if (i >= 0 && i < _Start.length - 1) {//up
                        if (_Start[i + 1][j] != -1 && !(parentMove.length() > 1 && parentMove.contains("D"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i + 1;
                            from[1] = j;
                            moveOneBlock(from, to, copyArr(), "U");
                        }
                    }
                }
            }
        }
    }


    public static void checkPossibleSituations() {
        int[] from = new int[2];
        int[] to = new int[2];
        for (int i = 0; i < _Start.length; i++) {
            for (int j = 0; j < _Start[i].length; j++) {
                if (_Start[i][j] == -1) {
                    if (j >= 0 && j < _Start[i].length - 1) {//left
                        if (_Start[i][j + 1] != -1 && !(parentMove.length() > 1 && parentMove.contains("R"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i;
                            from[1] = j + 1;
                            moveOneBlock(from, to, copyArr(), "L");
                        }
                    }
                    if (i >= 0 && i < _Start.length - 1) {//up
                        if (_Start[i + 1][j] != -1 && !(parentMove.length() > 1 && parentMove.contains("D"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i + 1;
                            from[1] = j;
                            moveOneBlock(from, to, copyArr(), "U");
                        }
                    }
                    if (j <= _Start[i].length - 1 && j > 0) {//right
                        if (_Start[i][j - 1] != -1 && !(parentMove.length() > 1 && parentMove.contains("L"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i;
                            from[1] = j - 1;
                            moveOneBlock(from, to, copyArr(), "R");
                        }
                    }
                    if (i <= _Start.length - 1 && i > 0) {//down
                        if (_Start[i - 1][j] != -1 && !(parentMove.length() > 1 && parentMove.contains("U"))) {
                            to[0] = i;
                            to[1] = j;
                            from[0] = i - 1;
                            from[1] = j;
                            moveOneBlock(from, to, copyArr(), "D");
                        }
                    }
                }
            }
        }
    }

    private static void moveOneBlock(int[] from, int[] to, int[][] copyArr, String m) {
        int[][] newArr = copyArr();
        int val = newArr[from[0]][from[1]];
        swap(from[0], from[1], to[0], to[1], newArr);
        String path = "";
        path = val + m;
//        System.out.println(path);
        StateGame node = new StateGame(newArr, _Parent, path, setEmptyPlaces(newArr), 5);
        q.add(node);
    }

    private static void swap(int i, int j, int i2, int j2, int[][] arr) {
        int temp = arr[i][j];
        arr[i][j] = arr[i2][j2];
        arr[i2][j2] = temp;
    }

    public static void checkPossibleSituations2() {
        if (_EmptyPlaces.length > 1) {
            int[] from = {_EmptyPlaces[0].x, _EmptyPlaces[0].y, _EmptyPlaces[1].x, _EmptyPlaces[1].y};
            if (checkForEmptyHorizontal(from[0], from[1], from[2], from[3], _COL)) {
                if (from[0] >= 0 && from[2] >= 0 && from[0] != _ROW - 1 && from[2] != _ROW - 1) {
                    int[] to = {from[0] + 1, from[1], from[2] + 1, from[3]};
                    moveTwoBlocks(from, to, copyArr(), "U");//U
                }
                if (from[0] < _ROW && from[2] < _ROW && from[0] != 0 && from[2] != 0) {
                    int[] to = {from[0] - 1, from[1], from[2] - 1, from[3]};
                    moveTwoBlocks(from, to, copyArr(), "D");//D
                }
            } else if (checkForEmptyVertical(from[0], from[1], from[2], from[3], _ROW)) {

                if (from[1] >= 0 && from[3] >= 0 && from[1] != _COL - 1 && from[3] != _COL - 1) {
                    int[] to = {from[0], from[1] + 1, from[2], from[3] + 1};
                    moveTwoBlocks(from, to, copyArr(), "L");//L
                }
                if (from[1] != 0 && from[3] != 0 && from[1] < _COL && from[3] < _COL) {
                    int[] to = {from[0], from[1] - 1, from[2], from[3] - 1};
                    moveTwoBlocks(from, to, copyArr(), "R");//R
                }
            }
        }
    }

    private static void moveTwoBlocks(int[] from, int[] to, int[][] copyArr, String m) {
        int[][] newArr = copyArr();
        swap(from[0], from[1], to[0], to[1], newArr);
        swap(from[2], from[3], to[2], to[3], newArr);
        String path = "";
        int price = 0;
        if (m.equals("L")) {
            price = 6;
            path = newArr[from[2]][from[3]] + "&" + newArr[from[0]][from[1]] + m;
        } else if (m.equals("R")) {
            path = newArr[from[0]][from[1]] + "&" + newArr[from[2]][from[3]] + m;
            price = 6;
        } else if (m.equals("D")) {
            path = newArr[from[2]][from[3]] + "&" + newArr[from[0]][from[1]] + m;
            price = 7;
        } else if (m.equals("U")) {
            path = newArr[from[0]][from[1]] + "&" + newArr[from[2]][from[3]] + m;
            price = 7;
        }
        StateGame node = new StateGame(newArr, _Parent, path, setEmptyPlaces(newArr), price);
//        if (_Parent != null && _Parent.get_Parent().get_Parent() != null && node.equals(_Parent.get_Parent().get_Parent())) return;
        q.add(node);
    }

    public static boolean checkForEmptyHorizontal(int i, int j, int i2, int j2, int _COL) {
        return (i == i2) && (((j - 1) >= 0 && j - 1 == j2) || ((j2 - 1) >= 0 && j == j2 - 1)) && (j < _COL && j2 < _COL);
    }

    public static boolean checkForEmptyVertical(int i, int j, int i2, int j2, int _ROW) {
        return (j == j2) && (((i2 - 1) >= 0 && i2 - 1 == i) || ((i - 1) >= 0 && i - 1 == i2)) && (i < _ROW && i2 < _ROW);
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
        String str = "Possible_situations{\n";
        int run = 1;
        while (!q.isEmpty()) {
            StateGame s = q.poll();
            str += "\nSituation " + (run++) + "{";
            for (int i = 0; i < s.get_Mat().length; i++) {
                str += "\n\t" + Arrays.toString(s.get_Mat()[i]);
            }
            str += "\n}";
        }
        str += "\n}";
        return str;
    }
}
