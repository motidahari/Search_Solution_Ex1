import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends Algorithm{
    private Board in;
    private HashSet<StateGame> _OpenList;
    private HashSet<StateGame> _ClosedList;
    private StateGame _Start,_Goal;
    private int _numOfExpanded = 1;
    private boolean run = true;
    private String _Path;
    private int _Price = 0;
    private double _time = 0;

    public BFS(Board in, StateGame[] startAndGoal){
        this.in = in;
        this._Time = in.is_Time();
        this._Print = in.is_ToPrint();
        this._OpenList = new HashSet<>();
        this._ClosedList = new HashSet<>();
        this._Start = startAndGoal[0];
        this._Goal = startAndGoal[1];
    }

    @Override
    public void solve() {
        String str = "";
        String strKeys = "";
        Queue<StateGame> queue = new LinkedList<>();
        queue.add(_Start);
        _OpenList.add(_Start);
        while (run && !queue.isEmpty()){
            StateGame curr = queue.poll();
//            if(_Print){
//                System.out.println(curr.getStringMat(curr.get_Mat()));
//            }
            _ClosedList.add(curr);
            if (in.is_ToPrint()) {
                System.out.println(curr.getStringMat(curr.get_Mat()));
            }
            _OpenList.remove(curr);
            if(in.is_ToPrint()){
                str += curr.get_Move() + " | ";
                strKeys += curr.get_KeyState() + " | ";
            }
            Queue<StateGame> PossibleSituations = Operator.StateOperator(curr);
            for (StateGame node:PossibleSituations) {
                if(!(_ClosedList.contains(node)) && !(_OpenList.contains(node))){
                    _numOfExpanded++;
                    if ((node.get_KeyState()).equals(_Goal.get_KeyState())) {
                        getPathToGoal(node);
                        run = false;
                        break;
                    }
                    queue.add(node);
                    _OpenList.add(node);
                }
            }
        }
        if(_Path.length() > 0 && _numOfExpanded > 0 && _Price > 0){
            endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            finish(true,_Time,_Path, _numOfExpanded,_Price,time);
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
