package com.company.behaviours.E;

import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.Executor;
import jade.lang.acl.ACLMessage;
import com.company.agents.CExecutor;


public class StartB extends OneShotBehaviour {
    Executor agent;
    public StartB(Executor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        this.agent.doWait(1000L);
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setContent("hello mister.. i am here..");
        message.addReceiver(CExecutor.IDENTIFIANT);
        this.agent.send(message);
    }
}
