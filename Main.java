import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.File;

public class Main {

    public static boolean finish = false;
    public static Snake[] snakes = new Snake[2];

    public static void main(String args[]) throws InterruptedException {

        String pathFile;
        if ( args == null || args.length == 0 ) {
            System.err.println("File path not informed.");
            System.exit(0);
        }
        pathFile = args[0];

        File f = new File(pathFile);
        if( !f.exists() ) {
            System.err.println("File not exists.");
            System.exit(0);
        }

        Grid grid = new Grid();
        List<List<Integer>> gridSnakes = grid.read(pathFile);

        int loop = 2500, bath = 750, interation = 0;
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            while ( !finish && interation < loop ) {
                for (int i = 0; i < bath; i++) {
                    Runnable worker = new PlayGame(gridSnakes);
                    executor.execute(worker);
                }
                interation++;
            }
        } finally {
            executor.shutdown();
            while (!executor.isTerminated()) {}
        }

        if ( finish ) {
            snakes[0].printSteps();
            snakes[1].printSteps();
        } else {
            System.err.println("FAIL");
        }

    }
}