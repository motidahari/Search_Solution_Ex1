import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Queue;

public class DFID extends Algorithm {
    private Board in;
    private StateGame _Start, _Goal;
    private HashSet<StateGame> _OpenList;
    private int _numOfExpanded = 1;
    private boolean run = true;
    private int price = 0;
    private String _Path;


    public DFID(Board in, StateGame[] startAndGoal) {
        this.in = in;
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
//        this._OpenList = new HashSet<>();
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
    }

    @Override
    public void solve() {
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            this._OpenList = new HashSet<>();
            String path = DFS(_Start, _Goal, i, _OpenList);
            if (!path.equals("cutOff")) {
                System.out.println("output = " + path);
            if(path.length() > 0 && _numOfExpanded > 0 && price > 0){
                endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                int maxCost = _OpenList.stream().mapToInt(StateGame::get_Cost).max().orElseThrow(NoSuchElementException::new);
                finish(true,_Time,_Path, _numOfExpanded,price,time);
            }
                return;
            }
        }
    }

    private String DFS(StateGame curr, StateGame goal, int limit, HashSet<StateGame> openList) {
        if ((curr.get_KeyState()).equals(goal.get_KeyState())) {
            getPathToGoal(curr);
            return "Found";
        } else if (limit == 0) {
            return "cutOff";
        } else {
            openList.add(curr);
            boolean isCutoff = false;
            Queue<StateGame> options = Operator.StateOperator(curr);
            for (StateGame p : options) {
                if (openList.contains(p))
                    continue;
                _numOfExpanded++;
                String result = DFS(p, goal, limit - 1, openList);
                if (result.equals("cutOff"))
                    isCutoff = true;
                else if (!result.equals("fail")) {
                    return result;
                }
            }
            openList.remove(curr);
            if (isCutoff) {
                return "cutOff";
            } else {
                return "fail";
            }
        }
    }


    private String fixPath(String path) {
        return path.substring(1, path.length() - 1);
    }

    private void getPathToGoal(StateGame node) {
        int price = node.get_Cost();
        String strMoves = "";
        for (StateGame i = node; i != null; i = i.get_Parent()) {
            strMoves = i.get_Move() + "-" + strMoves;
        }
        this._Path = strMoves;
        this.price = price;
    }
}
