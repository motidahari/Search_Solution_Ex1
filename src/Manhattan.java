/**
 * This Class represent a heuristic function.
 */
public class Manhattan {
    StateGame eState;
    public Manhattan(StateGame eState){
        this.eState=eState;
    }
    public int getH(StateGame boardState){
        int h = 0;
        int rows = boardState.get_ROW();
        int columns = boardState.get_COL();
        for(int i=0;i<rows;++i){
            for(int j=0;j<columns;++j){
                int value = boardState.get_Mat()[i][j];
                if(value==0) {
                    continue;
                }
                int cRow = value/columns;
                int cColumn = value%columns;
                if(cColumn==0){
                    cColumn = columns-1;
                    cRow--;
                }else{
                    cColumn--;
                }
                h += (Math.abs(i-cRow)+Math.abs(j-cColumn));
            }
        }
        return h*5;
    }

}
