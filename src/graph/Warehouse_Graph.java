package graph;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

public class Warehouse_Graph {

    private Vector<Vector<Integer>> nodes_network = new Vector<Vector<Integer>>();
    private Vector<Vector<Byte>> electrical_network = new Vector<Vector<Byte>>();
    private Vector<Byte> lights_state = new Vector<Byte>();

    private int cexecutor=0;
    private int executor=0;
    private LinkedList<String> lights;
    private LinkedList<String> nodes;

    public Warehouse_Graph(String path) throws ParserConfigurationException, IOException, SAXException {
        try {
            lights = new LinkedList<>();
            nodes = new LinkedList<>();
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Creating the warehouse graph...");
            NodeList nList = doc.getElementsByTagName("light");
            System.out.println("----------------------------");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element eElement = (Element) nNode;
                String id = eElement.getAttribute("id");
                if(! lights.contains(id))
                {
                    lights.add(id);
                    //System.out.println("Added: "+id);
                }
            }
            nList = doc.getElementsByTagName("node");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                String id = eElement.getAttribute("label");
                if(! nodes.contains(id))
                {
                    nodes.add(id);
                    //System.out.println("Added: "+id);
                }

            }
            //initalisation de la matrice des nodes //
            for (int j=0; j<nodes.size();j++){
                Vector<Integer> v = new Vector<Integer>(nodes.size());
                for (int jj=0; jj<nodes.size();jj++){
                    v.add(0);
                }
                nodes_network.add(v);
            }
            //initalisation du shema electrique //
            for (int j=0; j<nodes.size();j++){
                Vector<Byte> v = new Vector<Byte>(lights.size());
                for (int jj=0; jj<lights.size();jj++){
                    v.add((byte)0);
                }
                electrical_network.add(v);
            }

            for (int jj=0; jj<lights.size();jj++){
                lights_state.add((byte)0);
                //System.out.println(lights_state.get(jj));
            }
            nList = doc.getElementsByTagName("node");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                int id = Integer.parseInt(eElement.getAttribute("label"));
                NodeList lights = eElement.getElementsByTagName("light");
                for( int i=0;i<lights.getLength();i++){
                    eElement = (Element) lights.item(i);
                    int id2 = Integer.parseInt(eElement.getAttribute("id"));
                    electrical_network.get(id).setElementAt((byte)1, id2);
                }
            }

            System.out.println("Creating the electrical network...");
            System.out.println("----------------------------");
            nList = doc.getElementsByTagName("edge");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                int id1 = Integer.parseInt(eElement.getAttribute("src"));
                int id2 = Integer.parseInt(eElement.getAttribute("dst"));
                nodes_network.get(id1).setElementAt(1, id2);
                nodes_network.get(id2).setElementAt(1, id1);
            }
            //System.out.println(nodes_network);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<Byte> trigger(int trigger){
        Vector<Byte> v = electrical_network.get(trigger);
        synchronized (lights_state) {
            int i=0;
            for (byte b : v){
                byte bb =(byte)( b ^ lights_state.get(i));
                lights_state.setElementAt(bb , i);
                i++;
            }
        }
        return v;
    }

    public int getCexecutor() {
        return cexecutor;
    }

    public void setCexecutor(int cexecutor) {
        this.cexecutor = cexecutor;
    }

    public int getExecutor() {
        return executor;
    }

    public void setExecutor(int executor) {
        this.executor = executor;
    }

    public Vector<Vector<Integer>> getNodes_network() {
        return nodes_network;
    }

    public Vector<Byte> getLights_state() {
        return lights_state;
    }

    private Vector<Integer> getchilds(int src){
        Vector<Integer> results = new Vector<Integer>();
        int i =0;
        for(int j :  nodes_network.get(src)){
            if(j==1){
                results.add(i);
            }
            i++;
        }
        return results;
    }



    public Vector<Integer> path(int src,int target){
        Vector<Integer> file = new Vector<Integer>();
        Vector<Integer> road = new Vector<Integer>();
        Vector<Integer> pred = new Vector<Integer>();
        Vector<Integer> explored = new Vector<Integer>();
        for(int i=0; i<nodes.size(); i++ ){
            pred.add(-1);
        }
        file.add(target);
        explored.add(target);
        boolean found = false;
        while (file.size()>0 && !false){
            int elem = file.remove(0);
            for(int i : getchilds(elem)){
                if(!explored.contains(i)){
                    pred.setElementAt(elem, i);
                    file.add(i);
                    explored.add(i);
                    if(i==src){
                        road.add(i);
                        int buff = i;
                        while (pred.get(buff)!=-1){
                            buff = pred.get(buff);
                            road.add(buff);
                        }
                        return road;
                    }
                }

            }

        }

        return road;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        String path = "graphs/graph1.xml";
        Warehouse_Graph WG = new Warehouse_Graph(path);
        WG.path(6,0);
        WG.trigger(1);
        System.out.println(WG.getLights_state());
        WG.trigger(2);
        System.out.println(WG.getLights_state());
        WG.trigger(0);
        System.out.println(WG.getLights_state());

    }

}
