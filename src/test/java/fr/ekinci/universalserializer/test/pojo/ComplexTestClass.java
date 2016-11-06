package fr.ekinci.universalserializer.test.pojo;

import java.util.List;

/**
 * @author Gokan EKINCI
 */
public class ComplexTestClass extends SimpleTestClass {
	// Complex types (NOT for flat files serialization)
	private List<Double> attr10;
	private List<String> attr11;
	private ComplexTestClass attr12;
	private List<ComplexTestClass> attr13;

	// Getters and Setters
	public List<Double> getAttr10() {
		return attr10;
	}

	public void setAttr10(List<Double> attr10) {
		this.attr10 = attr10;
	}

	public List<String> getAttr11() {
		return attr11;
	}

	public void setAttr11(List<String> attr11) {
		this.attr11 = attr11;
	}

	public ComplexTestClass getAttr12() {
		return attr12;
	}

	public void setAttr12(ComplexTestClass attr12) {
		this.attr12 = attr12;
	}

	public List<ComplexTestClass> getAttr13() {
		return attr13;
	}

	public void setAttr13(List<ComplexTestClass> attr13) {
		this.attr13 = attr13;
	}
}
