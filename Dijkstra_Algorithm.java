/**
 *@author JYOTSANA SARVANSH SINHA
 * Dijkstra Algorithm
 * 1610110164s
 */
package problem4_jyotsana;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SARVANSH SINHA
 */
public class Problem4_Jyotsana {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {  }

        JFrame j = new JFrame();
        j.setTitle("Dijkstra Algorithm");

        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(new Dimension(900, 600));
        j.add(new MainWindow());
        j.setVisible(true);
    }
    
}
//Class to perform functions on edges of the graph
class Edge {
    private Node one;
    private Node two;
    private int weight = 1;
    //edge between two nodes
    public Edge(Node one, Node two){
        this.one = one;
        this.two = two;
    }
    //Get the first end of the edge
    public Node getNodeOne(){
        return one;
    }
    //Get the first end of the edge
    public Node getNodeTwo(){
        return two;
    }
    //set weight of the edge
    public void setWeight(int weight){
        this.weight = weight;
    }
    //get weight of the edges for the calculations
    public int getWeight(){
        return weight;
    }
   //if the nodes has this edge
    public boolean hasNode(Node node){
        return one==node || two==node;
    }
    public boolean equals(Edge edge) {
        return (one ==edge.one && two ==edge.two) || (one ==edge.two && two ==edge.one) ;
    }
    //format to get the weight of the edge from the JOptionPane when edge is selected
    @Override
    public String toString() {
        return "Edge ~ "
                + getNodeOne().getId() + " - "
                + getNodeTwo().getId();
    }
}

//Class to handle opeartions on nodes
class Node {
    private Point coord = new Point();
    private int id;
    private java.util.List<Node> path;

    public Node(){}
    //constructor to set the id of the node
    public Node(int id){
        this.id = id;
    }  
    //constructor to give the coordinate of the current node
    public Node(Point p){
        this.coord = p;
    }
    //set an id to the node
    public void setId(int id){
        this.id = id;
    }
    //set the coordinates of the node
    public void setCoord(int x, int y){
        coord.setLocation(x, y);
        
    }
   //return the coordinates of the node
    public Point getCoord(){
        return coord;
    }
    //set the path
    public void setPath(java.util.List<Node> path) {
        this.path = path;
    }
   //get the shortest path
    public java.util.List<Node> getPath() {
        return path;
    }
    //get X coordinate
    public int getX(){
        return (int) coord.getX();
    }
    //get Y coordinate
    public int getY(){
        return (int) coord.getY();
    }
    //return the id of the node
    public int getId(){
        return id;
    }
    
    @Override
    public String toString() {
        return "Node " + id;
    }
}
//class to manage the graph opeartions
class Graph {
    private int count = 1;
    private java.util.List<Node> nodes = new ArrayList<>();
    private java.util.List<Edge> edges = new ArrayList<>();
   
    private Node source;
    private Node destination;

