import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MxGraph {

    private List<Object> vertexList = new ArrayList<Object>();
    private List<Object> edgeList = new ArrayList<Object>();
    private int numOfNodes, numOfEdges;
    private List<Integer> lowerSList = new ArrayList<Integer>();
    private int[][] edgeCapacity;
    private int x = 20;
    private final int maxX = 1500;
    private int y = 500;
    private final int maxY = 1000;
    private final mxGraph graph = new mxGraph();
    private Object parent = graph.getDefaultParent();
    private final JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private mxGraphComponent graphComponent;
    private JLabel maxFlowLabel = new JLabel();
    private JLabel infoLabel = new JLabel();
    private mxParallelEdgeLayout pLayout;

    public void createGraph(int numOfNodes, int numOfEdges, int[][] edgeCapacity) {

        this.numOfNodes = numOfNodes;
        this.numOfEdges = numOfEdges;
        this.edgeCapacity = edgeCapacity;

        //Used in placing nodes in the frame
        int n =(this.numOfNodes-2);
        this.lowerSList.add(3);
        this.lowerSList.add(4);
        this.lowerSList.add(7);
        this.lowerSList.add(8);
        frame.setSize(1500, 1000);
        panel.setSize(frame.getMaximumSize().width, frame.getMaximumSize().height);
        maxFlowLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setHorizontalAlignment(JLabel.LEFT);
        maxFlowLabel.setFont (maxFlowLabel.getFont ().deriveFont (20.0f));
        maxFlowLabel.setText("Max Flow: ");
        infoLabel.setText("<html>Usual Flow : <font color='red'>Red</font> &nbsp; Last Flow : <font color='blue'> Blue</font></html>");
        infoLabel.setFont (infoLabel.getFont ().deriveFont (20.0f));
        graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_FONTSIZE,16);
        graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_FONTCOLOR,"black");
        graph.getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_FONTSIZE,16);
        graph.getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_FONTCOLOR,"black");

        graph.getModel().beginUpdate();
        try {

            for(int i = 0; i<numOfNodes; i++) {
                if (i == 0) {
                    vertexList.add(graph.insertVertex(parent, "0", "s", this.x, y, 50, 30,"fillColor=lightgreen;fontColor=black"));
                    this.x = (2400/n);
                    this.y = (1000/n);
                }
                else if (i == this.numOfNodes - 1) {
                    vertexList.add(graph.insertVertex(parent, ""+i+"", "t", 1300, 500, 50, 30,"fillColor=lightgreen;fontColor=black"));

                }
                else {
                    if(i%2==0) {
                        if(lowerSList.contains(i)){
                            if (this.x+900 > 1400) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", (char) (i + 96), this.x+900, this.y+600, 50, 30,"fillColor=lightgreen;fontColor=black"));
                            this.y = this.y * 2;
                            this.x = this.x *2;
                        }
                        else {
                            if ((this.x)+900 > 1400) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", (char) (i + 96), (this.x)+900, this.y, 50, 30,"fillColor=lightgreen;fontColor=black"));
                        }
                    }
                    else {
                        if(lowerSList.contains(i)) {
                            if ((this.y * 2)+600 > 1000) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", Character.toString((char) (i + 96)), this.x, this.y+600, 50, 30,"fillColor=lightgreen;fontColor=black"));
                            //this.x = this.x * 2;
                        }
                        else{
                            if ((this.y*2)+600 > 1000) {
                                n = n * 2;
                                this.x = maxX / n;
                                this.y = maxY / n;
                            }
                            vertexList.add(graph.insertVertex(parent, "" + i + "", Character.toString((char) (i + 96)), this.x, this.y, 50, 30,"fillColor=lightgreen;fontColor=black"));
                            //this.x = this.x * 2;
                        }
                    }
                }
            }
            for(int i =0; i<this.numOfNodes;i++){
                for(int j =0; j<this.numOfNodes;j++){
                    if(this.edgeCapacity[i][j]!=0) {
                        edgeList.add(graph.insertEdge(parent, ""+vertexList.get(i)+"-"+ vertexList.get(j)+"", "0/" + String.valueOf(this.edgeCapacity[i][j]), vertexList.get(i), vertexList.get(j),"strokeColor=grey;fillColor=grey;"));
                    }
                }
            }
            pLayout = new mxParallelEdgeLayout(graph);
            pLayout.execute(graph.getDefaultParent());

        }
        finally {
            graph.getModel().endUpdate();
        }

        panel.add(infoLabel, BorderLayout.NORTH);
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setFoldingEnabled(true);
        panel.setLayout(new BorderLayout());
        panel.add(graphComponent, BorderLayout.CENTER);
        panel.add(maxFlowLabel, BorderLayout.NORTH);
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public synchronized void addEdge(int u, int v, int path_flow, int[][] graphOriginal, String color) {

        graph.getModel().beginUpdate();
        try {
            //TimeUnit.SECONDS.sleep(1);
            Object[] edges = graph.getEdgesBetween(vertexList.get(u), vertexList.get(v), true);

            for( Object edge: edges) {
                graph.getModel().remove(edge);
            }
            graph.insertEdge(parent, null, path_flow+"/" + String.valueOf(graphOriginal[u][v]), vertexList.get(u), vertexList.get(v),"strokeColor="+color+";fillColor="+color+";");
            pLayout.execute(graph.getDefaultParent());
        }

        finally {
            graph.getModel().endUpdate();
        }


        graphComponent = new mxGraphComponent(graph);
        graphComponent.setFoldingEnabled(true);
        panel.setLayout(new BorderLayout());
        panel.add(graphComponent, BorderLayout.CENTER);
        panel.add(maxFlowLabel, BorderLayout.NORTH);
        panel.add(infoLabel, BorderLayout.NORTH);
        frame.add(panel);

    }

    public synchronized void updateMaxFlow(int maxFlow){

        maxFlowLabel.setText("Max Flow: "+ maxFlow);

    }

}