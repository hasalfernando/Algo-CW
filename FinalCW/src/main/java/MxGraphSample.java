import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class MxGraphSample {

    private List<Object> vertexList = new ArrayList<Object>();
    private int numOfNodes = 0;
    private int numOfEdges = 0;
    private List<Integer> lowerSList = new ArrayList<Integer>();
    private int[][] edgeCapacity;
    private int x = 20;
    private final int maxX = 1500;
    private int y = 500;
    private final int maxY = 1000;

    public void createGraph(int numOfNodes, int numOfEdges, int[][] edgeCapacity) {

        this.numOfNodes = numOfNodes;
        this.numOfEdges = numOfEdges;
        this.edgeCapacity = edgeCapacity;
        int n =(this.numOfNodes-2);
        this.lowerSList.add(3);
        this.lowerSList.add(4);
        this.lowerSList.add(7);
        this.lowerSList.add(8);
        final JFrame frame = new JFrame();
        frame.setSize(1500, 1000);
        JPanel panel = new JPanel();
        panel.setSize(frame.getMaximumSize().width,
                frame.getMaximumSize().height);

        final mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        try {

            for(int i = 0; i<numOfNodes; i++) {
                if (i == 0) {
                    System.out.println("x : "+this.x);
                    System.out.println("y : "+this.y);
                    graph.insertVertex(parent, "0", "s", this.x, y, 50, 30);
                    this.x = (1000/n);
                    this.y = (1000/n);
                }
                else if (i == this.numOfNodes - 1) {
                    System.out.println("x : "+1300);
                    System.out.println("y : "+500);
                    graph.insertVertex(parent, ""+i+"", "t", 1300, 500, 50, 30);
                }
                else {
                    if(i%2==0) {
                        if(lowerSList.contains(i)){
                            if ((this.x * 2)+500 > 1400) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            System.out.println("x : " + this.x * 2);
                            System.out.println("y : " + (this.y+500));
                            graph.insertVertex(parent, "" + i + "", (char) (i + 96), (this.x * 2)+500, this.y+500, 50, 30);
                            this.y = this.y * 2;
                        }
                        else {
                            if ((this.x * 2)+500 > 1400) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            System.out.println("x : " + this.x * 2);
                            System.out.println("y : " + this.y);
                            graph.insertVertex(parent, "" + i + "", (char) (i + 96), (this.x * 2)+500, this.y, 50, 30);
                            this.y = this.y * 2;
                        }
                    }
                    else {
                        if(lowerSList.contains(i)) {
                            if ((this.y * 2)+500 > 1000) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            System.out.println("x : " + (this.x));
                            System.out.println("y : " + (this.y+500));
                            graph.insertVertex(parent, "" + i + "", Character.toString((char) (i + 96)), this.x, this.y+500, 50, 30);
                            //this.x = this.x * 2;
                        }
                        else{
                            if ((this.y * 2)+500 > 1000) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            System.out.println("x : " + this.x);
                            System.out.println("y : " + this.y);
                            graph.insertVertex(parent, "" + i + "", Character.toString((char) (i + 96)), this.x, this.y, 50, 30);
                            //this.x = this.x * 2;
                        }
                    }
                }
            }
            /*
            Object v1 = graph.insertVertex(parent, null, "v1", 20, 20, 80, 30);
            Object v2 = graph.insertVertex(parent, null, "v2", 120, 70, 80, 30);
            Object v3 = graph.insertVertex(parent, null, "v3", 220, 70, 80, 30,
                    "fillColor=lightgreen");

            graph.insertEdge(parent, null, "", v1, v2);
            graph.insertEdge(parent, null, "", v1, v3, "strokeColor=lightgreen");

            graph.cellsFolded(new Object[] {v1, v2, v3}, true, true);
*/
            mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
            layout.setLevelDistance(40);
            layout.setNodeDistance(30);
            layout.setEdgeRouting(false);
            layout.setUseBoundingBox(false);
            layout.execute(graph.getDefaultParent());

        } finally {
            graph.getModel().endUpdate();
        }
        final mxGraphComponent graphComponent = new mxGraphComponent(graph);

        graphComponent.setFoldingEnabled(true);

        panel.setLayout(new BorderLayout());
        panel.add(graphComponent, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addEdge(){

    }

    
    public static void main(String[] args) {
       // creategraph();
    }
}