import javax.swing.*;
import java.util.Scanner;

    /*
        Student Name - Hasal Fernando
        UoW ID - w1697758
     */

public class FlowNetworkGenerator extends JApplet{

    private int numOfNodes, numOfEdges, tempForAugPath = 0;
    private char[] nodeNames;
    private int[] edge_u;
    private int[] edge_v;
    private int[][] edge_capacity;
    private static Scanner sc = new Scanner(System.in);
    private static String ordinalIndicator;

    public static void main(String[] args) throws InterruptedException {


        FlowNetworkGenerator flowNetworkGenerator = new FlowNetworkGenerator();
        System.out.println("Please select an option from the menu below to find the maximum flow\n1. Select my own number of nodes, edges and capacities\n2. Randomly generate a graph");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid integer");
            sc.next();
        }
        int selectedOption = sc.nextInt();
        while(selectedOption!=1 && selectedOption!=2){
            System.out.println("Your have entered a wrong input");
            System.out.println("Please select an option from the menu below to find the maximum flow\n1. Select my own number of nodes, edges and capacities\n2. Randomly generate a graph");
            while(!sc.hasNextInt()){
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            selectedOption = sc.nextInt();
        }
        System.out.println("Please note that '0' is the starting node, which means s = 0");
        if (selectedOption == 1) {
            System.out.println("Please enter your preferred number of nodes (starting node and ending node inclusive)");
            while(!sc.hasNextInt()){
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            flowNetworkGenerator.numOfNodes = sc.nextInt();
            while(!(6<=flowNetworkGenerator.numOfNodes) ||!(flowNetworkGenerator.numOfNodes<=12)){
                System.out.println("Your have entered a wrong input");
                System.out.println("Please enter a number between 6 to 12");
                while(!sc.hasNextInt()){
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                flowNetworkGenerator.numOfNodes = sc.nextInt();
            }
            System.out.println("Please enter your preferred number of edges");
            while(!sc.hasNextInt()){
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            flowNetworkGenerator.numOfEdges = sc.nextInt();
            while(!(4<=flowNetworkGenerator.numOfEdges) ||!(flowNetworkGenerator.numOfEdges<=((flowNetworkGenerator.numOfNodes * (flowNetworkGenerator.numOfNodes - 1)) - (2 * flowNetworkGenerator.numOfNodes) + 3))){
                System.out.println("Your have entered a wrong number of edges");
                System.out.println("Please enter a number between "+4+" and "+((flowNetworkGenerator.numOfNodes * (flowNetworkGenerator.numOfNodes - 1)) - (2 * flowNetworkGenerator.numOfNodes) + 3));
                while(!sc.hasNextInt()){
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                flowNetworkGenerator.numOfEdges = sc.nextInt();
            }
            flowNetworkGenerator.nodeNames = new char[flowNetworkGenerator.numOfNodes];
            flowNetworkGenerator.edge_u = new int[flowNetworkGenerator.numOfEdges];
            flowNetworkGenerator.edge_v = new int[flowNetworkGenerator.numOfEdges];
            flowNetworkGenerator.edge_capacity = new int[flowNetworkGenerator.numOfNodes][flowNetworkGenerator.numOfNodes];

            for (int i = 0; i < flowNetworkGenerator.numOfEdges; i++) {
                int start, end, capacity;
                flowNetworkGenerator.ordinalIndicatorAssigner(i);
                System.out.println("Please enter the starting node of your " + (i + 1) + ordinalIndicator+" edge");
                while(!sc.hasNextInt()){
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                start = sc.nextInt();
                while((start>=flowNetworkGenerator.numOfNodes-1)||(start<0)){
                    System.out.println("Your have entered a wrong input");
                    System.out.println("Please enter a number between 0 and "+(flowNetworkGenerator.numOfNodes-2));
                    while(!sc.hasNextInt()){
                        System.out.println("Please enter a valid integer");
                        sc.next();
                    }
                    start = sc.nextInt();
                }
                flowNetworkGenerator.edge_u[i] = start;
                System.out.println("Please enter the ending node of your " + (i + 1) + ordinalIndicator+" edge");
                while(!sc.hasNextInt()){
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                end = sc.nextInt();
                while((end>flowNetworkGenerator.numOfNodes-1)||(end<=0)){
                    System.out.println("Your have entered a wrong input");
                    System.out.println("Please enter a number between 1 and "+(flowNetworkGenerator.numOfNodes-1));
                    while(!sc.hasNextInt()){
                        System.out.println("Please enter a valid integer");
                        sc.next();
                    }
                    end = sc.nextInt();
                }
                flowNetworkGenerator.edge_v[i] = end;
                System.out.println("Please enter the capacity of your " + (i + 1) + ordinalIndicator+" edge");
                while(!sc.hasNextInt()){
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                capacity = sc.nextInt();
                while((capacity>20)||(capacity<5)){
                    System.out.println("Your have entered a wrong input");
                    System.out.println("Please enter a number between 5 and 20");
                    while(!sc.hasNextInt()){
                        System.out.println("Please enter a valid integer");
                        sc.next();
                    }
                    capacity = sc.nextInt();
                }
                flowNetworkGenerator.edge_capacity[start][end] = capacity;

            }
            System.out.println("Number of Nodes(including s and t): " + flowNetworkGenerator.numOfNodes);
            System.out.println("Number of Edges: " + flowNetworkGenerator.numOfEdges);
            System.out.println("\n-------------------------");
            System.out.println("| Connection | Capacity |");
            System.out.println("-------------------------");
            flowNetworkGenerator.assignNodeNames();
            flowNetworkGenerator.printEdgeCapacities();
        }
        else {
            flowNetworkGenerator.numOfNodes = flowNetworkGenerator.generateRandomNumber(6, 12);
            flowNetworkGenerator.numOfEdges = flowNetworkGenerator.generateRandomNumber(4, ((flowNetworkGenerator.numOfNodes * (flowNetworkGenerator.numOfNodes - 1)) - (2 * flowNetworkGenerator.numOfNodes) + 3));
            flowNetworkGenerator.nodeNames = new char[flowNetworkGenerator.numOfNodes];
            flowNetworkGenerator.edge_u = new int[flowNetworkGenerator.numOfEdges];
            flowNetworkGenerator.edge_v = new int[flowNetworkGenerator.numOfEdges];
            flowNetworkGenerator.edge_capacity = new int[flowNetworkGenerator.numOfNodes][flowNetworkGenerator.numOfNodes];
            flowNetworkGenerator.tempForAugPath = flowNetworkGenerator.generateRandomNumber(1, flowNetworkGenerator.numOfNodes - 2);
            System.out.println("Number of Nodes(including s and t): " + flowNetworkGenerator.numOfNodes);
            System.out.println("Number of Edges: " + flowNetworkGenerator.numOfEdges);
            System.out.println("\n-------------------------");
            System.out.println("| Connection | Capacity |");
            System.out.println("-------------------------");
            flowNetworkGenerator.assignNodeNames();
            flowNetworkGenerator.generateNetwork();
            flowNetworkGenerator.printEdgeCapacities();
        }


        MxGraph drawnGraph = flowNetworkGenerator.graphGenerator();
        MaxFlowFinder m = new MaxFlowFinder();

        //Get the time to calculate the elapsed time at the end
        long startTime = System.currentTimeMillis();

        //Print calculated maximum flow on the console
        int maximumFlow = m.findMaxFlow(flowNetworkGenerator.edge_capacity, 0, flowNetworkGenerator.numOfNodes-1, flowNetworkGenerator.numOfNodes,drawnGraph,flowNetworkGenerator.nodeNames);
        System.out.println("\nThe maximum possible flow is " + maximumFlow);
        //Print the elapsed time
        System.out.println("Elapsed milli seconds: "+(System.currentTimeMillis()-startTime));
        drawnGraph.updateMaxFlow(maximumFlow);

        System.out.println("Do you want to edit the existing graph ? Enter 1 to edit");
        int edit = sc.nextInt();
        while(edit==1){
            flowNetworkGenerator.removeEdges();
            flowNetworkGenerator.addEdges();
            drawnGraph = flowNetworkGenerator.graphGenerator();
            m = new MaxFlowFinder();

            //Get the time to calculate the elapsed time at the end
            startTime = System.currentTimeMillis();

            //Print calculated maximum flow on the console
            maximumFlow = m.findMaxFlow(flowNetworkGenerator.edge_capacity, 0, flowNetworkGenerator.numOfNodes-1, flowNetworkGenerator.numOfNodes, drawnGraph, flowNetworkGenerator.nodeNames);
            System.out.println("\nThe maximum possible flow is " + maximumFlow);
            //Print the elapsed time
            System.out.println("Elapsed milli seconds: "+(System.currentTimeMillis()-startTime));
            drawnGraph.updateMaxFlow(maximumFlow);

            System.out.println("Do you want to edit the existing graph ? Enter 1 to edit");
            edit = sc.nextInt();
        }

    }

    private void addEdges() {

        //Taking user inputs while doing validations
        System.out.println("Please enter your preferred number of edges to add");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid integer");
            sc.next();
        }
        int newEdges = sc.nextInt();
        int edgeStart, edgeEnd, capacity;
        for (int i = 0; i < newEdges; i++) {
            ordinalIndicatorAssigner(i);
            System.out.println("Please enter the starting node of your " + (i + 1) + ordinalIndicator + " edge");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            edgeStart = sc.nextInt();
            while ((edgeStart >= numOfNodes - 1) || (edgeStart < 0)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Please enter a number between 0 and " + (numOfNodes - 2)+" to add starting node");
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                edgeStart = sc.nextInt();
            }
            System.out.println("Please enter the ending node of your " + (i + 1) + ordinalIndicator + " edge");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            edgeEnd = sc.nextInt();
            while ((edgeEnd > numOfNodes - 1) || (edgeEnd <= 0)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Please enter a number between 1 and " + (numOfNodes - 1)+ " to add ending node");
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                edgeEnd = sc.nextInt();
            }
            System.out.println("Please enter the capacity of your " + (i + 1) + ordinalIndicator + " edge");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            capacity = sc.nextInt();
            while ((capacity > 20) || (capacity < 5)) {
                System.out.println("Your have entered a wrong input");
                System.out.println("Please enter a number between 5 and 20 to add new capacity");
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                capacity = sc.nextInt();
            }
            edge_capacity[edgeStart][edgeEnd] = capacity;
        }
    }

    //Assigning the ordinal Indicator
    private void ordinalIndicatorAssigner(int i) {
        switch (i){
            case 0:
                ordinalIndicator = "st";
                break;
            case 1:
                ordinalIndicator = "nd";
                break;
            case 2:
                ordinalIndicator = "rd";
                break;
            default:
                ordinalIndicator = "th";
        }
    }

    //If the user wants to remove an edge in the process of editing the existing graph
    private void removeEdges() throws InterruptedException{

        System.out.println("Please enter your preferred number of edges to remove");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a valid integer");
            sc.next();
        }
        int edgesToRemove = sc.nextInt();

        for (int i = 0; i < edgesToRemove; i++) {
            int start, end;
            ordinalIndicatorAssigner(i);
            System.out.println("Please enter the starting node of your " + (i + 1) + ordinalIndicator+" edge to remove");
            while(!sc.hasNextInt()){
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            start = sc.nextInt();
            while((start>=numOfNodes-1)||(start<0)){
                System.out.println("Your have entered a wrong input");
                System.out.println("Please enter a number between 0 and "+(numOfNodes-2));
                while(!sc.hasNextInt()){
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                start = sc.nextInt();
            }
            System.out.println("Please enter the ending node of your " + (i + 1) + ordinalIndicator+" edge to remove");
            while(!sc.hasNextInt()){
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            end = sc.nextInt();
            while((end>numOfNodes-1)||(end<=0)){
                System.out.println("Your have entered a wrong input");
                System.out.println("Please enter a number between 1 and "+(numOfNodes-1));
                while(!sc.hasNextInt()){
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                end = sc.nextInt();
            }
            edge_capacity[start][end] = 0;

        }
        System.out.println("Number of Nodes(including s and t): " + numOfNodes);
        System.out.println("Number of Edges: " + numOfEdges);
        System.out.println("\n-------------------------");
        System.out.println("| Connection | Capacity |");
        System.out.println("-------------------------");
        printEdgeCapacities();

    }

    //Generate the flow network
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

    //Print the capacities of edges in a table, on the console
    private void printEdgeCapacities(){
        for (int i = 0; i<edge_u.length; i++) {
            System.out.print("|   "+nodeNames[edge_u[i]] + " -> ");
            System.out.print(nodeNames[edge_v[i]] + "   | ");
            System.out.printf("   %02d    |\n", edge_capacity[edge_u[i]][edge_v[i]]);
            System.out.println("-------------------------");
        }
        System.out.println("\nSteps to search the maximum flow:\n");
    }

    private void assignNodeNames(){
        for(int i=0; i<numOfNodes; i++){
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

    }

    //Connect two edges by adding a capacity
    private void connect(int u, int v){
        edge_capacity[u][v] = generateRandomNumber(5, 20);

    }

    //Check whether there is a capacity for the certain edge and return boolean
    private boolean isConnected(int u, int v){
        return edge_capacity[u][v] > 0;
    }

    //Generate the graph with correct number of nodes and edges before any flow
    private MxGraph graphGenerator(){

        MxGraph applet = new MxGraph();
        applet.createGraph(this.numOfNodes,this.numOfEdges, this.edge_capacity);
        return applet;
    }

}