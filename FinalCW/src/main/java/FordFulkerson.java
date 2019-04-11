// Java program for implementation of Ford Fulkerson algorithm

import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

class FordFulkerson extends JApplet{

    private int V = 0; //Number of vertices in graph
    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private static int number = 1;


    boolean bfs(int rGraph[][], int s, int t, int parent[]){

        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        //System.out.println("Visited " +s);

        parent[s]=-1;

        while (queue.size()!=0){

            int u = queue.poll();

            for (int v=0; v<V; v++){
                if (visited[v]==false && rGraph[u][v] > 0){
                    queue.add(v);
                    parent[v] = u;
                    //System.out.println("Visited " +v);
                    visited[v] = true;
                }
            }
        }

        return (visited[t] == true);
    }

    // Returns tne maximum flow from s to t in the given graph
    int fordFulkerson(int[][] graph, int s, int t, int V, MxGraphSample drawnGraph) throws InterruptedException {

        /*
        List<MxGraphSample> tempList = new ArrayList<MxGraphSample>();
        List<Integer> tempU = new ArrayList<Integer>();
        List<Integer> tempV = new ArrayList<Integer>();
        List<Integer> pathFlow = new ArrayList<Integer>();
*/
        this.V = V;
        int u, v;

        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        int parent[] = new int[V];

        int max_flow = 0; // There is no flow initially

        while (bfs(rGraph, s, t, parent)){

            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            for (v=t; v != s; v=parent[v]){

                u = parent[v];
                System.out.println(number+". Connecting "+u+" to "+v+" having a capacity of "+rGraph[u][v]);
                System.out.println("---A flow of "+path_flow+ " is sent from "+u+" to "+v);
                drawnGraph.addEdge(u,v,path_flow,rGraph);
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
                System.out.println("------Available capacity from "+u+" to "+v+" = "+rGraph[u][v]);
                System.out.println("------Available capacity from "+v+" to "+u+" = "+rGraph[v][u]);
                System.out.println(" ");
                number++;
            }

            max_flow += path_flow;
        }
        return max_flow;
    }

}
