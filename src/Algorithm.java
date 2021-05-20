//import State.*;
//public interface Algorithm {
import java.util.concurrent.TimeUnit;
public abstract class Algorithm {
    protected boolean _Time, _Print;
    long startTime = System.currentTimeMillis();;
    long endTime = 0;

    public abstract void solve();

    public void finish(boolean foundSolution,boolean printTime, String path, int numOfCreated, int price, double totalTime) {
        Printer.Output(foundSolution,printTime,_Print,path,numOfCreated,price,totalTime);
    }
}