    private boolean solved = false;
    //to set that the graph is solved
    public void setSolved(boolean solved) {
        this.solved = solved;
    }
   //return if the graph is solved
    public boolean isSolved() {
        return solved;
    }
    //set the nodes
    public void setNodes(java.util.List<Node> nodes){
        this.nodes = nodes;
    }
    //get all the nodes
    public java.util.List<Node> getNodes(){
        return nodes;
    }
    //set all the edges, stores it in the list
    public void setEdges(java.util.List<Edge> edges){
        this.edges = edges;
    }
    //get all the edges stored in list
    public java.util.List<Edge> getEdges(){
        return edges;
    }
    //to see if node is reachable, shortese cannot be calculatedif graph is not connected
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
    //get the destination
    public Node getDestination(){
        return destination;
    }
    //check if source is present
    public boolean isSource(Node node){
        return node == source;
    }
   //check if dest is present
    public boolean isDestination(Node node){
        return node == destination;
    }
   //add a node at a selected point to the graph
    public void addNode(Point coord){
        Node node = new Node(coord);
        addNode(node);
    }
   //add a new node
    public void addNode(Node node){
        node.setId(count++);
        nodes.add(node);
        if(node.getId()==1)
            source = node;
    }
    //add an edge
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
    public void deleteEdge(Edge e)
    {
            edges.remove(e);
       
    }
    //reset the graph, works when reset button is clicked
    public void clear(){
        count = 1;
        nodes.clear();
        edges.clear();
        solved = false;

        source = null;
        destination = null;
    }

}
//Class to manage the main frame of the gui
class MainWindow extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;
    
    public MainWindow(){
        super.setLayout(new BorderLayout());
        setGraphPanel();
    }
    //design of the instructions panel, graph Panel and the buttons in the lowerPanel.
    private void setGraphPanel(){
        //Make a graphPanel
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));
        //Set the color to graphPanel
        Color c = new Color(179, 230, 255);
        graphPanel.setBackground(c);
        //Add labels to the instruction panel : using gridLayout
        JLabel label3 = new JLabel("<html><font color=\"white\" size=\"10\">INSTRUCTIONS</font></html>", SwingConstants.CENTER);
         label3.setVerticalAlignment(SwingConstants.TOP);
        JLabel label2 = new JLabel("<html><pre><font color=\"white\"> <u>ADD NODE</u><br>  Click anywhere in the graph panel to add a node.</font></pre></html>", SwingConstants.LEFT);
         label2.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label5 = new JLabel("<html><pre><font color=\"white\"> <u>ADD EDGE</u><br>  Drag from node to node to create an edge.</font></pre></html>", SwingConstants.LEFT);
         label5.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label4 = new JLabel("<html><pre><font color=\"white\"> <u>ADD WEIGHT</u><br>  Click on edges to set the weight.</font></pre></html>", SwingConstants.LEFT);
         label4.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label1 = new JLabel("<html><pre><font color=\"white\"> <u>DELETE A NODE</u><br>  Click the node you want to delete.</font></pre></html>", SwingConstants.LEFT);
         label1.setVerticalAlignment(SwingConstants.CENTER);
         JLabel label9 = new JLabel("<html><pre><font color=\"white\"> <u>DELETE A EDGE</u><br>  Right Click on the edge you want to delete.</font></pre></html>", SwingConstants.LEFT);
         label9.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label6 = new JLabel("<html><pre><font color=\"white\"> <u>REPOSITION A NODE</u><br>  Press CTRL and click on the node.</font></pre></html>", SwingConstants.LEFT);
         label6.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label7 = new JLabel("<html><pre><font color=\"white\"> <u>ADD SOURCE AND DESTINATION</u><br>  After the graph is drawn,click on the DONE button.</font></pre></html>", SwingConstants.LEFT);
         label7.setVerticalAlignment(SwingConstants.CENTER);
        JLabel label8 = new JLabel("<html><pre><font color=\"white\"> <u>FIND THE SHORTEST PATH</u><br>  After src and dest entered, path automatically displayed.</font></pre></html>", SwingConstants.LEFT);
         label8.setVerticalAlignment(SwingConstants.CENTER);
        JPanel p = new JPanel(new GridLayout(9, 1));//NO OF ROWS NO PF COLUMNS GAP BETWEEN COLUMNS GAP BETWEEN ROWS
          p.add(label3);
          p.add(label2);
          p.add(label5);
          p.add(label4);
          p.add(label1);
          p.add(label9);
          p.add(label6);
          p.add(label7);
          p.add(label8);
        Color c1 = new Color(51, 51, 51);
          p.setBackground(c1);
// p.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
       // JLabel l2 = new JLabel("Click anywhere on the ")
