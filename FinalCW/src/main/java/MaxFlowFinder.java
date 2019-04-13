import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

class MaxFlowFinder extends JApplet{

    private int V = 0; //Number of vertices in graph

    //To keep track of steps in finding the maximum flow
    private static int number = 1;


    boolean bfs(int rGraph[][], int startingNode, int endingNode, int parent[]){

        //Visited array for the length of one dimension of the 2D array
        boolean visited[] = new boolean[V];

        //Initialize it with marking every node as unvisited
        for(int i=0; i<V; ++i) {
            visited[i] = false;
        }

        //List to store the nodes
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(startingNode);
        visited[startingNode] = true;
        //System.out.println("Visited " +s);

        parent[startingNode]=-1;

        while (queue.size()!=0){

            //Retrieve and remove the head of the list
            int u = queue.poll();

            for (int v=0; v<V; v++){

                //If a node has not been visited and it is also having a capacity
                if (visited[v]==false && rGraph[u][v] > 0){
                    //Add to the queue
                    queue.add(v);
                    parent[v] = u;
                    //Mark it as visited in the visited array
                    visited[v] = true;
                }

            }
        }

        //Return whether the last node (t node) has been visited or not
        return (visited[endingNode] == true);
    }

    // Returns tne maximum flow from s to t in the given graph
    synchronized int fordFulkerson(int[][] graph, int s, int t, int V, MxGraph drawnGraph) throws InterruptedException {

        //Creating temporary lists to store the starting nodes, ending nodes, path flows and renewed capacity lists
        //To graphically represent a flow starting from node 's' (node 0)

        List<Integer> tempU = new ArrayList<Integer>();
        List<Integer> tempV = new ArrayList<Integer>();
        List<Integer> finalU = new ArrayList<Integer>();
        List<Integer> finalV = new ArrayList<Integer>();

        //Length of one dimension of the 2D capacity array
        this.V = V;

        //Starting with u and ending with v
        int u, v;

        //Copying original capacity array to a new array
        int rGraph[][] = new int[V][V];
        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];

        //To store the maximum flow, initially it is 0
        int max_flow = 0;

        //Do flow generation while the last node can be visited
        while (bfs(rGraph, s, t, parent)){
            //Assign the maximum value to the path_flow variable on initialization
            int path_flow = Integer.MAX_VALUE;


            for (v=t; v!=s; v=parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            for (v=t; v != s; v=parent[v]){

                u = parent[v];
                System.out.println(number+". Connecting "+u+" to "+v+" having a capacity of "+rGraph[u][v]);
                System.out.println("---A flow of "+path_flow+ " is sent from "+u+" to "+v);
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;

                //To visualize the flow starting from s node
                if(u!=0){
                    finalU.clear();
                    finalV.clear();
                    tempU.add(u);
                    tempV.add(v);
                }
                else {
                    drawnGraph.addEdge(u, v, (rGraph[v][u]-graph[v][u]), graph,"red");
                    finalU.add(u);
                    finalV.add(v);
                    for(int i = tempU.size()-1; i>-1; i--) {
                        drawnGraph.addEdge(tempU.get(i), tempV.get(i), (rGraph[tempV.get(i)][tempU.get(i)]-graph[tempV.get(i)][tempU.get(i)]), graph, "red");
                        finalU.add(tempU.get(i));
                        finalV.add(tempV.get(i));
                    }

                    tempU.clear();
                    tempV.clear();
                }

                System.out.println("------Available capacity from "+u+" to "+v+" = "+rGraph[u][v]);
                System.out.println("------Available capacity from "+v+" to "+u+" = "+rGraph[v][u]);
                System.out.println(" ");
                number++;
            }

            max_flow += path_flow;
            //drawnGraph.updateMaxFlow(max_flow);

        }
        synchronized (drawnGraph) {
            for (int i = 0; i < finalU.size(); i++) {
                drawnGraph.addEdge(finalU.get(i), finalV.get(i), (rGraph[finalV.get(i)][finalU.get(i)] - graph[finalV.get(i)][finalU.get(i)]), graph, "blue");
                //drawnGraph.updateMaxFlow(max_flow);
            }
        }
        return max_flow;

    }

}