package com.company.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import com.company.behaviours.E.EndB;
import com.company.behaviours.E.ExecuteB;
import com.company.behaviours.E.InvestigateB;
import com.company.behaviours.E.StartB;
import jade.proto.states.StateResetter;

public class Executor extends Agent {
    private static final String BEHAVIOUR_END = "end";
    private static final String BEHAVIOUR_START= "start";
    private static final String BEHAVIOUR_INVESTIGATE = "investigate";
    private static final String BEHAVIOUR_EXECUTE = "execute";
    public static AID IDENTIFIANT = new AID("Executor", false);

    public Executor(){
    }

    public void setup() {
        FSMBehaviour behaviour = new FSMBehaviour(this);
        behaviour.registerFirstState(new StartB(this),BEHAVIOUR_START);
        behaviour.registerState(new ExecuteB(this),BEHAVIOUR_EXECUTE);
        behaviour.registerState(new InvestigateB(this),BEHAVIOUR_INVESTIGATE);
        behaviour.registerLastState(new EndB(this),BEHAVIOUR_END);
        behaviour.registerDefaultTransition(BEHAVIOUR_START,BEHAVIOUR_INVESTIGATE);
        behaviour.registerDefaultTransition(BEHAVIOUR_INVESTIGATE,BEHAVIOUR_EXECUTE);
        behaviour.registerDefaultTransition(BEHAVIOUR_EXECUTE,BEHAVIOUR_END);
        this.addBehaviour(behaviour);
    }
}
