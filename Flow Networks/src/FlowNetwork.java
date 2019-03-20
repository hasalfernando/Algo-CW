import java.util.LinkedList;
import java.util.List;

public class FlowNetwork {

    private static int numOfNodes = (int)((Math.random() * 9) + 4);
    private static int numOfEdges = (int)((Math.random() * (numOfNodes*(numOfNodes-1))) + 1);
    private static int[] nodes = new int[numOfNodes];
    private static char[] nodeNames = new char[numOfNodes];
    private static int[] edge_u = new int[numOfEdges];
    private static int[] edge_v = new int[numOfEdges];
    private static int[] edge_capacity = new int[numOfEdges];
    private static int[] edge_reverse_capacity= new int[numOfEdges];

    private static int[][] connected = new int[numOfNodes][numOfNodes];

    public static void main(String[] args) {

        System.out.println("Nodes: "+numOfNodes);
        System.out.println("Edges: "+numOfEdges);

        for(int i=0; i<numOfNodes; i++){
            nodes[i] = i+1;
            if(i==0){
                nodeNames[i]='s';
            }
            else if(i==numOfNodes-1){
                nodeNames[i]='t';
            }
            else{
                nodeNames[i]=(char)(i+96);
            }
        }
        int tempVNode = 0;
        for(int i = 0; i<edge_u.length; i++){
            do {
                edge_u[i] = (int) ((Math.random() * numOfNodes) + 1);
                tempVNode = (int) ((Math.random() * numOfNodes) + 1);
            }while((tempVNode == edge_u[i])|(isConnected(edge_u[i],tempVNode)));

            edge_v[i]=tempVNode;
            edge_capacity[i] = capacityGenerator();
            connect(edge_u[i],edge_v[i]);
        }

        for (int i = 0; i<edge_u.length; i++) {
            System.out.print(nodeNames[edge_u[i]-1] + " -> ");
            System.out.print(nodeNames[edge_v[i]-1] + " = ");
            System.out.print(edge_capacity[i] + "\n");
        }

    }

    private static int capacityGenerator() {
        return (int) ((Math.random() * 20) + 5);
    }

    private static void connect(int u, int v){
        connected[u-1][v-1] = 1;
    }

    private static boolean isConnected(int u, int v){
        if(connected[u-1][v-1]==1){
            return true;
        }
        else{
            return false;
        }
    }
}
