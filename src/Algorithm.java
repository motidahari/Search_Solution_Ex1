//import State.*;
//public interface Algorithm {
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
public abstract class Algorithm {
    protected boolean _Time, _Print;
    long startTime = System.currentTimeMillis();;
    long endTime = 0;

    public abstract void solve();

    public void finish(boolean foundSolution,boolean printTime, String path, int numOfCreated, int price, double totalTime) {
        Printer.Output(foundSolution,printTime,_Print,path,numOfCreated,price,totalTime);
    }
    protected void printList(HashMap<StateGame,StateGame> list) {
        System.out.println("list = {");
        for (StateGame x: list.values()){
            System.out.print("\tx.get_Move() = " + x.get_Move());
            System.out.print(" | x.get_KeyState() = " + x.get_KeyState() + "\n");
        }
        System.out.println("}\n");
    }
    protected void printClosedList(HashSet<StateGame> list) {
        System.out.println("list = {");
        for (StateGame x: list){
            System.out.print("\tx.get_Move() = " + x.get_Move());
            System.out.print(" | x.get_KeyState() = " + x.get_KeyState() + "\n");
        }
        System.out.println("}\n");
    }
}
