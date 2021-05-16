package com.company.behaviours.E;
import com.company.agents.CExecutor;
import com.company.behaviours.utils.AgentLogger;
import jade.core.behaviours.OneShotBehaviour;
import com.company.agents.Executor;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public class InvestigateB extends OneShotBehaviour {
    Executor agent;

    public InvestigateB(Executor agent){
        this.agent=agent;
    }
    @Override
    public void action() {
        this.agent.doWait();
        ACLMessage message = this.agent.receive();
        AgentLogger.log(message);
        String[] res = message.getContent().split(",");
        Vector<String> buff = new Vector<String>(Arrays.asList(res));
        Vector<Integer> tplan = new Vector<Integer>();
        for(String s: buff){
            tplan.add(Integer.parseInt(s));
        }
        this.agent.setTplan(tplan);
        System.out.println("Executor < i received my instructions...");
        System.out.println("Executor == Investigating...");

        HashMap<Integer, Vector<Byte>> obs = this.agent.executeTplan();
        //Execute T plan//
        System.out.println("Executor > Here is my observations...");
        this.agent.doWait(1000L);
        try{ACLMessage obs2 = new ACLMessage(ACLMessage.INFORM);
        obs2.setContentObject(obs);
        obs2.addReceiver(CExecutor.IDENTIFIANT);
        this.agent.send(obs2);} catch (Exception e) {
            e.printStackTrace();
        }

    }
}
