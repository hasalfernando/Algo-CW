import java.util.LinkedList;
import java.util.List;

public class FlowNetwork {

    private int numOfNodes = (int)((Math.random() * 7) + 6);
    private int numOfEdges = (int)(Math.random() * ((numOfNodes*(numOfNodes-1))-(2*numOfNodes)+3)-1)+2 ;
    private char[] nodeNames = new char[numOfNodes];
    private int[] edge_u = new int[numOfEdges];
    private int[] edge_v = new int[numOfEdges];
    private int[][] edge_capacity = new int[numOfNodes][numOfNodes];

    private int[][] connected = new int[numOfNodes][numOfNodes];

    public static void main(String[] args) {

        FlowNetwork flowNetwork = new FlowNetwork();

        System.out.println("Number of Nodes: "+flowNetwork.numOfNodes);
        System.out.println("Number of Edges: "+flowNetwork.numOfEdges);
        flowNetwork.assignNodeNames();

        int tempVNode = 0;
        int tempForAugPath = (int)(Math.random() * (flowNetwork.numOfNodes-2))+ 1;
        System.out.println("Node which connects source and sink: "+tempForAugPath);

        //Making sure at least 1 augmented path is there
        for(int i = 0; i<flowNetwork.numOfEdges; i++){
            do {
                if(i==0){
                    flowNetwork.edge_u[i] = 0;
                    tempVNode = tempForAugPath;
                }
                else if(i==1){
                    flowNetwork.edge_u[i] = tempForAugPath;
                    tempVNode = flowNetwork.numOfNodes-1;
                }
                else {
                    flowNetwork.edge_u[i] = (int) (Math.random() * (flowNetwork.numOfNodes-1));
                    tempVNode = (int)(Math.random() * (flowNetwork.numOfNodes-1))+1;
                }
            }while((tempVNode==flowNetwork.edge_u[i])||(flowNetwork.isConnected(flowNetwork.edge_u[i],tempVNode)));

            flowNetwork.edge_v[i]=tempVNode;
            flowNetwork.edge_capacity[flowNetwork.edge_u[i]][flowNetwork.edge_v[i]] = flowNetwork.capacityGenerator();
            flowNetwork.connect(flowNetwork.edge_u[i],flowNetwork.edge_v[i]);
        }

        flowNetwork.printEdgeCapacities();

        System.out.println("-----------------------");
        for(int i = 0; i<flowNetwork.numOfNodes; i++){
            for(int j = 0; j<flowNetwork.numOfNodes; j++) {
                System.out.print(flowNetwork.connected[i][j]+" ");
            }
            System.out.println(" ");
        }

        System.out.println("-----------------------");
        System.out.println("Capacity Matrix");
        for(int i = 0; i<flowNetwork.numOfNodes; i++){
            for(int j = 0; j<flowNetwork.numOfNodes; j++) {
                System.out.printf("%02d ",flowNetwork.edge_capacity[i][j]);
            }
            System.out.println(" ");
        }

        FordFulkerson m = new FordFulkerson();

        System.out.println("\nThe maximum possible flow is " + m.fordFulkerson(flowNetwork.edge_capacity, 0, flowNetwork.numOfNodes-1, flowNetwork.numOfNodes));


    }

    private void printEdgeCapacities(){
        for (int i = 0; i<edge_u.length; i++) {
            System.out.print(nodeNames[edge_u[i]] + " -> ");
            System.out.print(nodeNames[edge_v[i]] + " = ");
            System.out.print(edge_capacity[edge_u[i]][edge_v[i]] + "\n");
        }
    }

    private void assignNodeNames(){
        for(int i=0; i<numOfNodes; i++){
            if(i==0){
                nodeNames[i]='s';
                System.out.println(i+" is "+nodeNames[i]);
            }
            else if(i==numOfNodes-1){
                nodeNames[i]='t';
                System.out.println(i+" is "+nodeNames[i]);
            }
            else{
                nodeNames[i]=(char)(i+96);
                System.out.println(i+" is "+nodeNames[i]);
            }
        }

    }
    private int capacityGenerator() {
        return (int) ((Math.random() * 20) + 5);
    }

    private void connect(int u, int v){
        connected[u][v] = 1;

        //System.out.println(u+" to "+v+" connected");
    }

    private boolean isConnected(int u, int v){
        if(connected[u][v]==1){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isHavingAugPath(int u, int v){
        boolean available = false;
        for(int i = 0; i<connected[u-1].length; i++){
            if(connected[u-1][i]>0){
                    if(connected[i][v-1]==1){
                        available = true;
                    }
            }
        }
        if(isConnected(u,v)){
            available = true;
        }
        return available;
    }
}
