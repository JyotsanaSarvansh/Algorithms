
/**
 *@author JYOTSANA SARVANSH SINHA
 * Prims Algorithm
 * 1610110164s
 */
package problem2_jyotsana;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author SARVANSH SINHA
 */

public class Problem2_Jyotsana {

    /**
     * @param args the command line arguments
     */
    static int temp;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {  }

        JFrame j = new JFrame();
        j.setTitle("KNN");
        JTextArea t1 = new JTextArea();
        temp=0;
        j.add(t1);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(new Dimension(900, 600));
        j.add(new MainWindow());
        j.setVisible(true);

    }
    }
    



//class to manage the nodes
class Node {
    private Point coord = new Point();
    private int id;
    private java.util.List<Node> path;

    public Node(){}
//set the id
    public Node(int id){
        this.id = id;
    }
    //coordinate of the point
    public Node(Point p){
        this.coord = p;
    }
   //set the id
    public void setId(int id){
        this.id = id;
    }
   //set the coordinate
    public void setCoord(int x, int y){
        coord.setLocation(x, y);
    }
   //get the coordinaates of a point
    public Point getCoord(){
        return coord;
    }
    //set the path
    public void setPath(java.util.List<Node> path) {
        this.path = path;
    }
    //get the path
    public java.util.List<Node> getPath() {
        return path;
    }
    //get the X coordinate
    public int getX(){
        return (int) coord.getX();
    }
    //get the Y coordinate
    public int getY(){
        return (int) coord.getY();
    }
    //get the id
    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Node " + id;
    }
}
//to manage the graph
class Graph {
    private int count = 1;
    private java.util.List<Node> nodes = new ArrayList<>();
    private java.util.List<Edge> edges = new ArrayList<>();
   
    private Node source;
    private Node destination;

    private boolean solved = false;
    
    //set if graph is solved or not
    public void setSolved(boolean solved) {
        this.solved = solved;
    }
    //to see if graph is solved
    public boolean isSolved() {
        return solved;
    }
   //set the value to the nodes
    public void setNodes(java.util.List<Node> nodes){
        this.nodes = nodes;
    }
    //get the value of the nodes
    public java.util.List<Node> getNodes(){
        return nodes;
    }
   //set the edges
    public void setEdges(java.util.List<Edge> edges){
        this.edges = edges;
    }
    //get the edges
    public java.util.List<Edge> getEdges(){
        return edges;
    }
    //to see if node is reachable
    public boolean isNodeReachable(Node node){
        for(Edge edge : edges)
            if(node == edge.getNodeOne() || node == edge.getNodeTwo())
                return true;

        return false;
    }
    //set the source
    public void setSource(Node node){
        if(nodes.contains(node))
            source = node;
    }
    //set the destination
    public void setDestination(Node node){
        if(nodes.contains(node))
            destination = node;
    }
    //get the source
    public Node getSource(){
        return source;
    }
    //get the dest
    public Node getDestination(){
        return destination;
    }
    //if source is present
    public boolean isSource(Node node){
        return node == source;
    }
   //if destination is present
    public boolean isDestination(Node node){
        return node == destination;
    }
    //add a node
    public void addNode(Point coord){
        Node node = new Node(coord);
        addNode(node);
    }

    public void addNode(Node node){
        node.setId(count++);
        nodes.add(node);
        if(node.getId()==1)
            source = node;
    }
    //add edge
    public void addEdge(Edge new_edge){
        boolean added = false;
        for(Edge edge : edges){
            if(edge.equals(new_edge)){
                added = true;
                break;
            }
        }
        if(!added)
            edges.add(new_edge);
    }
    //delete a node
    public void deleteNode(Node node){
        java.util.List<Edge> delete = new ArrayList<>();
        for (Edge edge : edges){
            if(edge.hasNode(node)){
                delete.add(edge);
            }
        }
        for (Edge edge : delete){
            edges.remove(edge);
        }
       nodes.remove(node);
    }
  
    public void clear(){
        count = 1;
        nodes.clear();
        edges.clear();
        solved = false;

        source = null;
        destination = null;
    }

}
//class to manage the edges
class Edge {
    private Node one;
    private Node two;
    private int weight = 1;

