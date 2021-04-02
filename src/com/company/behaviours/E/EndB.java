package com.company.behaviours.E;

import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.Executor;
public class EndB extends OneShotBehaviour {
    Executor agent;
    public EndB(Executor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        System.out.println("Executor < Now i can die in peace...");
        this.agent.doDelete();

    }
}
