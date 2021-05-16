package com.company.behaviours.CE;

import com.company.agents.Executor;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.CExecutor;
public class ExecuteB extends OneShotBehaviour {
    CExecutor agent;
    public ExecuteB(CExecutor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        //Execute E plan//
        System.out.println("Cognitive Executor == Executing E Plan...");
        this.agent.executeEplan();
        System.out.println("Cognitive Executor == i finished!");

    }
}
