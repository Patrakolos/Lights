package com.company.agents;

import graph.Warehouse_Graph;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;




public class Launcher {
    public static void main(String[] args) {
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8888, null);
        config.setParameter("gui", "true");
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController ac;
        AgentController ac2;
        try {
            String path = "graphs/graph1.xml";
            Warehouse_Graph WG = new Warehouse_Graph(path);
            Object[] agentargs = new Object[3];
            agentargs[0]=1;
            agentargs[1]="ABCD";
            agentargs[2]=true;
            ac = mc.createNewAgent("Executor", Executor.class.getName(),  new Object[] {WG});
            ac2= mc.createNewAgent("Cognitive Executor",CExecutor.class.getName(), new Object[] {WG});
            ac.start();
            ac2.start();
        } catch (Exception e) { }
    }
}