//         JPanel panel1 = new JPanel(); 
//         
//         panel1.setPreferredSize( new Dimension( getWidth(), 100 ) );
//          JLabel l1 = new JLabel("INSTRUCTIONS");
//        //l1.setBounds(220,65,170,25);
//        JLabel l2 = new JLabel("CLICK ANYWHRE on the graph panel to make a node.");
//        //l1.setBounds(280,65,170,25);
//        JLabel l3 = new JLabel("DRAG TO NODE TO NODE to create an edge");
//        JLabel l4 = new JLabel("DRAG TO NODE TO NODE to create an edge");
//        l1.setBounds(1150,25,100,10);
//	l2.setBounds(930,45,50,20);
//	l3.setBounds(1090,45,20,20);
//	l4.setBounds(1225,45,50,20);
//         panel1.add(l1);
//         panel1.add(l2);
//         panel1.add(l3);
//         panel1.add(l4);
//        JPanel p1 = new JPanel();
//        //JPanel p2 = new JPanel();
//	
//	p1.setPreferredSize( new Dimension(6000, 100 ) );
//       
//        JLabel l1,l2,l3,l4;
//	l2=new JLabel("INSTRUCTIONS");
//        //System.out.println(super.getWidth());
//        l2.setBounds(100,30,100,10);
////	l2 = new JLabel("Enter Source: ");
////	l3 = new JLabel("Enter Sink: ");
////       
////	l2.setBounds(20,30,100,10);
////	l3.setBounds(300,30,100,10);
//        
//        p1.add(l2);
//        //p2.add(l1);
//        //p1.setLayout(null);
//        l3 = new JLabel("Enter Sink: ");
//        l4 = new JLabel("Enter Sink: ");
////       
////	l2.setBounds(20,30,100,10);
//	l3.setBounds(300,30,100,10);
//        l4.setBounds(300,30,100,10);
//        p1.add(l3);
//         p1.add(l4);
        //Divide the frame into two vertical parts: instruction and graph Panel
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        graphPanel.setLayout(new GridLayout(1, 1));
         JLabel l1 = new JLabel("<html><font size=\"10\">GRAPH PANEL</font></html>", SwingConstants.CENTER);
         l1.setVerticalAlignment(SwingConstants.TOP);
        graphPanel.add(l1);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p, graphPanel);  
        add(splitPane);  
        setButtons();
    }
    //function to set the buttons in the lowerPanel
    private void setButtons(){
        JButton run = new JButton("<html>Find the Shortest Path <br><center>(Alt+Q)</center></html>");
        //Set the Mnemoic function to the run button
        run.setMnemonic(KeyEvent.VK_Q);
        run.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DrawUtils.parseColor("#DDDDDD"));
        buttonPanel.add(reset);
        buttonPanel.add(run);
        buttonPanel.add(exit);
       //functionality of the reset Button
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.reset();
            }
        });
        //functionality of the exit Button
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
}
        });
        //functionality of the run Button
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DijkstraAlgorithm d = new DijkstraAlgorithm(graph);
                int i=0;
                //get the nodes to add in comboBox
                for(Node node : d.getLastNode()) 
                  i++;  
                if(i==0)
                    JOptionPane.showMessageDialog(null, "No shortest path in an empty graph");
                else if(i==1)
                    JOptionPane.showMessageDialog(null, "Atleast two nodes needed to calculate the distance");
                else{
                 String s[] = new String[i];
                i=0;
                for(Node node : d.getLastNode()) {
                  s[i]=node.toString();
                  i++;
                }
                //Design the frame which opens by clicking on the run button
                JFrame f1 = new JFrame();
                f1.setVisible(true);
                Container pane = f1.getContentPane();
                f1.pack();
                JPanel label = new JPanel(new GridLayout(6, 1));
                Color c1 = new Color(200, 230, 255);
                Color c2 = new Color(200, 230, 100);
                label.setBackground(c1);
                JLabel l1 = new JLabel("<html><font size=\"4\"><b>Source<b></font><html>",SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(24,6,8,8));
                l1.setVerticalAlignment(SwingConstants.CENTER);
                JLabel l2 = new JLabel("<html><font size=\"4\"><b>Destination<b></font><html>",SwingConstants.LEFT);
                l2.setBorder(new EmptyBorder(10,0,0,0));
                l2.setVerticalAlignment(SwingConstants.CENTER);
                JComboBox b1 = new JComboBox(s);
                b1.setBorder(new EmptyBorder(0,6,0,24));
                JComboBox b2 = new JComboBox(s);
                b2.setBorder(new EmptyBorder(0,6,0,24));
                b1.setToolTipText("Select the sorurce node");
                b2.setToolTipText("Select the destination node");
                b1.setOpaque(true);
                b1.setBackground(c2);
                label.add(l1);
                label.add(b1);
                label.add(l2);
                label.add(b2);
                JButton set = new JButton("set");
                set.setToolTipText("Click to see the Shortest Path");
                set.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
                set.setBackground(Color.YELLOW);
                set.setForeground(Color.RED);
                JButton exit = new JButton("exit");
                exit.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
                exit.setBackground(Color.YELLOW);
                exit.setForeground(Color.RED);
                JPanel card1 = new JPanel();
                card1.add(set);
                card1.add(exit);
                pane.add(label,BorderLayout.PAGE_START);
                pane.add(card1, BorderLayout.CENTER);
                f1.setSize(300, 280); 
                //functionality on the exit button
                exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               f1.setVisible(false);
            
             }
        });
                //functionality of the set Button
                set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sel1 = (String)b1.getSelectedItem();
                 String sel2 = (String)b2.getSelectedItem();
                int in1=0,in2=0;
                Node src=null;
                Node dest=null;
                for(int j=0;j<s.length;j++)
                {
                    if(s[j].equals(sel1))
                        in1=j;
                    if(s[j].equals(sel2))
                        in2=j;
                }
                int j=0;
                for(Node node : d.getLastNode()) {
                  if(j==in1)
                      src=node;
                  if(j==in2)
                      dest=node;
                  j++;
                }
                //set source and destination nodes
                graphPanel.setSrcDest(dest,src);
                //to find the shortest path
                DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
                try{
                    dijkstraAlgorithm.run();
                    graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
                } catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            }
        });
                }
            } }  );
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
//class to calculate the shortest path
class DijkstraAlgorithm {
    private boolean safe = false;
    private String message = null;
    private Graph graph;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distances;
    private PriorityQueue<Node> unvisited;
    private HashSet<Node> visited;
    //declare peredecessors and unvisited list
    public DijkstraAlgorithm(Graph graph) {
       this.graph = graph;
        predecessors = new HashMap<>();
        distances = new HashMap<>();
        for(Node node : graph.getNodes()){
            distances.put(node, Integer.MAX_VALUE);
        }
        visited = new HashSet<>();
        safe = evaluate();
    }
    //compare the distances of the node
    public class NodeComparator implements Comparator<Node>  {
        @Override
        public int compare(Node node1, Node node2) {
            return distances.get(node1) - distances.get(node2);
        }
    };
    //check if graph is correct to calculate the path
    private boolean evaluate(){
        if(graph.getSource()==null){
            message = "Source must be present in the graph";
            return false;
        }

        if(graph.getDestination()==null){
            message = "Destination must be present in the graph";
            return false;
        }

        for(Node node : graph.getNodes()){
            if(!graph.isNodeReachable(node)){
                if(!graph.isNodeReachable(graph.getDestination()))
                {
                message = "Graph contains unreachable nodes";
                return false;
                }
                
            }
        }
        return true;
    }
    //run the algorithm
    public void run() throws IllegalStateException {
        if(!safe) {
            throw new IllegalStateException(message);
        }
        unvisited = new PriorityQueue<>(graph.getNodes().size(), new NodeComparator());
        Node source = graph.getSource();
        distances.put(source, 0);
        visited.add(source);
       for(int i=0;i<=1000;i++);
        for (Edge neighbor : getNeighbors(source)){
            Node adjacent = getAdjacent(neighbor, source);
            if(adjacent==null)
                continue;

            distances.put(adjacent, neighbor.getWeight());
            predecessors.put(adjacent, source);
            unvisited.add(adjacent);
        }

        while (!unvisited.isEmpty()){
            Node current = unvisited.poll();

            updateDistance(current);

            unvisited.remove(current);
            visited.add(current);
        }

        for(Node node : graph.getNodes()) {
            System.out.println("sum "+(node.getX()+node.getY())+"     "+node.getId());
            node.setPath(getPath(node));
        }
         

        graph.setSolved(true);
        
    }
    //get the last node which was made: for the combo box
    public java.util.List<Node> getLastNode()
    {
       return graph.getNodes();
    }
    //update the new distance of the nodes
    private void updateDistance(Node node){
        int distance = distances.get(node);

        for (Edge neighbor : getNeighbors(node)){
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
    //get the two node connected to the given node connected with given edge
    private Node getAdjacent(Edge edge, Node node) {
        if(edge.getNodeOne()!=node && edge.getNodeTwo()!=node)
            return null;

        return node==edge.getNodeTwo()?edge.getNodeOne():edge.getNodeTwo();
    }
    //get the neighbours of the node
    private java.util.List<Edge> getNeighbors(Node node) {
        java.util.List<Edge> neighbors = new ArrayList<>();

        for(Edge edge : graph.getEdges()){
            if(edge.getNodeOne()==node ||edge.getNodeTwo()==node)
                neighbors.add(edge);
        }

        return neighbors;
    }
    //get the distance of the destination( not updated)
    public Integer getDestinationDistance(){
        return distances.get(graph.getDestination());
    }
    //distance of the given node
    public Integer getDistance(Node node){
        return distances.get(node);
    }
    //return the path to the destination
    public java.util.List<Node> getDestinationPath() {
        return getPath(graph.getDestination());
    }
    //calculate the path to the destination
    public java.util.List<Node> getPath(Node node){
        java.util.List<Node> path = new ArrayList<>();
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
//handle the colors of the graph
class DrawUtils {
    private Graphics2D g;
    private static int radius = 20;

    public DrawUtils(Graphics2D graphics2D){
        g = graphics2D;
    }
    //checking coordinates for the graph
    public static boolean isWithinBounds(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();

        int boundX = (int) p.getX();
        int boundY = (int) p.getY();

        return (x <= boundX + radius && x >= boundX - radius) && (y <= boundY + radius && y >= boundY - radius);
    }
    //so that no two nodes overlap each other
    public static boolean isOverlapping(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();

        int boundX = (int) p.getX();
        int boundY = (int) p.getY();

        return (x <= boundX + 2.5*radius && x >= boundX - 2.5*radius) && (y <= boundY + 2.5*radius && y >= boundY - 2.5*radius);
    }

    public static boolean isOnEdge(MouseEvent e, Edge edge) {

        int dist = distToSegment( e.getPoint(),
                                  edge.getNodeOne().getCoord(),
                                  edge.getNodeTwo().getCoord() );
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
    //draw the edges
    public void drawWeight(Edge edge) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;

        int rad = radius/2;
        g.fillOval(x-rad, y-rad, 2*rad, 2*rad);
        drawWeightText(String.valueOf(edge.getWeight()), x, y);
    }
    //draw the shortest path
    public void drawPath(java.util.List<Node> path) {
        java.util.List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < path.size()-1; i++) {
            edges.add(new Edge(path.get(i), path.get(i+1)));
        }

        for(Edge edge : edges) {
            drawPath(edge);
            
        }
    }
   //set the different color of the path
    public void drawPath(Edge edge) {
        g.setColor(parseColor("#00BCD4"));
        drawBoldEdge(edge);
    }
    //set the color of the edge when mouse hovers over it
    public void drawHoveredEdge(Edge edge) {
        g.setColor(parseColor("#E1BEE7"));
        drawBoldEdge(edge);
    }
    //draw the edge
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
    //set the color of the edges
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
    //set the color of the source node
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
    //set the color of the source node
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
    //set the color of the intermediate node
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
    
    public void drawWeightText(String text, int x, int y) {
        g.setColor(parseColor("#cccccc"));
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }
    //numbers on the node
    public void drawCentreText(String text, int x, int y) {
         g.setColor(parseColor("#000000"));
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }


    // Calculations to draw the node and edges
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
//add new nodes and edges etc
class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {

    private DrawUtils drawUtils;
    private Graph graph;private Node selectedNode = null;
    private Node hoveredNode = null;
    private Edge hoveredEdge = null;
    private java.util.List<Node> path = null;
    private Point cursor;
    public GraphPanel(Graph graph){
        this.graph = graph;
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    //set the path
    public void setPath(java.util.List<Node> path) {
        this.path = path;
        hoveredEdge = null;
        repaint();
    }
    //paint the different components
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawUtils = new DrawUtils(graphics2d);

        if(graph.isSolved()){
            drawUtils.drawPath(path);
        }

        if(selectedNode != null && cursor != null){
            Edge e = new Edge(selectedNode, new Node(cursor));
            drawUtils.drawEdge(e);
        }

        for(Edge edge : graph.getEdges()){
            if(edge == hoveredEdge)
                drawUtils.drawHoveredEdge(edge);
            drawUtils.drawEdge(edge);
        }

        for(Node node : graph.getNodes()){
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
    //set the source and the destination node: for combo box
    public void setSrcDest(Node src,Node dest)
    {
         //System.out.println(src+" "+dest);
        if(!graph.isDestination(dest))
            graph.setSource(dest);
        else
           JOptionPane.showMessageDialog(null, "Source and Destination cannot be same");
        if(!graph.isSource(src))
            graph.setDestination(src);
        else
           JOptionPane.showMessageDialog(null, "Source and Destination cannot be same");
        graph.setSolved(false);
        repaint();
        return;
        
    }
    //action on mouse click: enter weigths and delete nodes 
    @Override
    public void mouseClicked(MouseEvent e) {

        Node selected = null;
        for(Node node : graph.getNodes()) {
            if(DrawUtils.isWithinBounds(e, node.getCoord())){
                selected = node;
                break;
            }
            
        }
        if(selected!=null) {
            if(e.isControlDown() && graph.isSolved()){
                path = selected.getPath();
                repaint();
                return;
                //another function for this will created to set source and detination.
            } else if(e.isShiftDown()){
                if(SwingUtilities.isLeftMouseButton(e)){
                    if(!graph.isDestination(selected))
                        graph.setSource(selected);
                    else
                        JOptionPane.showMessageDialog(null, "Source and Destination cannot be same");
                } else if(SwingUtilities.isRightMouseButton(e)) {
                    if(!graph.isSource(selected))
                        graph.setDestination(selected);
                    else
                        JOptionPane.showMessageDialog(null, "Source and Destination cannot be same");
                }else
                    return;

                graph.setSolved(false);
                repaint();
                return;
            }
        }

        if(hoveredEdge!=null){
            if(SwingUtilities.isRightMouseButton(e)){
                int n1=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the edge?");
                if(n1==0){
                graph.getEdges().remove(hoveredEdge);
                hoveredEdge = null;
                graph.setSolved(false);
                repaint();
                return;
            }
                
            }
             
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

        for(Node node : graph.getNodes()) {
            if(DrawUtils.isOverlapping(e, node.getCoord())){
                //JOptionPane.showMessageDialog(null, "Overlapping Node can't be created");
                int n1 = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the node?");
                if(n1==0)
                {
                   graph.deleteNode(selected);
                   graph.setSolved(false);
                   repaint();
                   return;
                   //JOptionPane.showMessageDialog(null, "Overlapping Node can't be created");  
                }
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
    //after dragging when mouse released add edge or reposition node
    @Override
    public void mouseReleased(MouseEvent e) {
        for (Node node : graph.getNodes()) {
            if(selectedNode !=null && node!= selectedNode && DrawUtils.isWithinBounds(e, node.getCoord())){
                Edge new_edge = new Edge(selectedNode, node);
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
    //add edge or reposition node
    @Override
    public void mouseDragged(MouseEvent e) {
        hoveredNode = null;

        for (Node node : graph.getNodes()) {
            if(selectedNode ==null && DrawUtils.isWithinBounds(e, node.getCoord())){
                selectedNode = node;
            } else if(DrawUtils.isWithinBounds(e, node.getCoord())) {
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
            for (Node node : graph.getNodes()) {
                if(DrawUtils.isWithinBounds(e, node.getCoord())) {
                    hoveredNode = node;
                }
            }
        }
        hoveredEdge = null;
        for (Edge edge : graph.getEdges()) {
            if(DrawUtils.isOnEdge(e, edge)) {
                hoveredEdge = edge;
            }
        }
        repaint();
    }
    //reset the graph
    public void reset(){
        graph.clear();
        selectedNode = null;
        hoveredNode = null;
        hoveredEdge = null;
        repaint();
    }
}





