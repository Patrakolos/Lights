package com.company.agents;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
public class gamestate {
    private Vector<Byte> lights_state = new Vector<Byte>();

    public gamestate(){
        lights_state=new Vector<>();
    }

    public gamestate(Vector<Byte> ls){
        lights_state=new Vector<Byte>(ls);
    }
    public void xor(Vector<Byte> interaction){
        int i=0;
        for (byte b : interaction){
            byte bb =(byte)( b ^ lights_state.get(i));
            lights_state.setElementAt(bb , i);
            i++;
        }
    }

    public gamestate copy(){
        Vector<Byte> ls = new Vector<Byte>();
        for(Byte b: lights_state){
            Byte bb = new Byte(b);
            ls.add(bb);
        }
        gamestate gs2 = new gamestate(ls);
        return gs2;
    }

    public boolean is_solution(){
        for(Byte b:lights_state){
            if(b==0){
                return false;
            }
        }
        return true;
    }

    public Vector<Byte> getLights_state() {
        return lights_state;
    }

    public void setLights_state(Vector<Byte> lights_state) {
        this.lights_state = lights_state;
    }
}
