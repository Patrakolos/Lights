package com.company.behaviours.E;

import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.Executor;
public class StartB extends OneShotBehaviour {
    Executor agent;
    public StartB(Executor agent){
        this.agent=agent;
    }
    @Override
    public void action() {

    }
}
