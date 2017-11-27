import java.util.*;

public class Snake {

    private String name;
    private List<String> cells;
    private int total;

    public Snake(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public List<String> getCells() {
        return cells;
    }

    public void printSteps(){
        System.out.println("\nSnake" + name);
        for ( int i =0; i < cells.size(); i++ ) {
            String[] moviment = cells.get(i).split(",");
            System.out.println( "\t Step " + (i+1) + " -> X:" + (Integer.parseInt(moviment[0])+1) + " - Y:" + (Integer.parseInt(moviment[1])+1) );
        }
        System.out.println("Sum -> " + total);
    }

    public void moveSnake(int positionX, int positionY, int value) {
        if ( getCells() == null )
            cells = new ArrayList<>();
        cells.add(createAlias(positionX, positionY));
        total += value;
    }

    private String createAlias(int positionX, int positionY){
        String alias = positionX + "," + positionY;
        return alias;
    }

    private List<Integer> lastMove(){
        String[] lastStep = cells.get(cells.size()-1).split(",");
        return new ArrayList(Arrays.asList(Integer.parseInt(lastStep[0]), Integer.parseInt(lastStep[1])));
    }

    public void inicializeSnake(List<List<Integer>> grid, Snake snake ) {
        Random random = new Random();
        int positionX, positionY, value;
        do {
            positionX = random.nextInt( grid.size() );
            positionY = random.nextInt( grid.size() );
            value = grid.get(positionX).get(positionY);
        } while ( accept(positionX, positionY, snake) );

        moveSnake(positionX, positionY, value);
    }

    public boolean accept(int positionX, int positionY, Snake snake ) {
        if ( snake.getCells() == null )
            return false;
        else {
            boolean contains = snake.getCells().contains(createAlias(positionX, positionY));
            return contains;
        }
    }

    public boolean acceptBoth(int positionX, int positionY, Snake snake ) {
        String alias = createAlias(positionX, positionY);
        return !this.getCells().contains(alias) && !snake.getCells().contains(alias);
    }

    public Object[] nextStep(Snake snake, List<List<Integer>> grid) {
        List<Integer> lastStep = lastMove();

        List<Integer[]> stepPossible = buildStepPossible(lastStep, grid.size());

        int positionX, positionY, value;
        List<Integer> attempt = new ArrayList(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle( attempt );

        accept : for ( int i = 0; i <= attempt.size(); i++ ) {
            Integer[] nextStepSelect = stepPossible.get(0);

            positionX = nextStepSelect[0];
            positionY = nextStepSelect[1];
            value = grid.get(positionX).get(positionY);

            boolean accept = acceptBoth(positionX, positionY, snake);

            if ( accept ) {
                Object[] values = new Object[3];
                values[0] = positionX;
                values[1] = positionY;
                values[2] = value;
                return values;
            }
        }

        return null;
    }

    private List<Integer[]> buildStepPossible(List<Integer> lastStep, int gridSize) {
        List<Integer[]> stepsPossibles = new ArrayList<>();

        int positionX = lastStep.get(0);
        int positionY = lastStep.get(1);

        //Right
        if ( (positionX+1) <= (gridSize-1) ) {
            stepsPossibles.add(new Integer[]{positionX + 1, positionY});
        }

        //Left
        if ( (positionX-1) >= 0 ) {
            stepsPossibles.add(new Integer[]{positionX - 1, positionY});
        }

        //Top
        if ( (positionY-1) >= 0 ) {
            stepsPossibles.add(new Integer[]{positionX, positionY - 1});
        }

        //Bottom
        if ( (positionY+1) <= (gridSize-1) ) {
            stepsPossibles.add(new Integer[]{positionX, positionY + 1});
        }

        return stepsPossibles;
    }

}