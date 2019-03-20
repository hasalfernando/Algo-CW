import java.util.LinkedList;
import java.util.List;

public class FlowNetwork {

    public static void main(String[] args) {

        int numOfNodes = (int)((Math.random() * 7) + 4);
        int numOfEdges = (int)((Math.random() * (numOfNodes*(numOfNodes-1))) + 1);
        int[] edge_u = new int[numOfEdges];
        int[] edge_v = new int[numOfEdges];
        int[] edge_capacity = new int[numOfEdges];
        int[] edge_reverse_capacity= new int[numOfEdges];

        System.out.println("Nodes: "+numOfNodes);
        System.out.println("Edges: "+numOfEdges);

        for(int i = 0; i<edge_u.length; i++){
            edge_u[i]=(int)((Math.random() * numOfNodes) + 1);
            int tempVNode;
            do{
                tempVNode = (int)((Math.random() * numOfNodes) + 1);
            }while(tempVNode == edge_u[i]);
            edge_v[i]=tempVNode;
            edge_capacity[i] = capacityGenerator();
        }

        System.out.println("edge U");
        for (int anEdge_u : edge_u) {
            System.out.print(anEdge_u + " ");
        }

        System.out.println("\nedge V");
        for (int anEdge_v : edge_v) {
            System.out.print(anEdge_v + " ");
        }

        System.out.println("\nCapacities");
        for (int anEdge_capacity : edge_capacity) {
            System.out.print(anEdge_capacity + " ");
        }

    }

    private static int capacityGenerator() {
        return (int) ((Math.random() * 20) + 5);
    }


}
