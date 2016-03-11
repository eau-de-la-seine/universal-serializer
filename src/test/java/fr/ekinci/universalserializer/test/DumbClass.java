package fr.ekinci.universalserializer.test;

import java.io.Serializable;

/**
 * A dumb object for test purpose, must implement Serializable
 * 
 * @author Gokan EKINCI
 */
class DumbClass implements Serializable {
    private int attr;
    
    public int getAttr(){
        return attr;
    }
    
    public void setAttr(int val){
        this.attr = val;
    }
}
