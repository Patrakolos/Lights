package com.company.behaviours.CE;

import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.CExecutor;
import jade.lang.acl.ACLMessage;

public class EndB extends OneShotBehaviour {
    CExecutor agent;
    public EndB(CExecutor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        this.agent.doWait();
        ACLMessage message = this.agent.receive();
        AgentLogger.log(message);
        System.out.println("Cognitive Executor > Lights state" + this.agent.getWG().getLights_state());
        System.out.println("Cognitive Executor < i can go now...");
        this.agent.doDelete();
    }
}
