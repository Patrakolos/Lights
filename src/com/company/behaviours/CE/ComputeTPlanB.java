package com.company.behaviours.CE;

import com.company.agents.Executor;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.CExecutor;
import jade.lang.acl.ACLMessage;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;

public class ComputeTPlanB extends OneShotBehaviour {
    CExecutor agent;
    public ComputeTPlanB(CExecutor agent){
        this.agent=agent;

    }
    @Override
    public void action() {
        this.agent.doWait();
        ACLMessage message = this.agent.receive();
        AgentLogger.log(message);
        //Compute T Plan//
        System.out.println("Cognitive Executor > Lights state" + this.agent.getWG().getLights_state());
        System.out.println("Cognitive Executor == Creating T Plan...");
        this.agent.doWait(1000L);
        ACLMessage tplan = new ACLMessage(ACLMessage.INFORM);
        tplan.setContent("4,5,6,7");
        tplan.addReceiver(Executor.IDENTIFIANT);
        this.agent.send(tplan);
        Vector<Integer> mtplan = new Vector<Integer>();
        mtplan.add(0);
        mtplan.add(1);
        mtplan.add(2);
        mtplan.add(3);
        this.agent.setTplan(mtplan);


    }
}
