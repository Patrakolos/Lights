package com.company.behaviours.CE;

import com.company.agents.Executor;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.CExecutor;
import jade.lang.acl.ACLMessage;

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
        System.out.println("Cognitive Executor == Creating T Plan...");
        this.agent.doWait(1000L);
        ACLMessage tplan = new ACLMessage(ACLMessage.INFORM);
        tplan.setContent("so the T plan is...");
        tplan.addReceiver(Executor.IDENTIFIANT);
        this.agent.send(tplan);

    }
}
