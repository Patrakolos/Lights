package com.company.behaviours.CE;

import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.CExecutor;
public class InvestigateB extends OneShotBehaviour {
    CExecutor agent;
    public InvestigateB(CExecutor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        // Execute E Plan
        //Execute T plan//
        System.out.println("Cognitive Executor ==  Investigating...");
        this.agent.doWait(1000L);
    }
}
