import java.util.*;

public class DFBnB extends Algorithm{
    private Board in;
    private HashMap<StateGame,StateGame> _OpenList;
    private StateGame _Start,_Goal;
    private int _numOfCreated = 1;
    private boolean run = true;
    private String _Path;
    private int price = 0;
    private static ArrayList<StateGame> _List;


    public DFBnB(Board in, StateGame[] startAndGoal) {
        this.in = in;
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this._OpenList = new HashMap<>();
        _List = new ArrayList<>();
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
        this._numOfCreated = 0;
        solve();
    }

    @Override
    public void solve() {
        _List = new ArrayList<>();
        Stack<StateGame> stack = new Stack<>();
        _OpenList.put(_Start, _Start);
        stack.add(_Start);
        _Start.setDistance(_Goal);
        double t =  _Start.get_DistanceHeuristic() * 7.5;

        while (!stack.empty()) {
            if (!run) break;
            StateGame n = stack.pop();
            if (n.get_Mark().equals("out")) {
                _OpenList.remove(n);

            } else {
                n.set_Mark("out");
                Queue<StateGame> queue = Operator.StateOperator(n);
                ArrayList<StateGame> options = new ArrayList<>();
                options.addAll(queue);
                for (StateGame set:options) {
                    set.setDistance(_Goal);
                }
//                options.sort(new StateGameComparator()::compare);
                ArrayList<StateGame> copy = new ArrayList<>(options);
                _numOfCreated += options.size();

                for (StateGame possible : copy) {
                    possible.setDistance(_Goal);
                    int fg = (int) possible.get_DistanceHeuristic() + possible.get_Cost();
                    if (fg >= t) {
                        for (StateGame toRm: copy ) {
                            int tempV =(int)toRm.get_DistanceHeuristic() +toRm.get_Cost();
                            if (tempV >= t){
                                options.remove(toRm);
                            }
                        }
                    } else if (_OpenList.containsKey(possible) && possible.get_Mark().equals("out")) {
                        options.remove(possible);
                    } else if (_OpenList.containsKey(possible) && !possible.get_Mark().equals("out")) {
                        double left = _OpenList.get(possible).get_Cost();
                        double right = possible.get_Cost();

                        if (left <= right) {
                            options.remove(possible);
                        } else {
                            _OpenList.remove(possible);
                            stack.remove(possible);
                        }
                    } else if ((possible.get_KeyState()).equals(_Goal.get_KeyState())) {
                        getPathToGoal(possible);
                        t = possible.get_Cost();
                        for (StateGame path : stack) {
                            if (path.get_Mark().equals("out"))
                                _List.add(path);
                        }
                        options.removeIf(StateGame -> options.indexOf(possible) < options.indexOf(possible));
                        options.remove(possible);
                        run = false;

                    }
                    Collections.reverse(options);
                    stack.addAll(options);
                    for (StateGame toPut: options) {
                        _OpenList.put(toPut, toPut);
                    }
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
        this.price = price;
    }
}
