import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class A_Star extends Algorithm{
    private Board in;
    private HashMap<StateGame,StateGame> _OpenList;
    private HashMap<StateGame,StateGame> _ClosedList;
    private StateGame _Start,_Goal;
    private int _numOfCreated = 0;
    private boolean run = true;
    private String _Path;
    private int price = 0;


    public A_Star(Board in, StateGame[] startAndGoal){
        this.in = in;
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this._OpenList = new HashMap<>();
        this._ClosedList = new HashMap<>();
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
        this._numOfCreated = 0;
        solve();
    }
    @Override
    public void solve() {
//        PriorityQueue<StateGame> queue = new PriorityQueue<StateGame>(new StateComparator());
        PriorityQueue<StateGame> queue = new PriorityQueue<StateGame>();
        queue.add(_Start);
        _OpenList.put(_Start,_Start);
        _numOfCreated = 0;
        while (!queue.isEmpty()) {
            StateGame current = queue.poll();
            if ((_Start.get_KeyState()).equals(_Goal.get_KeyState())) {
                getPathToGoal(current);
                run = false;
                break;
            }
            _ClosedList.put(current,current);
            Queue<StateGame> options = Operator.StateOperator(current);
            _numOfCreated += options.size();
            while (!options.isEmpty()) {
                StateGame possible = options.poll();
                if (!(_ClosedList.containsKey(possible)) &&
                        !(_OpenList.containsKey(possible))) {
                    possible.setDistance(_Goal);
                    queue.add(possible);
                    _OpenList.put(possible,possible);
                }else if (queue.contains(possible) && _OpenList.containsKey(possible) && _OpenList.get(possible).get_Cost() > possible.get_Cost()) {
                    queue.remove(possible);
                    queue.add(possible);
                    _OpenList.put(possible, possible);
                }
            }
        }
        if (run && _Path.length() > 0 && _numOfCreated > 0 && price > 0) {
            _Path = _Path.substring(1,_Path.length()-1);
            endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            finish(true,_Time,_Path,_numOfCreated,price,time);
        }

    }

    private void getPathToGoal(StateGame node) {
        int price = node.get_Cost();
        String strMoves = "";
        for (StateGame i = node ; i != null ; i = i.get_Parent()) {
            strMoves = i.get_Move() + "-" + strMoves;
        }
        this._Path = strMoves;
        this._numOfCreated = _numOfCreated;
        this.price = price;
    }
}
