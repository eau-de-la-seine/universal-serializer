package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.format.file.FileInfo;

import java.io.Serializable;

/**
 * @author Gokan EKINCI
 */
public class SimpleTestClass implements Serializable {
    // Primitive attributes + String
    protected byte attr1;
    protected short attr2;
    protected int attr3;
    protected long attr4;
    protected float attr5;
    protected double attr6;
    protected boolean attr7;
    protected char attr8;
    protected String attr9;

    // Getters and Setters
    public byte getAttr1() {
        return attr1;
    }

    public void setAttr1(byte attr1) {
        this.attr1 = attr1;
    }

    public short getAttr2() {
        return attr2;
    }

    public void setAttr2(short attr2) {
        this.attr2 = attr2;
    }

    public int getAttr3() {
        return attr3;
    }

    public void setAttr3(int attr3) {
        this.attr3 = attr3;
    }

    public long getAttr4() {
        return attr4;
    }

    public void setAttr4(long attr4) {
        this.attr4 = attr4;
    }

    public float getAttr5() {
        return attr5;
    }

    public void setAttr5(float attr5) {
        this.attr5 = attr5;
    }

    public double getAttr6() {
        return attr6;
    }

    public void setAttr6(double attr6) {
        this.attr6 = attr6;
    }

    public boolean isAttr7() {
        return attr7;
    }

    public void setAttr7(boolean attr7) {
        this.attr7 = attr7;
    }

    public char getAttr8() {
        return attr8;
    }

    public void setAttr8(char attr8) {
        this.attr8 = attr8;
    }

    public String getAttr9() {
        return attr9;
    }

    public void setAttr9(String attr9) {
        this.attr9 = attr9;
    }
}
