package com.company.agents;

import graph.Warehouse_Graph;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import com.company.behaviours.E.EndB;
import com.company.behaviours.E.ExecuteB;
import com.company.behaviours.E.InvestigateB;
import com.company.behaviours.E.StartB;
import jade.proto.states.StateResetter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public class Executor extends Agent {
    private static final String BEHAVIOUR_END = "end";
    private static final String BEHAVIOUR_START= "start";
    private static final String BEHAVIOUR_INVESTIGATE = "investigate";
    private static final String BEHAVIOUR_EXECUTE = "execute";
    public static AID IDENTIFIANT = new AID("Executor", false);
    private Vector<Integer> tplan = new Vector<Integer>();
    private HashMap<Integer, Vector<Byte>> obs = new HashMap<Integer, Vector<Byte>>();
    private HashMap<Integer, Vector<Byte>> orders = new HashMap<Integer, Vector<Byte>>();
    private Warehouse_Graph WG;
    public Executor(){
    }

    public HashMap<Integer, Vector<Byte>> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Integer, Vector<Byte>> orders) {
        this.orders = orders;
    }

    public void setup() {
        Object[] args = getArguments();
        this.WG = (Warehouse_Graph) args[0];
        WG.setExecutor(4);
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

    public void move(int pos) {
       try{ int src = WG.getExecutor();

        Vector<Integer> pth = WG.path(src,pos);
        for(int i=0;i<pth.size();i++){
            doWait(1000L);
        }
        WG.setExecutor(pos);
       } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public HashMap<Integer, Vector<Byte>> executeTplan()  {
        try{  this.obs = new HashMap<Integer, Vector<Byte>>();

        for(int i :tplan){
            move(i);
            Vector<Byte> change = WG.trigger(i);
            System.out.println("Executor == i trigger: " + i);
            obs.put(i,change);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obs;

    }

    private boolean comparevector2(Vector<Byte> v1, Vector<Byte> v2){
        for(int i=0;i<v1.size();i++){
            if(Byte.compare(v1.get(i),v2.get(i)) != 0){
                return false;
            }
        }
        return true;
    }

    public boolean executeEplan(){
        try{  this.obs = new HashMap<Integer, Vector<Byte>>();

            for(int i :orders.keySet()){
                move(i);
                boolean done = false;

                while(!done){
                    if(comparevector2(WG.getLights_state(),orders.get(i))){
                        System.out.println("Executor == i trigger: " + i);
                        WG.trigger(i);
                        done=true;
                    }else{
                        doWait(200L);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Vector<Integer> getTplan() {
        return tplan;
    }

    public void setTplan(Vector<Integer> tplan) {
        this.tplan = tplan;
    }
}
