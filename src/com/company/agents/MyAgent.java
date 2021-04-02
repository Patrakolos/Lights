
package com.company.agents;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;

public class MyAgent extends Agent {
    protected void setup() {
        Behaviour behaviour = new TickerBehaviour(this, 1000){
            protected void onTick() {
                System.out.println(myAgent.getLocalName());
            }
        };
        addBehaviour(behaviour);
    }
}