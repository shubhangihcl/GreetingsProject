package com.model;
import org.codehaus.jackson.annotate.JsonIgnore;
public class Result {
	private String formattedAddress;
	private boolean partialMatch;
	private Geometry geometry;

	 @JsonIgnore
	 private Object address_components;
	 
	 @JsonIgnore
	 private Object types;

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public boolean isPartialMatch() {
		return partialMatch;
	}

	public void setPartialMatch(boolean partialMatch) {
		this.partialMatch = partialMatch;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	@Override
	public String toString() {
		return "Result [formattedAddress=" + formattedAddress + ", partialMatch=" + partialMatch + ", geometry="
				+ geometry + ", address_components=" + address_components + ", types=" + types + "]";
	}

}
