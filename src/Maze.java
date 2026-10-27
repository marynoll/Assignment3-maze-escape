/**
 * Missions 1 and 2. Building the maze out of the walls.
 * A wall k joins the cells w.a()[k] and w.b()[k] and costs w.cost()[k]. Indexes start from 0,
 * the line number in walls.txt is the index + 1. The cells are connected by DisjointSet.
 */
import java.util.*;

public class Maze {

    public static int[] breakWalls(Input.Walls w) {
        Integer[] order = new Integer[w.count()];
        for (int k = 0; k < order.length; k++) order[k] = k;
        return build(w, order);
    }

    public static int[] cheapestMaze(Input.Walls w) {
        Integer[] order = new Integer[w.count()];
        for (int k = 0; k < order.length; k++) order[k] = k;
        Arrays.sort(order, (x, y) -> Integer.compare(w.cost()[x], w.cost()[y]));
        return build(w, order);
    }

    private static int[] build(Input.Walls w, Integer[] order) {
        DisjointSet d = new DisjointSet(w.cells());
        int[] broken = new int[w.cells()];
        int n = 0;
        for (int k : order) {
            if (d.union(w.a()[k], w.b()[k])) broken[n++] = k;
        }
        return Arrays.copyOf(broken, n);
    }

    public static long primCost(Input.Walls w) {
        List<List<Integer>> walls = new ArrayList<>();
        for (int i = 0; i < w.cells(); i++) walls.add(new ArrayList<>());
        for (int k = 0; k < w.count(); k++) {
            walls.get(w.a()[k]).add(k);
            walls.get(w.b()[k]).add(k);
        }
        boolean[] inTree = new boolean[w.cells()];
        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> Integer.compare(x[0], y[0]));
        pq.add(new int[]{0, 0});
        long total = 0;
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int cell = top[1];
            if (inTree[cell]) continue;
            inTree[cell] = true;
            total += top[0];
            for (int k : walls.get(cell)) {
                int other = w.a()[k] == cell ? w.b()[k] : w.a()[k];
                if (!inTree[other]) pq.add(new int[]{w.cost()[k], other});
            }
        }
        return total;
    }
}