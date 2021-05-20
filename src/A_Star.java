import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class A_Star extends Algorithm{
    private Board in;
    private HashMap<StateGame,StateGame> C;
    private HashMap<StateGame,StateGame> H;
    private StateGame _Start,_Goal;
    private int _numOfCreated = 0;
    private HeuristicFunction heuristic;
    private boolean run = true;
    private String _Path;
    private int _Price = 0;
    private PriorityQueue<StateGame> L;

    public A_Star(Board in, StateGame[] startAndGoal){
        this.in = in;
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this.C = new HashMap<>();
        this.H = new HashMap<>();
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
        this.heuristic = new HeuristicFunction(_Start,_Goal);
        this.L =  new PriorityQueue<StateGame>(heuristic::compare);
    }
    @Override
    public void solve() {

        L.add(_Start);
        while (!L.isEmpty()) {
//            System.out.println("while");
            StateGame current = L.poll();
            if (in.is_ToPrint()) {
                System.out.println(current.getStringMat(current.get_Mat()));
            }
            H.remove(current);
            if (current.equals(_Goal)) {
                getPathToGoal(current);
                run = false;
                break;
            }
            C.put(current,current);
            Queue<StateGame> options = Operator.StateOperator(current);
            _numOfCreated++;

            for(StateGame option:options){
                if(!C.containsKey(option) && !L.contains(option)){
                    L.add(option);
                    H.put(option,option);
                }else if (L.contains(option) && H.get(option) != null && H.get(option).get_Cost() > option.get_Cost()) {
                    L.remove(option);
                    L.add(option);
                    H.put(option, option);
                }

            }
        }
        System.out.println("finish");
        if (!run && _Path.length() > 0 && _numOfCreated > 0 && _Price > 0) {
            _Path = _Path.substring(1,_Path.length()-1);
            endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            finish(true,_Time,_Path,_numOfCreated,_Price,time);
        }

    }

    private void getPathToGoal(StateGame node) {
        int price = node.get_Cost();
        String strMoves = "";
        for (StateGame i = node ; i != null ; i = i.get_Parent()) {
            strMoves = i.get_Move() + "-" + strMoves;
        }
        this._Path = strMoves;
        this._Price = price;
    }
}
