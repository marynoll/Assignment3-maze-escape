/**
 * Mission 3. Escape: DFS from cell 0 to the last cell (rows * cols - 1) in the maze of Mission 1.
 * Two neighbouring cells are connected when the wall between them is broken.
 * The neighbours are tried in this order: up, right, down, left (cell - cols, cell + 1, cell + cols, cell - 1).
 * The ready method Graphs.open(w, broken) gives you the open neighbours of every cell, in no particular order:
 * to visit them in the required order, go through the four cells above one by one and take those that are in the list.
 * The program must not overflow the stack on a maze of 1000 by 1000 cells.
 */
public class Escape {

    public record Result(int[] path, int visited, int deadEnds) {}

    public static Result escape(Input.Walls w, int[] broken) {
        int n = w.cells(), cols = w.cols(), last = n - 1;
        int[][] open = Graphs.open(w, broken);
        boolean[] seen = new boolean[n];
        int[] parent = new int[n];
        int[] dir = new int[n];
        int[] stack = new int[n];
        int top = 0;

        stack[top++] = 0;
        seen[0] = true;
        int visited = 1;
        while (!seen[last]) {
            int u = stack[top - 1];
            if (dir[u] == 4) { top--; continue; }
            int[] around = {u - cols, u + 1, u + cols, u - 1};
            int v = around[dir[u]++];
            if (v >= 0 && v < n && !seen[v] && isOpen(open[u], v)) {
                seen[v] = true;
                visited++;
                parent[v] = u;
                stack[top++] = v;
            }
        }

        int length = 1;
        for (int v = last; v != 0; v = parent[v]) length++;
        int[] path = new int[length];
        int v = last;
        for (int i = length - 1; i >= 0; i--) {
            path[i] = v;
            v = parent[v];
        }

        int deadEnds = 0;
        for (int[] list : open) if (list.length == 1) deadEnds++;
        return new Result(path, visited, deadEnds);
    }

    private static boolean isOpen(int[] list, int v) {
        for (int x : list) if (x == v) return true;
        return false;
    }
}
