import java.util.HashSet;
import java.util.Queue;

public class DFID extends Algorithm{

    private StateGame _Start,_Goal;
    private static HashSet<StateGame> _OpenList;
    private int _Created;
    private Board in;
    private int _numOfCreated = 0;
    private boolean run = true;
    private String _Path;
    private int price = 0;
    private double _time = 0;
    boolean print,time;

    public DFID(Board in, StateGame[] startAndGoal) {
        this.in = in;
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this._OpenList = new HashSet<>();
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
        this._numOfCreated = 0;
        solve();
    }

    @Override
    public void solve() {
        _Created = 1;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (!((_Path = DFS(_Start, _Goal, i))).equals("false")) {
                return;
            }
        }
    }
    private String DFS(StateGame start, StateGame goal, int limit) {
//        PrintToScreen(output.is_Print(), possible);
        if ((start.get_KeyState()).equals(goal.get_KeyState())) {
            getPathToGoal(start);
            return _Path;
        } else if (limit == 0) {
            return "false";
        }else {
            _OpenList.add(start);
            boolean isCutoff = false;
            Queue<StateGame> options = Operator.StateOperator(start);
            _Created += options.size();
            while (!options.isEmpty())  {
                StateGame p = options.poll();
                if (!_OpenList.contains(p)) {
                    String result = DFS(p, goal, limit - 1);
                    if (result.equals("cutOff")) {
                        isCutoff = true;
                    } else if (!result.equals("fail")) {
                        return result;
                    }
                }
            }
            _OpenList.remove(start);
            if (isCutoff) {
                return "false";
            }else {
                return "fail";
            }
        }

    }
    private String fixPath(String path) {
        return path.substring(1,path.length()-1);
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
