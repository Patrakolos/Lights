package com.company.behaviours.E;

import com.company.agents.CExecutor;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.Executor;
import jade.lang.acl.ACLMessage;

public class ExecuteB extends OneShotBehaviour {
    Executor agent;
    public ExecuteB(Executor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        this.agent.doWait();
        ACLMessage message = this.agent.receive();
        AgentLogger.log(message);
        //Execute E plan//
        System.out.println("Executor == Executing E Plan...");
        this.agent.doWait(1000L);

        ACLMessage finish = new ACLMessage(ACLMessage.INFORM);
        finish.setContent("i finished master...");
        finish.addReceiver(CExecutor.IDENTIFIANT);
        this.agent.send(finish);
    }
}
