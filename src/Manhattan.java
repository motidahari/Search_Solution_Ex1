/**
 * This Class represent a heuristic function.
 */
public class Manhattan {
    private StateGame curr;

    public Manhattan(StateGame eState){
        this.curr = eState;
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
        int cost = 5;
        int i,j,i2,j2;
        if(curr.get_EmptyPlaces().length>1){
            i = curr.get_EmptyPlaces()[0].x;
            j = curr.get_EmptyPlaces()[0].y;
            i2 = curr.get_EmptyPlaces()[1].x;
            j2 = curr.get_EmptyPlaces()[1].y;
            if(Operator.checkForEmptyVertical(i,j,i2,j2,curr.get_ROW())){
                cost = 7;
            }else if(Operator.checkForEmptyHorizontal(i,j,i2,j2,curr.get_COL())){
                cost = 6;
            }
        }
        return h*cost;
    }

}
