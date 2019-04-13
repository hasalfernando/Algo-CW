import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class FlowNetworkGenerator extends JApplet{

    private int numOfNodes, numOfEdges, tempForAugPath = 0;
    private char[] nodeNames;
    private int[] edge_u;
    private int[] edge_v;
    private int[][] edge_capacity;

    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private static int number = 1;

    //private int[][] connected = new int[numOfNodes][numOfNodes];

    public static void main(String[] args) throws InterruptedException {

        FlowNetworkGenerator flowNetworkGenerator = new FlowNetworkGenerator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select an option from the menu below to find the maximum flow\n1. Select my own number of nodes, edges and capacities\n2. Randomly generate a graph");
        int selectedOption = sc.nextInt();
        while(selectedOption!=1 && selectedOption!=2){
            System.out.println("Your have entered a wrong input");
            System.out.println("Please select an option from the menu below to find the maximum flow\n1. Select my own number of nodes, edges and capacities\n2. Randomly generate a graph");
            selectedOption = sc.nextInt();
        }
        System.out.println("Please note that '0' is the starting node, which means s = 0");
        if (selectedOption == 1) {
            System.out.println("Please enter your preferred number of nodes");
            flowNetworkGenerator.numOfNodes = sc.nextInt();
            System.out.println("Please enter your preferred number of edges");
            flowNetworkGenerator.numOfEdges = sc.nextInt();
            flowNetworkGenerator.nodeNames = new char[flowNetworkGenerator.numOfNodes];
            flowNetworkGenerator.edge_u = new int[flowNetworkGenerator.numOfEdges];
            flowNetworkGenerator.edge_v = new int[flowNetworkGenerator.numOfEdges];
            flowNetworkGenerator.edge_capacity = new int[flowNetworkGenerator.numOfNodes][flowNetworkGenerator.numOfNodes];

            for (int i = 0; i < flowNetworkGenerator.numOfEdges; i++) {
                int start = 0;
                int end = 0;
                int capacity = 0;
                if (i == 0) {
                    System.out.println("Please enter the starting node of your " + (i + 1) + "st edge");
                    start = sc.nextInt();
                    flowNetworkGenerator.edge_u[i] = start;
                    System.out.println("Please enter the ending node of your " + (i + 1) + "st edge");
                    end = sc.nextInt();
                    flowNetworkGenerator.edge_v[i] = end;
                    System.out.println("Please enter the capacity of your " + (i + 1) + "st edge");
                    capacity = sc.nextInt();
                    flowNetworkGenerator.edge_capacity[start][end] = capacity;
                } else if (i == 1) {
                    System.out.println("Please enter the starting node of your " + (i + 1) + "nd edge");
                    start = sc.nextInt();
                    flowNetworkGenerator.edge_u[i] = start;
                    System.out.println("Please enter the ending node of your " + (i + 1) + "nd edge");
                    end = sc.nextInt();
                    flowNetworkGenerator.edge_v[i] = end;
                    System.out.println("Please enter the capacity of your " + (i + 1) + "nd edge");
                    capacity = sc.nextInt();
                    flowNetworkGenerator.edge_capacity[start][end] = capacity;
                } else if (i == 2) {
                    System.out.println("Please enter the starting node of your " + (i + 1) + "rd edge");
                    start = sc.nextInt();
                    flowNetworkGenerator.edge_u[i] = start;
                    System.out.println("Please enter the ending node of your " + (i + 1) + "rd edge");
                    end = sc.nextInt();
                    flowNetworkGenerator.edge_v[i] = end;
                    System.out.println("Please enter the capacity of your " + (i + 1) + "rd edge");
                    capacity = sc.nextInt();
                    flowNetworkGenerator.edge_capacity[start][end] = capacity;
                } else {
                    System.out.println("Please enter the starting node of your " + (i + 1) + "th edge");
                    start = sc.nextInt();
                    flowNetworkGenerator.edge_u[i] = start;
                    System.out.println("Please enter the ending node of your " + (i + 1) + "th edge");
                    end = sc.nextInt();
                    flowNetworkGenerator.edge_v[i] = end;
                    System.out.println("Please enter the capacity of your " + (i + 1) + "th edge");
                    capacity = sc.nextInt();
                    flowNetworkGenerator.edge_capacity[start][end] = capacity;
                }
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
            flowNetworkGenerator.numOfEdges = flowNetworkGenerator.generateRandomNumber(4, (flowNetworkGenerator.numOfNodes * (flowNetworkGenerator.numOfNodes - 1)) - (2 * flowNetworkGenerator.numOfNodes) + 3);
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


//        System.out.println("Node which connects source and sink: "+flowNetworkGenerator.tempForAugPath);



/*
        System.out.println("-----------------------");
        for(int i = 0; i<flowNetworkGenerator.numOfNodes; i++){
            for(int j = 0; j<flowNetworkGenerator.numOfNodes; j++) {
                System.out.print(flowNetworkGenerator.connected[i][j]+" ");
            }
            System.out.println(" ");
        }

        System.out.println("-----------------------");
        System.out.println("Capacity Matrix");
        for(int i = 0; i< flowNetworkGenerator.numOfNodes; i++){
            for(int j = 0; j< flowNetworkGenerator.numOfNodes; j++) {
                System.out.printf("%02d ", flowNetworkGenerator.edge_capacity[i][j]);
            }
            System.out.println(" ");
        }
*/
        MxGraph drawnGraph = flowNetworkGenerator.graphGenerator();

        FordFulkerson m = new FordFulkerson();

        System.out.println("\nThe maximum possible flow is " + m.fordFulkerson(flowNetworkGenerator.edge_capacity, 0, flowNetworkGenerator.numOfNodes-1, flowNetworkGenerator.numOfNodes,drawnGraph));



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
            System.out.print("|   "+nodeNames[edge_u[i]] + " -> ");
            System.out.print(nodeNames[edge_v[i]] + "   | ");
            System.out.printf("   %02d    |\n", edge_capacity[edge_u[i]][edge_v[i]]);
            //System.out.print(edge_capacity[edge_u[i]][edge_v[i]] + " |\n");
            System.out.println("-------------------------");
        }
        System.out.println("\nSteps to search the maximum flow:\n");
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

    //Connect two edges by adding a capacity
    private void connect(int u, int v){
        edge_capacity[u][v] = (int) ((Math.random() * 20) + 5);

    }

    private boolean isConnected(int u, int v){
        return edge_capacity[u][v] > 0;
    }

    public MxGraph graphGenerator(){

        MxGraph applet = new MxGraph();
        applet.createGraph(this.numOfNodes,this.numOfEdges, this.edge_capacity);
        return applet;
/*       JGraphAdapterDemo applet = new JGraphAdapterDemo(this.numOfNodes,this.numOfEdges, this.edge_capacity);
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Flow Network Representation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
*/
    }

}
