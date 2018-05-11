import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 100005; // 10^5

        int n;
        int m;

        @SuppressWarnings("unchecked")
        ArrayList<Integer> adj[] = new ArrayList[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                                INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 1; i <= m; i++) {
                    int x, y;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    adj[x].add(y);
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<Integer> result) {
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
                                OUTPUT_FILE)));
                for (int i = 0; i < result.size(); i++) {
                    pw.printf("%d ", result.get(i));
                }
                pw.printf("\n");
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //DFS
        private void topologicalSortHelper(Stack<Integer> stack, boolean[] visited, int node) {
            visited[node] = true;
            int tmp;
            
            Iterator<Integer> iter = adj[node].iterator();
            while(iter.hasNext()) {
                tmp = iter.next();
                if(!visited[tmp]) {
                    topologicalSortHelper(stack, visited, tmp);
                }
            }
            stack.push(new Integer(node));
        }

        private ArrayList<Integer> getResult() {
            // Faceti sortarea topologica a grafului stocat cu liste de
            // adiacenta in adj.
            // *******
            // ATENTIE: nodurile sunt indexate de la 1 la n.
            // *******
            ArrayList<Integer> topsort = new ArrayList<>();
            boolean[] visited = new boolean[n+1];
            Stack<Integer> stack = new Stack<>();
            
            // Init
            for (int i = 0; i <= n; i++) {
                visited[i] = false;
            }
            
            // Sort the vertices using dfs
            for(int i = 1; i <= n; i++) {
                if(!visited[i]) {
                    topologicalSortHelper(stack, visited, i);
                }
            }
            
            while(!stack.isEmpty()) {
                topsort.add(stack.pop());
            }
            
            //System.out.println(topsort.toString());
            
            return topsort;
        }

        public void solve() {
            readInput();
            writeOutput(getResult());
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
