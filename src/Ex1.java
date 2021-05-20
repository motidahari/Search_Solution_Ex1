public class Ex1 {

    final static Config c = new Config();
    static Board in;
    static StateGame _StartState;
    static StateGame _GoalState;
    static StateGame[] startAndGoal = new StateGame[2];


    public static void main(String[] args) {
        in = new Board(c.inputOutput);
        startAndGoal[0] = in.getStart();
        startAndGoal[1] = in.getGoal();
        AlgorithmSelection();
    }


    public static void AlgorithmSelection() {
        switch (in.get_AlgoName()) {
            case "A*":
//                System.out.println("A*");
                Algorithm A_Star = new A_Star(in, startAndGoal);
                A_Star.solve();

                break;
            case "DFBnB":
//                System.out.println("DFBnB");
                Algorithm DFBnB = new DFBnB(in, startAndGoal,new Manhattan(in.getGoal()));
                DFBnB.solve();

                break;
            case "BFS":
//                System.out.println("BFS");
                Algorithm BFS = new BFS(in, startAndGoal);
                BFS.solve();

                break;
            case "DFID":
//                System.out.println("DFID");
                Algorithm DFID = new DFID(in, startAndGoal);
                DFID.solve();

                break;
            case "IDA*":
//                System.out.println("IDA*");
                Algorithm IDA_Star = new IDA_Star(in, startAndGoal,new Manhattan(in.getGoal()));
                IDA_Star.solve();

                break;
        }
    }
}
