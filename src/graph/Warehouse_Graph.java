package graph;

import com.company.agents.HelloAgent;
import com.company.agents.MyAgent;
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

    int[][] nodes_network;
    int[][] electrical_network;

    LinkedList<String> lights;
    LinkedList<String> nodes;

    public Warehouse_Graph(String path) throws ParserConfigurationException, IOException, SAXException {
        try {
            lights = new LinkedList<>();
            nodes = new LinkedList<>();
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("light");
            System.out.println("----------------------------");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element eElement = (Element) nNode;
                String id = eElement.getAttribute("id");
                if(! lights.contains(id))
                {
                    lights.add(id);
                    System.out.println("Added: "+id);
                }
            }
            nList = doc.getElementsByTagName("node");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                NodeList switchs = nNode.getChildNodes();
                for (int j=0; i<switchs.getLength(); j++){
                    System.out.println(" -> "+ switchs.item(j));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        String path = "/home/mohamed/IdeaProjects/Lights/graphs/graph1.xml";
        Warehouse_Graph WG = new Warehouse_Graph(path);
    }

}
