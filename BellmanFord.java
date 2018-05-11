import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 50005;
        public static final int INF = 99999;

        int n;
        int m;
        int source;

        public class Edge {
            public int node;
            public int cost;

            Edge(int _node, int _cost) {
                node = _node;
                cost = _cost;
            }
        }

        @SuppressWarnings("unchecked")
        ArrayList<Edge> adj[] = new ArrayList[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(
                        new BufferedReader(new FileReader(INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();
                source = sc.nextInt();

                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
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
                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(OUTPUT_FILE));
                StringBuilder sb = new StringBuilder();
                if (result.size() == 0) {
                    sb.append("Ciclu negativ!\n");
                } else {
                    for (int i = 1; i <= n; i++) {
                        sb.append(result.get(i)).append(' ');
                    }
                    sb.append('\n');
                }
                bw.write(sb.toString());
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private ArrayList<Integer> getResult() {
            // Gasiti distantele minime de la nodul source la celelalte
            // noduri
            // folosind BellmanFord pe graful orientat cu n noduri, m arce
            // stocat in
            // adj.
            // d[node] = costul minim / lungimea minima a unui drum de la source
            // la
            // nodul node;
            // d[source] = 0;
            // d[node] = -1, daca nu se poate ajunge de la source la node.

            // Atentie:
            // O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
            // adj[x].get(i).node = nodul adiacent lui x,
            // adj[x].get(i).cost = costul.

            // In cazul in care exista ciclu de cost negativ, returnati un
            // vector gol:
            // return new ArrayList<Integer>();
            
            ArrayList<Integer> d = new ArrayList<>();

            // Init
            for (int i = 0; i <= n; i++)
                d.add(INF);
            
            for(Edge e : adj[source]) {
                d.set(e.node, e.cost);
            }
            d.set(source, 0);
            d.set(0, 0);

            int flag = 1;       // used for optimization
            // Relaxation
            for (int i = 1; i <= n - 2; i++) {
                if(flag == 0) {
                    break;
                }
                flag = 0;
                for(int u = 1; u <= n; u++) {
                    for (Edge v : adj[u]) {
                        if (d.get(v.node) > d.get(u) + v.cost) {
                            d.set(v.node, d.get(u) + v.cost);
                            flag = 1;
                        }
                    }
                }
            }

            // If further relaxations can be done => Negative cycles
            for(int u = 1; u <= n; u++) {
                for (Edge v : adj[u]) {
                    if (d.get(v.node) > d.get(u) + v.cost) {
                        return new ArrayList<Integer>();
                    }
                }
            }
            // PRINT
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
