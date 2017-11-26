import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid {

    private List<List<Integer>> grid;

    public List<List<Integer>> read(String path){
        try ( Stream<String> lines = Files.lines(Paths.get(path))) {
            grid = lines.map(line -> Arrays.asList( convertStringToInteger(line.split(";")) )).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }

    public Integer[] convertStringToInteger(String[] s) {
        Integer[] result = new Integer[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }

    public List<List<Integer>> getGrid() {
        return grid;
    }

    public void setGrid(List<List<Integer>> grid) {
        this.grid = grid;
    }
}