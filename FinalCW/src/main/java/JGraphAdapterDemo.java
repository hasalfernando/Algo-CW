
import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JGraphAdapterDemo extends JApplet
{
    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private int numOfNodes =0;
    private int numOfEdges =0;
    private int[][] capacityArray;
    private List<String> vertexList = new ArrayList<String>();


    public JGraphAdapterDemo(int numOfNodes, int numOfEdges,int[][] capacityArray){
        this.numOfNodes = numOfNodes;
        this.numOfEdges = numOfEdges;
        this.capacityArray = capacityArray;
    }
   /*
    public static void main(String[] args)
    {
        JGraphAdapterDemo applet = new JGraphAdapterDemo();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
*/
    @Override
    public void init()
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<String, DefaultEdge>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        for(int i = 0; i<this.numOfNodes; i++){
            if(i ==0){
                g.addVertex("s");
                vertexList.add("s");
            }
            else if(i == this.numOfNodes-1){
                g.addVertex("t");
                vertexList.add("t");
            }
            else {
                g.addVertex("" + (char) (i + 97) + "");
                vertexList.add(String.valueOf((char)(i+97)));
            }
        }

        for(int i = 0; i< this.numOfNodes; i++){
            for(int j = 0; j< this.numOfNodes; j++) {
                if(capacityArray[i][j]!=0){
                    g.addEdge(vertexList.get(i),vertexList.get(j));
                    g.setEdgeWeight(vertexList.get(i),vertexList.get(j),10);
                }
            }

        }

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 300;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
    }
}
