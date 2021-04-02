package com.company.agents;
import jade.core.Agent;
public class HelloAgent extends Agent {
    protected void setup() {
        Object args[] = getArguments();
        for (int i = 0; i < args.length; i++){
            System.out.println("Argument " + i
                    + " : " + args[i].toString());
        }
    }
}