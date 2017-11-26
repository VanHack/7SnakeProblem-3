import java.util.List;

public class PlayGame implements Runnable {

    private List<List<Integer>> grid;

    public PlayGame(List<List<Integer>> grid) {
        this.grid = grid;
    }

    @Override
    public void run() {
        Snake snakeA = new Snake("A");
        Snake snakeB = new Snake("B");

        snakeA.inicializeSnake(grid, snakeB);
        snakeB.inicializeSnake(grid, snakeA);

        while ( snakeA.getTotal() != snakeB.getTotal() ) {
            if ( snakeA.getTotal() > snakeB.getTotal() ) {
                Object[] next = snakeB.nextStep(snakeA, grid);
                if ( next != null )
                    snakeB.moveSnake((int) next[0], (int) next[1], (int) next[2]);
                else
                    break;
            } else {
                Object[] next = snakeA.nextStep(snakeB, grid);
                if ( next != null )
                    snakeA.moveSnake((int) next[0], (int) next[1], (int) next[2]);
                else
                    break;
            }
        }

        if ( snakeA.getTotal() == snakeB.getTotal() ) {
            Main.finish = true;
            Main.snakes[0] = snakeA;
            Main.snakes[1] = snakeB;
        }

    }

}