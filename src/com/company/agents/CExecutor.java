package com.company.agents;

import com.company.behaviours.CE.EndB;
import com.company.behaviours.CE.ExecuteB;
import com.company.behaviours.CE.InvestigateB;
import jade.core.AID;
import jade.core.Agent;
import com.company.behaviours.CE.ComputeTPlanB;
import com.company.behaviours.CE.ComputeEPlanB;
import jade.core.behaviours.FSMBehaviour;

public class CExecutor  extends Agent {
    private static final String BEHAVIOUR_END= "end";
    private static final String BEHAVIOUR_INVESTIGATE = "investigate";
    private static final String BEHAVIOUR_EXECUTE = "execute";
    private static final String BEHAVIOUR_COMPUTETPLAN = "computeTplan";
    private static final String BEHAVIOUR_COMPUTEEPLAN = "computeEplan";
    public static AID IDENTIFIANT = new AID("Cognitive Executor", false);
    public CExecutor(){

    }
    public void setup() {
        FSMBehaviour behaviour = new FSMBehaviour(this);
        behaviour.registerState(new ExecuteB(this),BEHAVIOUR_EXECUTE);
        behaviour.registerFirstState(new ComputeTPlanB(this),BEHAVIOUR_COMPUTETPLAN);
        behaviour.registerState(new ComputeEPlanB(this),BEHAVIOUR_COMPUTEEPLAN);
        behaviour.registerLastState(new EndB(this),BEHAVIOUR_END);
        behaviour.registerState(new InvestigateB(this),BEHAVIOUR_INVESTIGATE);
        behaviour.registerDefaultTransition(BEHAVIOUR_COMPUTETPLAN,BEHAVIOUR_INVESTIGATE);
        behaviour.registerDefaultTransition(BEHAVIOUR_INVESTIGATE,BEHAVIOUR_COMPUTEEPLAN);
        behaviour.registerDefaultTransition(BEHAVIOUR_COMPUTEEPLAN,BEHAVIOUR_EXECUTE);
        behaviour.registerDefaultTransition(BEHAVIOUR_EXECUTE,BEHAVIOUR_END);
        this.addBehaviour(behaviour);
    }


}