    public Edge(Node one, Node two){
        this.one = one;
        this.two = two;
    }
    //get the one end of the edge
    public Node getNodeOne(){
        return one;
    }
    //get the second of the edge
    public Node getNodeTwo(){
        return two;
    }
    //set the weight
    public void setWeight(int weight){
        this.weight = weight;
    }
    //get the weight
    public int getWeight(){
        return weight;
    }
    //see if node is present
    public boolean hasNode(Node node){
        return one==node || two==node;
    }

    public boolean equals(Edge edge) {
        return (one ==edge.one && two ==edge.two) || (one ==edge.two && two ==edge.one) ;
    }
    //get the weight of the edge between the two nodes
    @Override
    public String toString() {
        return "Edge ~ "
                + getNodeOne().getId() + " - "
                + getNodeTwo().getId();
    }
}
//graph drwaing: node and edge draw
class DrawUtils {
    private Graphics2D g;
    private static int radius = 20;

    public DrawUtils(Graphics2D graphics2D){
        g = graphics2D;
    }
   //to check if node can be created in that coordinate
    public static boolean isWithinBounds(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();

        int boundX = (int) p.getX();
        int boundY = (int) p.getY();

        return (x <= boundX + radius && x >= boundX - radius) && (y <= boundY + radius && y >= boundY - radius);
    }
    //if two nodes are overlapping
    public static boolean isOverlapping(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();
        int boundX = (int) p.getX();
        int boundY = (int) p.getY();
        return (x <= boundX + 2.5*radius && x >= boundX - 2.5*radius) && (y <= boundY + 2.5*radius && y >= boundY - 2.5*radius);
    }

    public static boolean isOnEdge(MouseEvent e, Edge edge) {

        int dist = distToSegment( e.getPoint(), edge.getNodeOne().getCoord(),  edge.getNodeTwo().getCoord() );
        if (dist<6)
            return true;
        return false;
    }

    public static Color parseColor(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }
    //add weight to the graph
    public void drawWeight(Edge edge) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;

