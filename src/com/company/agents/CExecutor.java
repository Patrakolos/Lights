package com.company.agents;

import com.company.behaviours.CE.EndB;
import com.company.behaviours.CE.ExecuteB;
import com.company.behaviours.CE.InvestigateB;
import graph.Warehouse_Graph;
import jade.core.AID;
import jade.core.Agent;
import com.company.behaviours.CE.ComputeTPlanB;
import com.company.behaviours.CE.ComputeEPlanB;
import jade.core.behaviours.FSMBehaviour;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import com.company.agents.gamestate;
import java.util.Collections;

public class CExecutor  extends Agent {
    private static final String BEHAVIOUR_END= "end";
    private static final String BEHAVIOUR_INVESTIGATE = "investigate";
    private static final String BEHAVIOUR_EXECUTE = "execute";
    private static final String BEHAVIOUR_COMPUTETPLAN = "computeTplan";
    private static final String BEHAVIOUR_COMPUTEEPLAN = "computeEplan";
    public static AID IDENTIFIANT = new AID("Cognitive Executor", false);
    private Vector<Integer> tplan = new Vector<Integer>();
    private LinkedList<Integer> order = new LinkedList<Integer>();
    private HashMap<Integer, Vector<Byte>> obs = new HashMap<Integer, Vector<Byte>>();
    private HashMap<Integer, Vector<Byte>> orders = new HashMap<Integer, Vector<Byte>>();
    private Warehouse_Graph WG;
    private Vector<Vector<Byte>> electrical_network = new Vector<Vector<Byte>>();
    public CExecutor(){

    }

    public HashMap<Integer,Vector<Byte>> distribue_plan(){
        gamestate gs = new gamestate();
        gs.setLights_state(WG.getLights_state());
        gamestate ngs = gs.copy();
        HashMap<Integer,Vector<Byte>> sol = new HashMap<>();
        for(int i: this.order){
            sol.put(i,copylist(ngs.getLights_state()));
            ngs.xor(this.obs.get(i));
        }
        return sol;
    }

    public HashMap<Integer, Vector<Byte>> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Integer, Vector<Byte>> orders) {
        this.orders = orders;
    }

    private boolean comparevector(Vector<Byte> v1, Vector<Byte> v2){
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
                    if(comparevector(WG.getLights_state(),orders.get(i))){
                        System.out.println("CExecutor == i trigger: " + i);
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


    public HashMap<Integer,HashMap<Integer,Vector<Byte>>> allocate(){
        HashMap<Integer,HashMap<Integer,Vector<Byte>>> aloc = new HashMap<>();
        HashMap<Integer,Vector<Byte>> dis = distribue_plan();
        HashMap<Integer,Vector<Byte>> diss1 = new HashMap<>();
        HashMap<Integer,Vector<Byte>> diss2 = new HashMap<>();
        for(int i : dis.keySet()){
            if(i>3){
                diss1.put(i,dis.get(i));
                aloc.put(1,diss1);
            }else{
                diss2.put(i,dis.get(i));
                aloc.put(0,diss2);
            }
        }
        return aloc;
    }

    public LinkedList<Integer> getOrder() {
        return order;
    }

    public Warehouse_Graph getWG() {
        return WG;
    }

    public void setWG(Warehouse_Graph WG) {
        this.WG = WG;
    }

    public void setOrder(LinkedList<Integer> order) {
        this.order = order;
    }

    public void setup() {
        Object[] args = getArguments();
        this.WG = (Warehouse_Graph) args[0];
        WG.setExecutor(0);
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

    public HashMap<Integer, Vector<Byte>> getObs() {
        return obs;
    }

    public void setObs(HashMap<Integer, Vector<Byte>> obs) {
        this.obs = obs;
    }

    private boolean computeE(Node src, int deepness, boolean value, LinkedList<Integer> pred){
        gamestate gs = src.getId();
        if(gs.is_solution() || value){
            return true;
        }
        if(deepness==0){
        return false;
        }else{

            for(int k=0; k<this.obs.size(); k++ ){
                Vector<Byte> v = this.obs.get(k);
                gamestate ngs = (gamestate) gs.copy();
                //System.out.println("previous gs: "+ ngs.getLights_state());
                ngs.xor(v);
                //System.out.println("act:" + v + " -> " + ngs.getLights_state());
                Node ch = new Node(ngs);
                src.addNode(ch);
                //System.out.println("deepnes: " + deepness + " gs: " + gs.getLights_state());

                value= value || computeE(ch,deepness-1, value,pred);
                if(value){
                    pred.add(k);
                   // System.out.println(pred);
                    return true;
                }
            }
            return value;
        }

    }

    public Vector<Byte> copylist(Vector<Byte> l1){
        Vector<Byte> ll = new Vector<>();
        for(byte b:l1){
            ll.add(b);
        }
        return ll;
    }

    public LinkedList<Integer> computeEPlan(){
        for (int j=0; j<this.obs.size();j++){
            electrical_network.add(obs.get(j));
        }
        gamestate gs = new gamestate((Vector<Byte>) WG.getLights_state().clone());
        Node root = new Node(gs);
        LinkedList<Integer> l = new LinkedList<>();
        computeE(root,3,false,l);

        return reverse(l);


    }

    private LinkedList<Integer> reverse(LinkedList<Integer> liste)
    {
        LinkedList<Integer> result = new LinkedList<Integer>();
        for(int i=liste.size()-1; i>=0; i--)
            result.add(liste.get(i));
        return result;
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
        try{
            this.obs = new HashMap<Integer, Vector<Byte>>();
            for(int i :tplan){
                move(i);
                Vector<Byte> change = WG.trigger(i);
                System.out.println("CExecutor == i trigger: " + i);
                obs.put(i,change);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obs;
    }
    public Vector<Integer> getTplan() {
        return tplan;
    }

    public void setTplan(Vector<Integer> tplan) {
        this.tplan = tplan;
    }


}
