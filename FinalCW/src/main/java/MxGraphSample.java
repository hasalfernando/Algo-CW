import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MxGraphSample {

    private List<Object> vertexList = new ArrayList<Object>();
    private List<Object> edgeList = new ArrayList<Object>();
    private int numOfNodes = 0;
    private int numOfEdges = 0;
    private List<Integer> lowerSList = new ArrayList<Integer>();
    private int[][] edgeCapacity;
    private int x = 20;
    private final int maxX = 1500;
    private int y = 500;
    private final int maxY = 1000;
    private static final Dimension DEFAULT_SIZE = new Dimension(1500, 1000);
    final mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    final JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    mxGraphComponent graphComponent = null;
    JLabel maxFlowLabel = new JLabel();

    public void createGraph(int numOfNodes, int numOfEdges, int[][] edgeCapacity) {

        this.numOfNodes = numOfNodes;
        this.numOfEdges = numOfEdges;
        this.edgeCapacity = edgeCapacity;
        int n =(this.numOfNodes-2);
        this.lowerSList.add(3);
        this.lowerSList.add(4);
        this.lowerSList.add(7);
        this.lowerSList.add(8);
        frame.setSize(1500, 1000);
        panel.setSize(frame.getMaximumSize().width, frame.getMaximumSize().height);

        graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_FONTSIZE,16);
        graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_FONTCOLOR,"black");
        graph.getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_FONTSIZE,16);
        graph.getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_FONTCOLOR,"black");

        graph.getModel().beginUpdate();
        try {

            for(int i = 0; i<numOfNodes; i++) {
                if (i == 0) {
                    vertexList.add(graph.insertVertex(parent, "0", "s", this.x, y, 50, 30,"ROUNDED;fillColor=lightgreen;fontColor=black"));
                    this.x = (2400/n);
                    this.y = (1000/n);
                }
                else if (i == this.numOfNodes - 1) {
                    vertexList.add(graph.insertVertex(parent, ""+i+"", "t", 1300, 500, 50, 30,"ROUNDED;fillColor=lightgreen;fontColor=black"));

                }
                else {
                    if(i%2==0) {
                        if(lowerSList.contains(i)){
                            if (this.x+900 > 1400) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", (char) (i + 96), this.x+900, this.y+600, 50, 30,"ROUNDED;fillColor=lightgreen;fontColor=black"));
                            this.y = this.y * 2;
                            this.x = this.x *2;
                        }
                        else {
                            if ((this.x)+900 > 1400) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", (char) (i + 96), (this.x)+900, this.y, 50, 30,"ROUNDED;fillColor=lightgreen;fontColor=black"));
                        }
                    }
                    else {
                        if(lowerSList.contains(i)) {
                            if ((this.y * 2)+600 > 1000) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", Character.toString((char) (i + 96)), this.x, this.y+600, 50, 30,"ROUNDED;fillColor=lightgreen;fontColor=black"));
                            //this.x = this.x * 2;
                        }
                        else{
                            if ((this.y*2)+600 > 1000) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", Character.toString((char) (i + 96)), this.x, this.y, 50, 30,"ROUNDED;fillColor=lightgreen;fontColor=black"));
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
            for(int i =0; i<this.numOfNodes;i++){
                for(int j =0; j<this.numOfNodes;j++){
                    if(this.edgeCapacity[i][j]!=0) {
                        edgeList.add(graph.insertEdge(parent, ""+vertexList.get(i)+"-"+ vertexList.get(j)+"", "0/" + String.valueOf(this.edgeCapacity[i][j]), vertexList.get(i), vertexList.get(j)));
                    }
                }
            }
//            graph.insertEdge(parent, null, String.valueOf(this.edgeCapacity[2][1])+"/"+String.valueOf(this.edgeCapacity[1][2]), vertexList.get(1), vertexList.get(2));
  /*          mxCircleLayout layout = new mxCircleLayout(graph);
            //layout.setLevelDistance(40);
            //layout.setNodeDistance(30);
            //layout.setEdgeRouting(false);
            //layout.setUseBoundingBox(false);
            int radius = 600;
            layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
            layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
            layout.setRadius(radius);
            layout.setMoveCircle(true);
*/
            mxParallelEdgeLayout pLayout = new mxParallelEdgeLayout(graph);
            pLayout.execute(graph.getDefaultParent());

        }
        finally {
            graph.getModel().endUpdate();
        }

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setFoldingEnabled(true);
        panel.setLayout(new BorderLayout());
        panel.add(graphComponent, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addEdge(int u, int v, int path_flow, int[][] graphOriginal) throws InterruptedException {
        //int tempCapacity[][]= rGraph;


        graph.getModel().beginUpdate();
        try {
            TimeUnit.SECONDS.sleep(3);
            Object[] edges = graph.getEdgesBetween(vertexList.get(u), vertexList.get(v), true);

            for( Object edge: edges) {
                graph.getModel().remove(edge);
            }
            if(v==graphOriginal.length-1){
                graph.insertEdge(parent, null, path_flow+"/" + String.valueOf(graphOriginal[u][v]), vertexList.get(u), vertexList.get(v),"strokeColor=red;fillColor=red;");
            }
            else{
                graph.insertEdge(parent, null, path_flow+"/" + String.valueOf(graphOriginal[u][v]), vertexList.get(u), vertexList.get(v),"strokeColor=red;fillColor=red;");
            }
        }
        finally {
            graph.getModel().endUpdate();
        }

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setFoldingEnabled(true);
        panel.setLayout(new BorderLayout());
        panel.add(graphComponent, BorderLayout.CENTER);
        maxFlowLabel.setText("Max Flow: "+String.valueOf(12));
        panel.add(maxFlowLabel);
        maxFlowLabel.setLocation(1400,300);
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void updateMaxFlow(int maxFlow){
        frame.add(panel);
        frame.setVisible(true);
    }

}