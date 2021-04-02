package com.company.behaviours.CE;

import com.company.agents.Executor;
import com.company.behaviours.E.ExecuteB;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.CExecutor;
import jade.lang.acl.ACLMessage;

public class ComputeEPlanB extends OneShotBehaviour {
    CExecutor agent;
    public ComputeEPlanB(CExecutor a){
        this.agent=a;
    }
    @Override
    public void action() {
        this.agent.doWait();
        ACLMessage message = this.agent.receive();
        AgentLogger.log(message);
        //Compute E Plan//
        System.out.println("Cognitive Executor == Creating E Plan...");
        this.agent.doWait(1000L);
        ACLMessage eplan = new ACLMessage(ACLMessage.INFORM);
        eplan.setContent("so the E plan is...");
        eplan.addReceiver(Executor.IDENTIFIANT);
        this.agent.send(eplan);
    }
}
