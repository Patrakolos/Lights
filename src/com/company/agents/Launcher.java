package com.company.agents;

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
            Object[] agentargs = new Object[3];
            agentargs[0]=1;
            agentargs[1]="ABCD";
            agentargs[2]=true;
            ac = mc.createNewAgent("agent1", HelloAgent.class.getName(), agentargs);
            ac.start();
            ac2= mc.createNewAgent("ticket_agent",MyAgent.class.getName(),null);
            ac2.start();
        } catch (StaleProxyException e) { }
    }
}