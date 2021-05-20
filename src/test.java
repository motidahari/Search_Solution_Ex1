import java.awt.*;
import java.util.Queue;

public class test {
    static int[][] arr = {
            {1,2,3,4},
            {5,6,11,7},
            { 9,10,-1,-1}
    };
    static int[][] arr2 = {
            { 9,10,-1,-1},
            {1,2,3,4},
            {5,6,11,7}
    };
    static int[][] arr3 = {
            {1,2,3,4},
            {5,6,11,-1},
            {9,10,9,-1}
    };
    static int[][] arr4 = {
            { 9,10,9,9},
            {-1,2,3,4},
            {-1,6,11,7}
    };

    public static void printList(StateGame curr){
        System.out.println("curr node = " + curr);
        Queue<StateGame> q = Operator.StateOperator(curr);
        while (!q.isEmpty()){
            System.out.println(q.poll());
        }
    }
    public static void main(String[] args) {
        StateGame curr;
        curr = new StateGame(arr, null, "", new Point[]{new Point(2,2), new Point(2,3)},0);
        printList(curr);


        curr = new StateGame(arr2, null, "", new Point[]{new Point(0,2), new Point(0,3)},0);
        printList(curr);



        curr = new StateGame(arr3, null, "", new Point[]{new Point(1,3), new Point(2,3)},0);
        printList(curr);


        curr = new StateGame(arr4, null, "", new Point[]{new Point(1,0), new Point(2,0)},0);
        printList(curr);

    }

}
