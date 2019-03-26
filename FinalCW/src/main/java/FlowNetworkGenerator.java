public class FlowNetworkGenerator {

    private int numOfNodes, numOfEdges, tempForAugPath = 0;
    private char[] nodeNames;
    private int[] edge_u;
    private int[] edge_v;
    private int[][] edge_capacity;

    //private int[][] connected = new int[numOfNodes][numOfNodes];

    public static void main(String[] args) {

        FlowNetworkGenerator flowNetworkGenerator = new FlowNetworkGenerator();

        flowNetworkGenerator.numOfNodes = flowNetworkGenerator.generateRandomNumber(6,12);
        flowNetworkGenerator.numOfEdges= flowNetworkGenerator.generateRandomNumber(2,(flowNetworkGenerator.numOfNodes*(flowNetworkGenerator.numOfNodes-1))-(2*flowNetworkGenerator.numOfNodes)+3);
        flowNetworkGenerator.nodeNames = new char[flowNetworkGenerator.numOfNodes];
        flowNetworkGenerator.edge_u = new int[flowNetworkGenerator.numOfEdges];
        flowNetworkGenerator.edge_v = new int[flowNetworkGenerator.numOfEdges];
        flowNetworkGenerator.edge_capacity = new int[flowNetworkGenerator.numOfNodes][flowNetworkGenerator.numOfNodes];
        flowNetworkGenerator.tempForAugPath = flowNetworkGenerator.generateRandomNumber(1,flowNetworkGenerator.numOfNodes-2);
        System.out.println("Number of Nodes(including s and t): "+ flowNetworkGenerator.numOfNodes);
        System.out.println("Number of Edges: "+ flowNetworkGenerator.numOfEdges);

        flowNetworkGenerator.assignNodeNames();

        System.out.println("Node which connects source and sink: "+flowNetworkGenerator.tempForAugPath);

        flowNetworkGenerator.generateNetwork();


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

    private void generateNetwork(){

        int tempVNode;

        //Making sure at least 1 augmented path is there
        for(int i = 0; i< this.numOfEdges; i++){
            do {
                if(i==0){
                    this.edge_u[i] = 0;
                    tempVNode = tempForAugPath;
                }
                else if(i==1){
                    this.edge_u[i] = tempForAugPath;
                    tempVNode = this.numOfNodes-1;
                }
                else {
                    this.edge_u[i] = (int) (Math.random() * (this.numOfNodes-1));
                    tempVNode = (int)(Math.random() * (this.numOfNodes-1))+1;
                }
            }while((tempVNode== this.edge_u[i])||(this.isConnected(this.edge_u[i],tempVNode)));

            this.edge_v[i]=tempVNode;
            this.connect(this.edge_u[i], this.edge_v[i]);
        }

    }
    private int generateRandomNumber(int min, int max){
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
        return edge_capacity[u][v] > 0;
    }

}
