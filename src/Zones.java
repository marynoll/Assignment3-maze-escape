import java.util.*;

public class Zones {

    public static int[] kosaraju(Input.Slides s) {
        int n = s.n();
        int[][] out = Graphs.slidesFrom(s);
        int[][] in = Graphs.slidesInto(s);
        int[] stack = new int[n];

        int[] finished = new int[n];
        int done = 0;
        boolean[] seen = new boolean[n];
        int[] next = new int[n];
        for (int start = 0; start < n; start++) {
            if (seen[start]) continue;
            int top = 0;
            stack[top++] = start;
            seen[start] = true;
            while (top > 0) {
                int u = stack[top - 1];
                if (next[u] < out[u].length) {
                    int v = s.to()[out[u][next[u]++]];
                    if (!seen[v]) { seen[v] = true; stack[top++] = v; }
                } else {
                    finished[done++] = u;
                    top--;
                }
            }
        }

        int[] zoneOf = new int[n];
        Arrays.fill(zoneOf, -1);
        int zones = 0;
        for (int i = n - 1; i >= 0; i--) {
            int start = finished[i];
            if (zoneOf[start] != -1) continue;
            int top = 0;
            stack[top++] = start;
            zoneOf[start] = zones;
            while (top > 0) {
                int u = stack[--top];
                for (int slide : in[u]) {
                    int v = s.from()[slide];
                    if (zoneOf[v] == -1) { zoneOf[v] = zones; stack[top++] = v; }
                }
            }
            zones++;
        }
        return zoneOf;
    }

    public static long[] routes(Input.Slides s, int[] zoneOf, int zoneCount) {
        Integer[] order = new Integer[s.count()];
        for (int i = 0; i < order.length; i++) order[i] = i;
        Arrays.sort(order, (x, y) -> Integer.compare(zoneOf[s.from()[x]], zoneOf[s.from()[y]]));

        long INF = Long.MAX_VALUE / 2;
        long[] chain = new long[zoneCount];
        long[] cost = new long[zoneCount];
        Arrays.fill(chain, 1);
        Arrays.fill(cost, INF);
        cost[zoneOf[0]] = 0;

        for (int i : order) {
            int x = zoneOf[s.from()[i]], y = zoneOf[s.to()[i]];
            if (x == y) continue;
            chain[y] = Math.max(chain[y], chain[x] + 1);
            if (cost[x] < INF) cost[y] = Math.min(cost[y], cost[x] + s.time()[i]);
        }

        long stages = 0;
        for (long c : chain) stages = Math.max(stages, c);
        return new long[]{stages, cost[zoneOf[s.n() - 1]]};
    }
}