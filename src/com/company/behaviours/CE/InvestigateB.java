package com.company.behaviours.CE;

import jade.core.behaviours.OneShotBehaviour;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import com.company.agents.CExecutor;

public class InvestigateB extends OneShotBehaviour {
    CExecutor agent;
    public InvestigateB(CExecutor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        System.out.println("Cognitive Executor ==  Investigating...");
        this.agent.executeTplan();
        //Execute T plan//

        this.agent.doWait(1000L);
    }
}
