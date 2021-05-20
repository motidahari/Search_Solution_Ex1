import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;

public class IDA_Star extends Algorithm{
    private Board in;
    private Stack<StateGame> _Stack;
    private StateGame _Start,_Goal;
    private HashMap<StateGame,StateGame> _OpenList;
    private int _numOfCreated = 1;
    private boolean run = true;
    private String _Path;
    private int _Price = 0;

    public IDA_Star(Board in, StateGame[] startAndGoal) {
        this.in = in;
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this._OpenList = new HashMap<>();
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
        this._numOfCreated = 0;
        solve();
    }

    @Override
    public void solve() {
        Stack<StateGame> stack = new Stack<StateGame>();
        _Start.setDistance(_Goal);
        double t = _Start.get_DistanceHeuristic();

        while (t < Integer.MAX_VALUE) {

            double minF = Integer.MAX_VALUE;
            stack.push(_Start);
            _OpenList.put(_Start, _Start);

            while (!stack.empty()) {
                if (!run) break;
                StateGame current = stack.pop();

                if (current.get_Mark().equals("out")) {

                    _OpenList.remove(current.get_KeyState());

                } else {
                    current.set_Mark("out");
                    stack.add(current);
                    Queue<StateGame> options = Operator.StateOperator(current);
                    _numOfCreated += options.size();
                    while (!options.isEmpty()) {
                        StateGame possible = options.poll();
                        possible.setDistance(_Goal);
                        int f = (int) possible.get_DistanceHeuristic() + possible.get_Cost();
                        if (f > t) {
                            minF = Math.min(f, minF);
                            continue;
                        }
                        if (_OpenList.containsKey(possible.get_KeyState()) && possible.get_Mark().equals("out")) {
                            continue;
                        }
                        if (_OpenList.containsKey(possible.get_KeyState()) && !possible.get_Mark().equals("out")) {
                            double left = _OpenList.get(possible.get_KeyState()).get_Cost() ;
                            double right = possible.get_Cost();
                            if (left > right) {
                                _OpenList.remove(possible.get_KeyState());
                                stack.remove(possible);
                            } else {
                                continue;
                            }
                        }
                        if ((possible.get_KeyState()).equals(_Goal.get_KeyState())) {
                            getPathToGoal(possible);
                            t = Integer.MAX_VALUE;
                            run = false;
                            break;
                        }
                        _OpenList.put(possible, possible);
                        stack.add(possible);
                    }
                }
            }
            t = minF;
            _Start.set_Mark("AGAIN");
        }

        if(run && _Path.length() > 0 && _numOfCreated > 0 && _Price > 0){
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
