package fr.ekinci.universalserializer.test;

/**
 * Created by gokan on 16/10/2016.
 */

import fr.ekinci.universalserializer.format.file.FileInfo;

import java.util.Date;

/**
 * @author Gokan EKINCI
 */
@FileInfo(
    orderedFieldNames = {
        "attr1", "attr2", "attr3", "attr4", "attr5", "attr6", "attr7", "attr8", "attr9", "attr10", "attr11", "attr12", "attr13"
    },
    headerColumnNames = {
        "Col 1", "Col 2", "Col 3", "Col 4", "Col 5", "Col 6", "Col 7", "Col 8", "Col 9", "Col 10", "Col 11", "Col 12", "Col 13"
    })
public class ExcelFileTestClass {
    private String attr1;
    private Date attr2;
    private boolean attr3;
    private Boolean attr4;
    private byte attr5;
    private Byte attr6;
    private short attr7;
    private Short attr8;
    private int attr9;
    private Integer attr10;
    private long attr11;
    private Long attr12;
    private float attr13;
    private Float attr14;
    private double attr15;
    private Double attr16;
    // TODO: Create super class


    public boolean isAttr3() {
        return attr3;
    }

    public void setAttr3(boolean attr3) {
        this.attr3 = attr3;
    }

    public Boolean getAttr4() {
        return attr4;
    }

    public void setAttr4(Boolean attr4) {
        this.attr4 = attr4;
    }

    public byte getAttr5() {
        return attr5;
    }

    public void setAttr5(byte attr5) {
        this.attr5 = attr5;
    }

    public Byte getAttr6() {
        return attr6;
    }

    public void setAttr6(Byte attr6) {
        this.attr6 = attr6;
    }

    public short getAttr7() {
        return attr7;
    }

    public void setAttr7(short attr7) {
        this.attr7 = attr7;
    }

    public Short getAttr8() {
        return attr8;
    }

    public void setAttr8(Short attr8) {
        this.attr8 = attr8;
    }

    public int getAttr9() {
        return attr9;
    }

    public void setAttr9(int attr9) {
        this.attr9 = attr9;
    }

    public Integer getAttr10() {
        return attr10;
    }

    public void setAttr10(Integer attr10) {
        this.attr10 = attr10;
    }

    public long getAttr11() {
        return attr11;
    }

    public void setAttr11(long attr11) {
        this.attr11 = attr11;
    }

    public Long getAttr12() {
        return attr12;
    }

    public void setAttr12(Long attr12) {
        this.attr12 = attr12;
    }

    public float getAttr13() {
        return attr13;
    }

    public void setAttr13(float attr13) {
        this.attr13 = attr13;
    }

    public Float getAttr14() {
        return attr14;
    }

    public void setAttr14(Float attr14) {
        this.attr14 = attr14;
    }

    public double getAttr15() {
        return attr15;
    }

    public void setAttr15(double attr15) {
        this.attr15 = attr15;
    }

    public Double getAttr16() {
        return attr16;
    }

    public void setAttr16(Double attr16) {
        this.attr16 = attr16;
    }
}
