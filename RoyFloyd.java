import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 105;
        public static final int INF = 9999;

        int n;
        int d[][];
        int a[][];

        private void readInput() {
            try {
                Scanner sc = new Scanner(
                        new BufferedReader(new FileReader(INPUT_FILE)));
                n = sc.nextInt();
                d = new int[n + 1][n + 1];
                a = new int[n + 1][n + 1];
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= n; j++) {
                        a[i][j] = sc.nextInt();
                    }
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput() {
            try {
                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(OUTPUT_FILE));
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= n; j++) {
                        sb.append(d[i][j]).append(' ');
                    }
                    sb.append('\n');
                }
                bw.write(sb.toString());
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int min (int a, int b) {
            return (a < b) ? a : b;
        }
        
        private void compute() {
            // Gasiti distantele minime intre oricare doua noduri, folosind
            // RoyFloyd pe graful orientat cu n noduri, m arce stocat in matricea
            // ponderilor a (declarata mai sus).

            // Atentie:
            // O muchie (x, y, w) este reprezentata astfel in matricea ponderilor:
            //  a[x][y] = w;
            // Daca nu exista o muchie intre doua noduri x si y, in matricea
            // ponderilor se va afla valoarea 0:
            //  a[x][y] = 0;

            // Trebuie sa populati matricea d[][] (declarata mai sus):
            //  d[x][y] = distanta minima intre nodurile x si y, daca exista drum.
            //  d[x][y] = 0 daca nu exista drum intre x si y.
            //  d[x][x] = 0.
            
            // Initiliaze
            for (int i = 1; i <= n; i++) {
                for(int j = 1; j <= n; j++) {
                    if (i == j) {
                        d[i][i] = INF;
                        continue;
                    }
                    if(a[i][j] == 0) {
                        d[i][j] = INF;
                        continue;
                    }
                    d[i][j] = a[i][j];
                }
            }
       
            //Dynamic Programming Alg
            for(int k = 1; k <= n; k++) {
                for (int i = 1; i <= n; i++) {
                    for(int j = 1; j <= n; j++) {
                        d[i][j] = min(d[i][j], d[i][k] + d[k][j]);
                    } 
                }
            }
            
            // Clear diagonal
            for (int i = 1; i <= n; i++) {
                for(int j = 1; j <= n; j++) {
                    if (i == j) {
                        d[i][i] = 0;
                    }
                    if(d[i][j] == INF) {
                        d[i][j] = 0;
                    }
                }
            }
            
            // PRINT
            // for (int i = 1; i <= n; i++) {
            //     for(int j = 1; j <= n; j++) {
            //         System.out.print(d[i][j] + " ");
            //     }
            //     System.out.println();
            // }
        }

        public void solve() {
            readInput();
            compute();
            writeOutput();
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
