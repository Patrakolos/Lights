package com.company.behaviours.E;

import com.company.agents.CExecutor;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.Executor;
import jade.lang.acl.ACLMessage;

public class InvestigateB extends OneShotBehaviour {
    Executor agent;
    public InvestigateB(Executor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        this.agent.doWait();
        ACLMessage message = this.agent.receive();
        AgentLogger.log(message);
        //Execute T plan//
        System.out.println("Executor == Investigating...");
        this.agent.doWait(1000L);
        ACLMessage obs = new ACLMessage(ACLMessage.INFORM);
        obs.setContent("here is what i understood...");
        obs.addReceiver(CExecutor.IDENTIFIANT);
        this.agent.send(obs);

    }
}
