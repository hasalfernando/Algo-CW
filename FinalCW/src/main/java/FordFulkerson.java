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
import java.util.LinkedList;

class FordFulkerson extends JApplet{

    private int V = 0; //Number of vertices in graph
    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;


    boolean bfs(int rGraph[][], int s, int t, int parent[]){

        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        System.out.println("Visited " +s);

        parent[s]=-1;

        // Standard BFS Loop
        while (queue.size()!=0){

            int u = queue.poll();

            for (int v=0; v<V; v++){
                if (visited[v]==false && rGraph[u][v] > 0){
                    queue.add(v);
                    parent[v] = u;
                    System.out.println("Visited " +v);
                    visited[v] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }

    // Returns tne maximum flow from s to t in the given graph
    int fordFulkerson(int graph[][], int s, int t, int V){

        //graphGenerator();
        this.V = V;
        int u, v;

        // Create a residual graph and fill the residual graph
        // with given capacities in the original graph as
        // residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, t, parent)){
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v]){

                u = parent[v];
                System.out.println("Connecting "+u+" to "+v+" having a capacity of "+rGraph[u][v]);
                System.out.println("---A flow of "+path_flow+ " is sent from "+u+" to "+v);
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
                System.out.println("------Available capacity from "+u+" to "+v+" = "+rGraph[u][v]);
                System.out.println("------Available capacity from "+v+" to "+u+" = "+rGraph[v][u]);
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }
        System.out.println("Visited " +t);
        // Return the overall flow
        return max_flow;
    }

    // Driver program to test above functions
    public static void main (String[] args) throws Exception{

        // Let us create a graph shown in the above example
        int graph[][] =new int[][] {
                {0, 16, 13, 0, 0, 0},
                {0, 0, 10, 12, 0, 0},
                {0, 4, 0, 0, 14, 0},
                {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4},
                {0, 0, 0, 0, 0, 0}
        };

        FordFulkerson m = new FordFulkerson();

        //System.out.println("The maximum possible flow is " + m.fordFulkerson(graph, 0, 5));

    }

    public void graphGenerator(){
        JGraphAdapterDemo applet = new JGraphAdapterDemo();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<String, DefaultEdge>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class));
        jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add some sample data (graph manipulated via JGraphX)
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v2, v1);
        g.addEdge(v3, v1);
        g.addEdge(v4, v3);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...

    }
}
