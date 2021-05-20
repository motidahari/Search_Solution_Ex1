import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Printer {
    static Config config = new Config();
    private final static String OUTPUT_FILE_NAME = config.outputOutput;
    private static boolean _Time, _Print;

    public Printer(boolean time, boolean print){
        this._Print = print;
        this._Time = time;
    }


    public static void Output(boolean foundSolution, boolean time, boolean print, String path, int numOfCreated, int price, double totalTime) {
            writingToFile(foundSolution, time, print, path, numOfCreated, price, totalTime);
    }

    private static void writingToFile(boolean foundSolution, boolean time, boolean print, String path, int numOfCreated, int price, double totalTime) {
            try {
                FileWriter write = new FileWriter(OUTPUT_FILE_NAME);
                PrintWriter print_line = new PrintWriter(write, true);
                if (foundSolution) {
                    print_line.println(path);
                    print_line.println("Num: " + numOfCreated);
                    print_line.println("Cost: " + price);
                    if (time) {
                        print_line.println(totalTime/1000 + " seconds");
                    }
                }else {
                    print_line.println("no found Solution");
                }
                print_line.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
