public class FlowNetworkGenerator {

    private int numOfNodes = 0;
    private int numOfEdges = 0;
    private char[] nodeNames;
    private int[] edge_u;
    private int[] edge_v;
    private int[][] edge_capacity;

    //private int[][] connected = new int[numOfNodes][numOfNodes];

    public static void main(String[] args) {

        FlowNetworkGenerator flowNetworkGenerator = new FlowNetworkGenerator();

        flowNetworkGenerator.numOfNodes = flowNetworkGenerator.randomNumberGenerator(6,12);
        flowNetworkGenerator.numOfEdges= flowNetworkGenerator.randomNumberGenerator(2,(flowNetworkGenerator.numOfNodes*(flowNetworkGenerator.numOfNodes-1))-(2*flowNetworkGenerator.numOfNodes)+3);
        flowNetworkGenerator.nodeNames = new char[flowNetworkGenerator.numOfNodes];
        flowNetworkGenerator.edge_u = new int[flowNetworkGenerator.numOfEdges];
        flowNetworkGenerator.edge_v = new int[flowNetworkGenerator.numOfEdges];
        flowNetworkGenerator.edge_capacity = new int[flowNetworkGenerator.numOfNodes][flowNetworkGenerator.numOfNodes];

        System.out.println("Number of Nodes: "+ flowNetworkGenerator.numOfNodes);
        System.out.println("Number of Edges: "+ flowNetworkGenerator.numOfEdges);

        flowNetworkGenerator.assignNodeNames();

        int tempVNode = 0;
        int tempForAugPath = flowNetworkGenerator.randomNumberGenerator(1,flowNetworkGenerator.numOfNodes-2);
        System.out.println("Node which connects source and sink: "+tempForAugPath);

        //Making sure at least 1 augmented path is there
        for(int i = 0; i< flowNetworkGenerator.numOfEdges; i++){
            do {
                if(i==0){
                    flowNetworkGenerator.edge_u[i] = 0;
                    tempVNode = tempForAugPath;
                }
                else if(i==1){
                    flowNetworkGenerator.edge_u[i] = tempForAugPath;
                    tempVNode = flowNetworkGenerator.numOfNodes-1;
                }
                else {
                    flowNetworkGenerator.edge_u[i] = (int) (Math.random() * (flowNetworkGenerator.numOfNodes-1));
                    tempVNode = (int)(Math.random() * (flowNetworkGenerator.numOfNodes-1))+1;
                }
            }while((tempVNode== flowNetworkGenerator.edge_u[i])||(flowNetworkGenerator.isConnected(flowNetworkGenerator.edge_u[i],tempVNode)));

            flowNetworkGenerator.edge_v[i]=tempVNode;
            flowNetworkGenerator.connect(flowNetworkGenerator.edge_u[i], flowNetworkGenerator.edge_v[i]);
        }

        flowNetworkGenerator.printEdgeCapacities();
/*
        System.out.println("-----------------------");
        for(int i = 0; i<flowNetworkGenerator.numOfNodes; i++){
            for(int j = 0; j<flowNetworkGenerator.numOfNodes; j++) {
                System.out.print(flowNetworkGenerator.connected[i][j]+" ");
            }
            System.out.println(" ");
        }
*/
        System.out.println("-----------------------");
        System.out.println("Capacity Matrix");
        for(int i = 0; i< flowNetworkGenerator.numOfNodes; i++){
            for(int j = 0; j< flowNetworkGenerator.numOfNodes; j++) {
                System.out.printf("%02d ", flowNetworkGenerator.edge_capacity[i][j]);
            }
            System.out.println(" ");
        }

        FordFulkerson m = new FordFulkerson();

        System.out.println("\nThe maximum possible flow is " + m.fordFulkerson(flowNetworkGenerator.edge_capacity, 0, flowNetworkGenerator.numOfNodes-1, flowNetworkGenerator.numOfNodes));


    }

    private int randomNumberGenerator(int min, int max){
        return (int)(Math.random()*(max-min+1))+min;
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
                //System.out.println(i+" is "+nodeNames[i]);
            }
            else if(i==numOfNodes-1){
                nodeNames[i]='t';
                //System.out.println(i+" is "+nodeNames[i]);
            }
            else{
                nodeNames[i]=(char)(i+96);
                //System.out.println(i+" is "+nodeNames[i]);
            }
        }

    }
    private int capacityGenerator() {
        return (int) ((Math.random() * 20) + 5);
    }

    private void connect(int u, int v){
        //connected[u][v] = 1;
        edge_capacity[u][v] = capacityGenerator();


        //System.out.println(u+" to "+v+" connected");
    }

    private boolean isConnected(int u, int v){
        if(edge_capacity[u][v]>0){
            return true;
        }
        else{
            return false;
        }
    }

}