        int rad = radius/2;
        g.fillOval(x-rad, y-rad, 2*rad, 2*rad);
        drawWeightText(String.valueOf(edge.getWeight()), x, y);
    }
    //draw the path
    public void drawPath(java.util.List<Node> path) {
        java.util.List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < path.size()-1; i=i+2) {
            edges.add(new Edge(path.get(i), path.get(i+1)));
        }
        
        for(Edge edge : edges) {
            drawPath(edge);
        }
    }
    //draw the path by coloring the edge
    public void drawPath(Edge edge) {
        g.setColor(parseColor("#00BCD4"));
        drawBoldEdge(edge);
    }
    //color when mouse is hovered over the edge
    public void drawHoveredEdge(Edge edge) {
        g.setColor(parseColor("#E1BEE7"));
        drawBoldEdge(edge);
    }
    
    private void drawBoldEdge(Edge edge){
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        g.setStroke(new BasicStroke(8));
        g.drawLine(from.x, from.y, to.x, to.y);
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;
        int rad = 13;
        g.fillOval(x-rad, y-rad, 2*rad, 2*rad);
    }
    //draw thw normal edge
    public void drawEdge(Edge edge) {
        g.setColor(parseColor("#000000"));
        drawBaseEdge(edge);
        drawWeight(edge);
    }
   
    private void drawBaseEdge(Edge edge){
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        g.setStroke(new BasicStroke(3));
        g.drawLine(from.x, from.y, to.x, to.y);
    }

    public void drawHalo(Node node){
        g.setColor(parseColor("#E91E63"));
        radius+=5;
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);
        radius-=5;
    }
    //draw the source node
    public void drawSourceNode(Node node){
        g.setColor(parseColor("#228B22"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius-=5;
        g.setColor(parseColor("#ADFF2F"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius+=5;
        g.setColor(parseColor("#ADFF2F"));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    public void drawDestinationNode(Node node){
        g.setColor(parseColor("#DC143C"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius-=5;
        g.setColor(parseColor("#FA8072"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius+=5;
        g.setColor(parseColor("#FA8072"));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }
   //draw the normal node
    public void drawNode(Node node){
        g.setColor(parseColor("#00CED1"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius-=5;
        g.setColor(parseColor("#FFFFFF"));
        g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

        radius+=5;
        g.setColor(parseColor("#FFFFFF"));
        drawCentreText(String.valueOf(node.getId()), node.getX(), node.getY());
    }
   //draw the wieght on the edge
    public void drawWeightText(String text, int x, int y) {
        g.setColor(parseColor("#cccccc"));
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }
    //write the node number on the node
    public void drawCentreText(String text, int x, int y) {
        g.setColor(parseColor("#000000"));
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }


    // Calculations
    private static int sqr(int x) {
        return x * x;
    }
    private static int dist2(Point v, Point w) {
        return sqr(v.x - w.x) + sqr(v.y - w.y);
    }
    private static int distToSegmentSquared(Point p, Point v, Point w) {
        double l2 = dist2(v, w);
        if (l2 == 0) return dist2(p, v);
        double t = ((p.x - v.x) * (w.x - v.x) + (p.y - v.y) * (w.y - v.y)) / l2;
        if (t < 0) return dist2(p, v);
        if (t > 1) return dist2(p, w);
        return dist2(p, new Point(
                (int)(v.x + t * (w.x - v.x)),
                (int)(v.y + t * (w.y - v.y))
        ));
    }
    private static int distToSegment(Point p, Point v, Point w) {
        return (int) Math.sqrt(distToSegmentSquared(p, v, w));
    }

}
//manage the graph and the graphapanel
 class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {

    private   DrawUtils drawUtils;

    private   Graph graph;

    private   Node selectedNode = null;
    private   Node hoveredNode = null;
    private   Edge hoveredEdge = null;

    private java.util.List<  Node> path = null;

    private Point cursor;

    public GraphPanel(  Graph graph){
        this.graph = graph;
         addMouseListener(this);
        addMouseMotionListener(this);
    }
    //set the mst
    public void setPath(java.util.List<  Node> path) {
        this.path = path;
        hoveredEdge = null;
        repaint();
    }
    //paint the graph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        drawUtils = new   DrawUtils(graphics2d);
        if(graph.isSolved()){
            drawUtils.drawPath(path);
        }
        if(selectedNode != null && cursor != null){
              Edge e = new   Edge(selectedNode, new   Node(cursor));
            drawUtils.drawEdge(e);
        }

        for(  Edge edge : graph.getEdges()){
            if(edge == hoveredEdge)
                drawUtils.drawHoveredEdge(edge);
            drawUtils.drawEdge(edge);
        }

        for(  Node node : graph.getNodes()){
            if(node == selectedNode || node == hoveredNode)
                drawUtils.drawHalo(node);
            if(graph.isSource(node))
                drawUtils.drawSourceNode(node);
            else if(graph.isDestination(node))
                drawUtils.drawDestinationNode(node);
            else
                drawUtils.drawNode(node);
        }
    }
    //functionalities when mouse is clicked on the graph panel for adding and deleting node etc
    @Override
    public void mouseClicked(MouseEvent e) {
          
          Node selected = null;
        for(  Node node : graph.getNodes()) {
            if(  DrawUtils.isWithinBounds(e, node.getCoord())){
                selected = node;
                break;
            }
        }
        if(hoveredEdge!=null){
            //if right click on the edge: edge is deleted
            if(SwingUtilities.isRightMouseButton(e))
            {
               int n1=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the edge?");
               if(n1==0){
                graph.getEdges().remove(hoveredEdge);
                hoveredEdge = null;
                graph.setSolved(false);
                repaint();
                return; 
            }}
            String input = JOptionPane.showInputDialog("Enter weight for " + hoveredEdge.toString()
                                                        + " : ");
            try {
                int weight = Integer.parseInt(input);
                if (weight > 0) {
                    hoveredEdge.setWeight(weight);
                    graph.setSolved(false);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Weight should be positive");
                }
            } catch (NumberFormatException nfe) {}
            return;
        }
        for(  Node node : graph.getNodes())
        {
            if(  DrawUtils.isOverlapping(e, node.getCoord()))
            {
               JOptionPane.showMessageDialog(null,"Overlapping node cannot be created");
               return;
            }
        }
        
        graph.addNode(e.getPoint());
        graph.setSolved(false);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    //after dragging when mouse is realeased: edge is created
    @Override
    public void mouseReleased(MouseEvent e) {
        for (  Node node : graph.getNodes()) {
            if(selectedNode !=null && node!= selectedNode &&   DrawUtils.isWithinBounds(e, node.getCoord())){
                  Edge new_edge = new   Edge(selectedNode, node);
                graph.addEdge(new_edge);
                graph.setSolved(false);
            }
        }
        selectedNode = null;
        hoveredNode = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //repostion a node or draw a edge when mouse is dragged
    @Override
    public void mouseDragged(MouseEvent e) {
        hoveredNode = null;

        for (  Node node : graph.getNodes()) {
            if(selectedNode ==null &&   DrawUtils.isWithinBounds(e, node.getCoord())){
                selectedNode = node;
            } else if(  DrawUtils.isWithinBounds(e, node.getCoord())) {
                hoveredNode = node;
            }
        }

        if(selectedNode !=null){
            if(e.isControlDown()){
                selectedNode.setCoord(e.getX(), e.getY());
                cursor = null;
                repaint();
                return;
            }

            cursor = new Point(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if(e.isControlDown()){
            hoveredNode = null;
            for (  Node node : graph.getNodes()) {
                if(  DrawUtils.isWithinBounds(e, node.getCoord())) {
                    hoveredNode = node;
                }
            }
        }

        hoveredEdge = null;

        for (  Edge edge : graph.getEdges()) {
            if(  DrawUtils.isOnEdge(e, edge)) {
                hoveredEdge = edge;
            }
        }

        repaint();
    }
    //reset the button when reset button clicked
    public void reset(){
        graph.clear();
        selectedNode = null;
        hoveredNode = null;
        hoveredEdge = null;
        repaint();
    }
}
//class to calculate the MST
class Prims {
    private boolean safe = false;
    private String message = null;

    private   Graph graph;
    private Map<  Node,   Node> predecessors;
    private Map<  Node, Integer> distances;                   

    private PriorityQueue<  Node> unvisited;
    private HashSet<  Node> visited;
    public int parent[]=new int[100];

    public ArrayList<ArrayList<Integer>> aMatrix=new ArrayList<ArrayList<Integer>>();
    
    public Prims(  Graph graph) {
       
        this.graph = graph;
        predecessors = new HashMap<>();
        distances = new HashMap<>();

        for(  Node node : graph.getNodes()){
            distances.put(node, Integer.MAX_VALUE);
        }
        visited = new HashSet<>();

        safe = evaluate();
 //throw new UnsupportedOperationException("Not supported yet.");//To change body of generated methods, choose Tools | Templates.
    }

    public class NodeComparator implements Comparator<  Node>  {
        @Override
        public int compare(  Node node1,   Node node2) {
            return distances.get(node1) - distances.get(node2);
        }
    };

   
    private boolean evaluate(){
        if(graph.getSource()==null){
            message = "Source must be present in the graph";
            return false;
        }
        
        for(  Node node : graph.getNodes()){
            if(!graph.isNodeReachable(node)){
                message = "Graph contains unreachable nodes";
                return false;
            }
        }

        return true;
    }

    public void run() throws IllegalStateException {
        if(!safe) {
            throw new IllegalStateException(message);
        }

        unvisited = new PriorityQueue<>(graph.getNodes().size(), new NodeComparator());

          Node source = graph.getSource();
        distances.put(source, 0);
        visited.add(source);
        for(int i=0;i<=3000;i++);
        for(  Node node : graph.getNodes()){
            ArrayList<Integer> row=new ArrayList<Integer>();
            for(int j=0;j<graph.getNodes().size();j++){
                row.add(0);
            }
            aMatrix.add(row);
        }
        for(  Edge edge : graph.getEdges()) {
              Node a=edge.getNodeOne();
              Node b=edge.getNodeTwo();
            aMatrix.get(a.getId()-1).set(b.getId()-1,edge.getWeight());
            aMatrix.get(b.getId()-1).set(a.getId()-1,edge.getWeight());
        }
      
        primMST();
        
        for(  Node node : graph.getNodes()) {
            node.setPath(getPath1());
        }
       graph.setSolved(true);
        
    }
    void primMST() 
        { 
            int V = aMatrix.size();
            // Array to store constructed MST  

            // Key values used to pick minimum weight edge in cut 
            int key[] = new int [V]; 

            // To represent set of vertices not yet included in MST 
            Boolean mstSet[] = new Boolean[V]; 

            // Initialize all keys as INFINITE 
            for (int i = 0; i < V; i++) 
            { 
                key[i] = Integer.MAX_VALUE; 
                mstSet[i] = false; 
            } 

            // Always include first 1st vertex in MST. 
            key[0] = 0;     // Make key 0 so that this vertex is 
                            // picked as first vertex 
            parent[0] = -1; // First node is always root of MST 

            // The MST will have V vertices 
            for (int count = 0; count < V-1; count++) 
            { 
                // Pick thd minimum key vertex from the set of vertices 
                // not yet included in MST 
                int u = minKey(key, mstSet); 

                // Add the picked vertex to the MST Set 
                mstSet[u] = true; 

                // Update key value and parent index of the adjacent 
                // vertices of the picked vertex. Consider only those 
                // vertices which are not yet included in MST 
                for (int v = 0; v < V; v++) 

                    // graph[u][v] is non zero only for adjacent vertices of m 
                    // mstSet[v] is false for vertices not yet included in MST 
                    // Update the key only if graph[u][v] is smaller than key[v] 
                    if (aMatrix.get(u).get(v)!=0 && mstSet[v] == false && 
                        aMatrix.get(u).get(v) < key[v]) 
                    { 
                        parent[v] = u; 
                        key[v] = aMatrix.get(u).get(v); 
                    } 
            } 

            // print the constructed MST 
            //printMST(); 
        }
        
        int minKey(int key[], Boolean mstSet[]) 
        { 
            // Initialize min value 
            int min = Integer.MAX_VALUE, min_index=-1; 

            for (int v = 0; v < aMatrix.size(); v++) 
                if (mstSet[v] == false && key[v] < min) 
                { 
                    min = key[v]; 
                    min_index = v; 
                } 

            return min_index; 
        } 
        
        
        public java.util.List<  Node> getPath1() 
        { 
             
            java.util.List<  Node> path = new ArrayList<>();
            for (int i = 1; i < aMatrix.size(); i++) {
                for(  Node node : graph.getNodes()) {
                    if(parent[i]+1==node.getId()){
                        path.add(node);
                    }
                }
                for(  Node node : graph.getNodes()) {
                    if(i+1==node.getId()){
                        path.add(node);
                    }
                }
            }

            Collections.reverse(path);

            return path;
        }

    public java.util.List<  Node> getLastNode()//get the nodes for combo box
    {
       return graph.getNodes();
    }
    private void updateDistance(  Node node){
        int distance = distances.get(node);

        for (  Edge neighbor : getNeighbors(node)){
              Node adjacent = getAdjacent(neighbor, node);
            if(visited.contains(adjacent))
                continue;

            int current_dist = distances.get(adjacent);
            int new_dist = distance + neighbor.getWeight();

            if(new_dist < current_dist) {
                distances.put(adjacent, new_dist);
                predecessors.put(adjacent, node);
                unvisited.add(adjacent);
            }
        }
    }

    private   Node getAdjacent(  Edge edge,   Node node) {
        if(edge.getNodeOne()!=node && edge.getNodeTwo()!=node)
            return null;

        return node==edge.getNodeTwo()?edge.getNodeOne():edge.getNodeTwo();
    }

    private java.util.List<  Edge> getNeighbors(  Node node) {
        java.util.List<  Edge> neighbors = new ArrayList<>();

        for(  Edge edge : graph.getEdges()){
            if(edge.getNodeOne()==node ||edge.getNodeTwo()==node)
                neighbors.add(edge);
        }

        return neighbors;
    }

    public Integer getDestinationDistance(){
        return distances.get(graph.getDestination());
    }

    public Integer getDistance(  Node node){
        return distances.get(node);
    }

    public java.util.List<  Node> getDestinationPath() {
        return getPath1();
    }

    public java.util.List<  Node> getPath(  Node node){
        java.util.List<  Node> path = new ArrayList<>();

          Node current = node;
        path.add(current);
        while (current!=graph.getSource()){
            current = predecessors.get(current);
            path.add(current);
        }

        Collections.reverse(path);

        return path;
    }

}
//to handle the main window
class MainWindow extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;
    JButton run;
    public MainWindow(){
        super.setLayout(new BorderLayout());
       setGraphPanel();
    }
    //set the graph_panel and the instrugraoh ctionsPanel
    private void setGraphPanel(){
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));
        Color c = new Color(179, 230, 255);
        graphPanel.setBackground(c);
        JLabel label3 = new JLabel("<html><font color=\"white\" size=\"10\">INSTRUCTIONS</font></html>", SwingConstants.CENTER);
         label3.setVerticalAlignment(SwingConstants.TOP);
        JLabel label2 = new JLabel("<html><pre><font color=\"white\"> <u>ADD NODE</u><br>  Click anywhere in the graph panel to add a node.</font></pre></html>", SwingConstants.LEFT);
         label2.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label5 = new JLabel("<html><pre><font color=\"white\"> <u>ADD EDGE</u><br>  Drag from node to node to create an edge.</font></pre></html>", SwingConstants.LEFT);
         label5.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label4 = new JLabel("<html><pre><font color=\"white\"> <u>ADD WEIGHT</u><br>  Click on edges to set the weight.</font></pre></html>", SwingConstants.LEFT);
         label4.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label6 = new JLabel("<html><pre><font color=\"white\"> <u>REPOSITION A NODE</u><br>  Press CTRL and drag the node.</font></pre></html>", SwingConstants.LEFT);
         label6.setVerticalAlignment(SwingConstants.CENTER);
         JLabel label9 = new JLabel("<html><pre><font color=\"white\"><u>DELETE AN EDGE</u><br>  RIGHT CLICK the edge.</font></pre></html>", SwingConstants.LEFT);
         label9.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label7 = new JLabel("<html><pre><font color=\"white\"> <u>ADD SOURCE</u><br>  After the graph is drawn,click on the RUN button.</font></pre></html>", SwingConstants.LEFT);
         label7.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label8 = new JLabel("<html><pre><font color=\"white\"> <u>FIND THE MST</u><br>  Click on RUN button or press ALT+Q.</font></pre></html>", SwingConstants.LEFT);
         label8.setVerticalAlignment(SwingConstants.CENTER);
        //intructions panel
         JPanel p = new JPanel(new GridLayout(9, 1));//NO OF ROWS NO PF COLUMNS GAP BETWEEN COLUMNS GAP BETWEEN ROWS
          p.add(label3);
          p.add(label2);
          p.add(label5);
          p.add(label4);
          p.add(label6);
          p.add(label9);
          p.add(label7);
          p.add(label8);
        Color c1 = new Color(51, 51, 51);
          p.setBackground(c1);
          //divide the frame into vertical parts : instruction and graph panel
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
         graphPanel.setLayout(new GridLayout(1, 1));
        //JLabel l1 = new JLabel("Graph Panel");
         JLabel l1 = new JLabel("<html><font size=\"10\">GRAPH PANEL</font></html>", SwingConstants.CENTER);
         l1.setVerticalAlignment(SwingConstants.TOP);
        graphPanel.add(l1);
        //JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphPanel, p2);  
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p, graphPanel);  
        add(splitPane);  
        //add(splitPane1);
       // setTopPanel();
        setButtons();
    }
    //set the buttons on the lowerpanel
    private void setButtons(){
        run = new JButton("<html>Run the Algorithm<br><center>(Alt+Q)</center></html>");
        run.setMnemonic(KeyEvent.VK_Q);
        run.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        run.setBackground(Color.YELLOW);
        run.setForeground(Color.BLUE);

        JButton reset = new JButton("Reset");
        reset.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        reset.setBackground(Color.YELLOW);
        reset.setForeground(Color.RED);
        final JButton exit = new JButton("Exit");
        exit.setFont(new java.awt.Font("Arial", Font.BOLD, 16));
        exit.setBackground(Color.YELLOW);
        exit.setForeground(Color.RED);
       // setupIcon(exit, "info");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DrawUtils.parseColor("#DDDDDD"));
        buttonPanel.add(reset);
        buttonPanel.add(run);
        buttonPanel.add(exit);

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.reset();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
}
        });
        //functionality of the run button
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prims d = new Prims(graph);
                int i=0;
                for(Node node : d.getLastNode()) 
                  i++;  
                if(i==0)
                    JOptionPane.showMessageDialog(null, "No MST in an empty graph");
                else if(i==1)
                    JOptionPane.showMessageDialog(null, "Atleast two nodes needed to calculate the MST");
                else{
                 String s[] = new String[i];
                i=0;
                for(Node node : d.getLastNode()) {
                  s[i]=node.toString();
                  i++;
                }
                //calculate the mst bu calling the function
                Prims prim = new Prims(graph);
                try{
                    prim.run();
                    graphPanel.setPath(prim.getDestinationPath());
                } catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            }
        } }  );
        add(buttonPanel, BorderLayout.SOUTH);
    }


}




