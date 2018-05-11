import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {

    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 50005;
        public static final int INF = 99999;

        int n;
        int m;
        int source;
        
        static class CustomComparator implements Comparator<Edge> {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost - o2.cost;
            }

        }
        
        public class Edge{
            public int node;
            public int cost;

            Edge(int _node, int _cost) {
                node = _node;
                cost = _cost;
            }
            @Override
            public boolean equals(Object obj) {
                Edge e = (Edge) obj;
                return node == e.node;
            }
        }

        @SuppressWarnings("unchecked")
        ArrayList<Edge> adj[] = new ArrayList[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                                INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();
                source = sc.nextInt();

                for (int i = 1; i <= n; i++) {
                    adj[i] = new ArrayList<>();
                }
                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    adj[x].add(new Edge(y, w));
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<Integer> result) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(
                                OUTPUT_FILE));
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= n; i++) {
                    sb.append(result.get(i)).append(' ');
                }
                sb.append('\n');
                bw.write(sb.toString());
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private ArrayList<Integer> getResult() {
            // Gasiti distantele minime de la nodul source la celelalte noduri
            // folosind Dijkstra pe graful orientat cu n noduri, m arce stocat in adj.
            //  d[node] = costul minim / lungimea minima a unui drum de la source la
            //  nodul node;
            //  d[source] = 0;
            //  d[node] = -1, daca nu se poate ajunge de la source la node.
            // Atentie:
            // O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
            //  adj[x].get(i).node = nodul adiacent lui x,
            //  adj[x].get(i).cost = costul.
            
            ArrayList<Integer> d = new ArrayList<>();
            CustomComparator comparator = new CustomComparator();
            PriorityQueue<Edge> queue = new PriorityQueue<>(comparator);
            int[] selectat = new int[n+1];
            selectat[source] = 1;
            
            //Init
            for (int i = 0; i <= n; i++)
                d.add(INF);
            
            for(Edge e : adj[source]) {
                d.set(e.node, e.cost);
                queue.add(new Edge(e.node, e.cost));
            }

            while (!queue.isEmpty()) {
                Edge u = queue.poll();
                selectat[u.node] = 1;
                 for(Edge nod : adj[u.node]) {
                    if(selectat[nod.node] == 0 && d.get(nod.node) > d.get(u.node) + nod.cost) {
                        d.set(nod.node, d.get(u.node) + nod.cost);
                        queue.add(new Edge(nod.node, d.get(u.node) + nod.cost));
                    }
                }
            }
            
            d.set(source, 0);
            d.set(0, 0);
            
            for (int i = 0; i <= n; i++) {
                if(d.get(i) == INF)
                    d.set(i, -1);
            }
            
            //PRINT
            // System.out.println(d.toString());
            return d;
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
