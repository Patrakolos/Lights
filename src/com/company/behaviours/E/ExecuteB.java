package com.company.behaviours.E;

import com.company.agents.CExecutor;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.Executor;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.HashMap;
import java.util.Vector;

public class ExecuteB extends OneShotBehaviour {
    Executor agent;
    public ExecuteB(Executor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        this.agent.doWait();
        //Execute E plan//
        ACLMessage message = this.agent.receive();
        HashMap<Integer, Vector<Byte>> orders = null;
        try {
            orders = (HashMap<Integer, Vector<Byte>>) message.getContentObject();
            this.agent.setOrders(orders);
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
        System.out.println("Executor < i received my instructions...");
        System.out.println("Executor == Executing E Plan...");

        this.agent.executeEplan();
        ACLMessage finish = new ACLMessage(ACLMessage.INFORM);
        finish.setContent("i finished master...");
        finish.addReceiver(CExecutor.IDENTIFIANT);
        this.agent.send(finish);
    }
}
