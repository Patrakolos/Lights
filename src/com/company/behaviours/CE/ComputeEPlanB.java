package com.company.behaviours.CE;

import com.company.agents.Executor;
import com.company.behaviours.E.ExecuteB;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.CExecutor;
import jade.lang.acl.ACLMessage;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
public class ComputeEPlanB extends OneShotBehaviour {
    CExecutor agent;
    public ComputeEPlanB(CExecutor a){
        this.agent=a;
    }
    @Override
    public void action() {
        this.agent.doWait();
        try{
        ACLMessage message = this.agent.receive();
        HashMap<Integer, Vector<Byte>> obbs =(HashMap<Integer, Vector<Byte>> ) message.getContentObject();
        this.agent.getObs().putAll(obbs);
        System.out.println("Cognitive Executor > Lights state after the investigation " + this.agent.getWG().getLights_state());
        System.out.println("Cognitive Executor == Creating E Plan...");
        this.agent.setOrder(this.agent.computeEPlan());
        HashMap<Integer, Vector<Byte>> aloc = this.agent.allocate().get(1);
        this.agent.setOrders( this.agent.allocate().get(0));
        this.agent.doWait(1000L);
        System.out.println("Cognitive Executor > Here is the E Plan...");
        try{ACLMessage eplan = new ACLMessage(ACLMessage.INFORM);
            eplan.setContentObject(aloc);
            eplan.addReceiver(Executor.IDENTIFIANT);
            this.agent.send(eplan);} catch (Exception e) {
                e.printStackTrace();
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
