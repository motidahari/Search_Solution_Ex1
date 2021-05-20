import java.util.*;

public class DFBnB extends Algorithm{
    private Board in;
    private Stack<StateGame> L;
    private Hashtable<StateGame, StateGame> H;
    private int threshold = 0;
    private Manhattan _heuristic;
    private HeuristicFunction heuristic;
//    private Heuristic<StateGame> heuristic;
    private StateGame _Start,_Goal;
    private int _numOfCreated = 1;
    private boolean run = true,foundSolution = false;
    private String _Path;
    private int _Price = 0;
    private static ArrayList<StateGame> _List;

    public DFBnB(Board in, StateGame[] startAndGoal, Manhattan manhattan) {
        this._heuristic = manhattan;
        this.in = in;
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this.heuristic = new HeuristicFunction(_Start,_Goal);
    }

    @Override
    public void solve() {
//        double startTime, endTime, totalTime; //for time keeping
//        startTime = System.currentTimeMillis();

        StateGame resultState = null;
        StateGame start = _Start;
        StateGame goal = _Goal;
        int threshold = Integer.MAX_VALUE;
        int totalBlocks = _Start.get_ROW() * _Start.get_COL();
//        int numOfBlack = p.getNumOfBlack();
        int totalToLIMIT = totalBlocks;
//        if (totalToLIMIT <= 12) {
//            threshold = factorial(totalToLIMIT);
//        }
        ArrayList<StateGame> result = new ArrayList<>();
        L = new Stack<>();
        H = new Hashtable<>();
        L.add(start);
        H.put(start, start);
        while (!L.isEmpty()) {
            StateGame n = L.pop();
            if (in.is_ToPrint()) {
                System.out.println(n.getStringMat(n.get_Mat()));
            }
            if (n.isOut()) {
                H.remove(n);
            } else {
                n.setOut(true);
                L.add(n);
                List l = (List) Operator.StateOperator(n);

                Queue<StateGame> list = Operator.StateOperator(n);
                ArrayList<StateGame> allChilds = new ArrayList<>();
                for(StateGame s: list){
                    allChilds.add(s);
                }
                allChilds.sort(heuristic::compare);
                ArrayList<StateGame> copyAllChilds = new ArrayList<>(allChilds);
                _numOfCreated ++;
                for (StateGame child : copyAllChilds) {
                    if (heuristic.getFCost(child) >= threshold) {
                        //delete everyone after him. include him
                        for (StateGame children : copyAllChilds) {
                            if (heuristic.getFCost(children) >= threshold) {
                                allChilds.remove(children);
                            }
                        }
                    } else if (H.contains(child) && H.get(child).isOut()) {
                        allChilds.remove(child);
                    } else if (H.contains(child) && !H.get(child).isOut()) {
                        if (heuristic.getFCost(H.get(child)) <= heuristic.getFCost(child)) {
                            allChilds.remove(child);
                        } else {
                            L.remove(H.get(child));
                            H.remove(child);
                        }
                    } else if (child.equals(goal)) {
                        foundSolution = true;
                        getPathToGoal(child);
                        threshold = heuristic.getFCost(child);
                        result.clear();
                        resultState = child;
                        for (StateGame state : L) {
                            if (state.isOut()) {
                                result.add(state);
                            }
                        }
                        allChilds.removeIf(boardState -> allChilds.indexOf(child) < allChilds.indexOf(boardState));
                        allChilds.remove(child);
                    }
                    Collections.reverse(allChilds);
                    L.addAll(allChilds);
                    for (StateGame boardState : allChilds) {
                        H.put(boardState, boardState);
                    }
                }
            }
        }


        if (foundSolution && _Path.length() > 0 && _numOfCreated > 0 && _Price > 0) {
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
