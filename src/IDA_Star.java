import java.util.*;

public class IDA_Star extends Algorithm{
    private Board in;
    private StateGame _Start,_Goal;
    private int _numOfCreated = 1;
    private boolean run = true,foundSolution = false;
    private String _Path;
    private int _Price = 0;
    private Manhattan _heuristic;
    private Stack<StateGame> L;
    private Hashtable<StateGame, StateGame> H;
    private int minF;
    private int t;
    private static HashMap<StateGame, StateGame> _Frontier ;

    public IDA_Star(Board in, StateGame[] startAndGoal, Manhattan manhattan) {
        this.in = in;
        this._heuristic = manhattan;
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this.L = new Stack<>();
        this.H = new Hashtable<>();
        _Frontier = new HashMap<>();
    }

//    @Override
//    public void solve() {
//        double startTime, endTime, totalTime; //for time keeping
//        startTime = System.currentTimeMillis();
//        while (t != Integer.MAX_VALUE) {
//            _Start.isOut(false);
//            minF = Integer.MAX_VALUE;
//            L.add(_Start);
//            H.put(_Start, _Start);
//            while (!L.isEmpty()) {
//                StateGame n = L.pop();
//                if (in.is_ToPrint()) {
//                    System.out.println(n.getStringMat(n.get_Mat()));
//                }
//                if (n.isOut()) {
//                    H.remove(n);
//                }else {
//                    n.setOut(true);
//                    L.add(n);
//                    for (Direction direction:Direction.values()) {
//                        StateGame child = new StateGame(n,direction);
//                        if (child.isMoved()) {
//                            nodesExpanded++;
//                            int fOnChild = heuristic.getF(child);
//                            if (fOnChild > t) {
//                                minF = Math.min(minF, fOnChild);
//                                continue;
//                            }
//                            if (H.contains(child) && H.get(child).isOut()) {
//                                continue;
//                            }
//                            if (H.contains(child) && !H.get(child).isOut()) {
//                                if (heuristic.getF(H.get(child)) > heuristic.getF(child)) {
//                                    H.remove(child);
//                                    L.remove(child);
//                                } else {
//                                    continue;
//                                }
//                            }
//                            if (child.equals(_Goal)) {
//                                endTime = System.currentTimeMillis();
//                                totalTime = (endTime - startTime) * 1.0 / 1000;
//                                foundSolution = true;
//                                finish(child, totalTime);
//                                return;
//                            }
//                            L.add(child);
//                            H.put(child, child);
//                        }
//                    }
//                }
//            }
//            t = minF;
//
//        }
//        endTime = System.currentTimeMillis();
//        totalTime = (endTime - startTime) * 1.0 / 1000;
//        finish(null, totalTime);
//
//    }



    @Override
    public void solve() {


        Stack<StateGame> stack = new Stack<StateGame>();
        _Start.setDistance(_Goal);
        double t = _Start.get_DistanceHeuristic();

        while (t < Integer.MAX_VALUE) {

            double minF = Integer.MAX_VALUE;
            stack.push(_Start);
            _Frontier.put(_Start, _Start);

            while (!stack.empty()) {
                if (!run) break;
                StateGame current = stack.pop();
                if (in.is_ToPrint()) {
                    System.out.println(current.getStringMat(current.get_Mat()));
                }
                if (current.get_Mark().equals("out")) {
                    _Frontier.remove(current);

                } else {

                    current.set_Mark("out");
                    stack.add(current);
//                    HashSet<String> dummy = new HashSet<>();
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

                        if (_Frontier.containsKey(possible) && possible.get_Mark().equals("out")) {
                            continue;
                        }

                        if (_Frontier.containsKey(possible) && !possible.get_Mark().equals("out")) {

                            double left = _Frontier.get(possible).get_Cost() ;
                            double right = possible.get_Cost();

                            if (left > right) {
                                _Frontier.remove(possible);
                                stack.remove(possible);
                            } else continue;
                        }
                        if (possible.get_KeyState().equals(_Goal.get_KeyState())) {
                            getPathToGoal(possible);
                            t = Integer.MAX_VALUE;
                            run = false;
                            break;
                        }
                        _Frontier.put(possible, possible);
                        stack.add(possible);
                    }
                }
            }
            t = minF;
            _Start.set_Mark("AGAIN");
        }
        if(_Path.length() > 0 && _numOfCreated > 0 && _Price > 0){
            endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            finish(true,_Time,_Path, _numOfCreated,_Price,time);
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
